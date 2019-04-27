package com.crawler.fetcher;

import com.crawler.Constants;
import com.crawler.element.Page;
import com.crawler.proxy.Direct;
import com.crawler.proxy.Proxy;
import com.crawler.proxy.ProxyPool;
import com.crawler.utils.HttpClientUtil;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * page task
 * 下载网页并解析，具体解析由子类实现
 * 若使用代理，从ProxyPool中取
 *
 * @see ProxyPool
 */
public abstract class Fetcher implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(Fetcher.class);
    protected String url;
    protected HttpRequestBase request;
    protected boolean proxyFlag;//是否通过代理下载
    protected Proxy currentProxy;//当前线程使用的代理
    protected FetcherManager fetcherManager = null;


    public Fetcher(FetcherManager fetcherManager, String url, boolean proxyFlag) {
        this.fetcherManager = fetcherManager;
        this.url = url;
        this.proxyFlag = proxyFlag;
    }

    public Fetcher(FetcherManager fetcherManager, HttpRequestBase request, boolean proxyFlag) {
        this.fetcherManager = fetcherManager;
        this.request = request;
        this.proxyFlag = proxyFlag;
    }

    public void run() {
        Thread.currentThread().setName("fetcher-thread-task");
        logger.info("url: " + url);
        try {
            if (url != null) {
                request = new HttpGet(url);
            }

            if (proxyFlag) {
                currentProxy = ProxyPool.proxyQueue.take();
                if (!(currentProxy instanceof Direct)) {
                    HttpHost proxy = new HttpHost(currentProxy.getIp(), currentProxy.getPort());
                    request.setConfig(HttpClientUtil.getRequestConfigBuilder().setProxy(proxy).build());
                }
            }
            long requestStartTime = System.currentTimeMillis();
            Page page = new Page(HttpClientUtil.getWebPage(request));
            long requestEndTime = System.currentTimeMillis();
            long requestCostTime = requestEndTime - requestStartTime;
            page.setProxy(currentProxy);
            int status = page.getStatusCode();

            String logStr = Thread.currentThread().getName() + " " + currentProxy +
                    "  executing request " + page.getUrl() + " response statusCode:" + status +
                    "  request cost time:" + requestCostTime + "ms";

            if (status == HttpStatus.SC_OK) {
                if (page.getHtml().contains("zhihu") && !page.getHtml().contains("安全验证")) {
                    logger.debug(logStr);
                    currentProxy.setSuccessfulTimes(currentProxy.getSuccessfulTimes() + 1);
                    currentProxy.setSuccessfulTotalTime(currentProxy.getSuccessfulTotalTime() + requestCostTime);
                    double aTime = (currentProxy.getSuccessfulTotalTime() + 0.0) / currentProxy.getSuccessfulTimes();
                    currentProxy.setSuccessfulAverageTime(aTime);
                    currentProxy.setLastSuccessfulTime(System.currentTimeMillis());
                    parse(page);
                } else {
                    // 代理异常，没有正确返回目标url
                    logger.warn("proxy exception:" + currentProxy.toString());
                }
            }
            // 401--不能通过验证
            else if (status == 404 || status == 401 ||
                    status == 410) {
                logger.warn(logStr);
            } else {
                logger.error(logStr);
                Thread.sleep(100);
                retry();
            }
        } catch (InterruptedException e) {
            logger.error("InterruptedException", e);
        } catch (IOException e) {
            if (currentProxy != null) {
                // 该代理可用，将该代理继续添加到proxyQueue
                currentProxy.setFailureTimes(currentProxy.getFailureTimes() + 1);
            }
            if (!fetcherManager.pool.isShutdown()) {
                retry();
            }
        } finally {
            if (request != null) {
                request.releaseConnection();
            }
            if (currentProxy != null && !currentProxy.isDiscardProxy()) {
                currentProxy.setTimeInterval(Constants.TIME_INTERVAL);
                ProxyPool.proxyQueue.add(currentProxy);
            }
        }
    }

    /**
     * 如果下载page失败，retry
     */
    protected abstract void retry();


    /**
     * 下载page成功后的解析，子类实现page的处理
     */
    protected abstract void parse(Page page);


}
