package com.crawler.fetcher;

import com.crawler.Constants;
import com.crawler.element.Page;
import com.crawler.parser.ParseTask;
import com.crawler.processor.ProcessorManager;
import com.crawler.proxy.Proxy;
import com.crawler.proxy.ProxyPoolManager;
import com.crawler.utils.HttpClientUtil;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * page task 下载网页并解析，具体解析由子类实现 若使用代理，从ProxyPool中取
 *
 * @see ProxyPoolManager
 */
public class Fetcher implements Runnable {

  private static Logger logger = LoggerFactory.getLogger(Fetcher.class);
  protected FetcherTask fetcherTask;
  protected HttpRequestBase request;
  protected Proxy currentProxy;//当前线程使用的代理

  protected FetcherManager fetcherManager;

  protected ProxyPoolManager proxyPoolManager;

  protected ProcessorManager processorManager;

  public Fetcher(FetcherTask task) {
    this.fetcherTask = task;
  }

  public void setProxyPoolManager(ProxyPoolManager proxyPoolManager) {
    this.proxyPoolManager = proxyPoolManager;
  }

  public void setFetcherManager(FetcherManager fetcherManager) {
    this.fetcherManager = fetcherManager;
  }

  public void setProcessorManager(ProcessorManager processorManager) {
    this.processorManager = processorManager;
  }

  public void run() {
    Thread.currentThread().setName("fetcher-thread-task");
    logger.info("url: " + fetcherTask.getUrl());
    String url = fetcherTask.getUrl();
    boolean success = false;
    try {
      if (url != null) {
        request = new HttpGet(url);
      }

      currentProxy = proxyPoolManager.getProxy();

      HttpHost proxy = new HttpHost(currentProxy.getIp(), currentProxy.getPort());
      request.setConfig(HttpClientUtil.getRequestConfigBuilder().setProxy(proxy).build());

      long requestStartTime = System.currentTimeMillis();
      Page page = new Page(HttpClientUtil.getWebPage(request));
      long requestEndTime = System.currentTimeMillis();
      long requestCostTime = requestEndTime - requestStartTime;
      page.setProxy(currentProxy);
      int status = page.getStatusCode();

      String logStr = Thread.currentThread().getName() + " " + currentProxy +
          "  executing request " + page.getUrl() + " response statusCode:" + status +
          "  request cost time:" + requestCostTime + "ms";

      logger.warn(logStr);
      if (status == HttpStatus.SC_OK) {
        if (page.getHtml().contains("zhihu") && !page.getHtml().contains("安全验证")) {
          logger.debug(logStr);
          currentProxy.setSuccessfulTimes(currentProxy.getSuccessfulTimes() + 1);
          currentProxy
              .setSuccessfulTotalTime(currentProxy.getSuccessfulTotalTime() + requestCostTime);
          double aTime =
              (currentProxy.getSuccessfulTotalTime() + 0.0) / currentProxy.getSuccessfulTimes();
          currentProxy.setSuccessfulAverageTime(aTime);
          currentProxy.setLastSuccessfulTime(System.currentTimeMillis());
          success = true;
          parse(page);
        } else {
          // 代理异常，没有正确返回目标url
          logger.warn("proxy exception:" + currentProxy.toString());
          logger.warn("page" + page.getHtml());
        }
      }
    } catch (Exception e) {
      logger.error(String
          .format("request to %s due to exception %s", url, e.getClass().getSimpleName()));
    } finally {
      logger.info(String.format("request to %s %s.", url, success));
      if (success == false) {
        if (currentProxy != null) {
          // 该代理可用，将该代理继续添加到proxyQueue
          currentProxy.setFailureTimes(currentProxy.getFailureTimes() + 1);
        }
        retry();
      }
      if (request != null) {
        request.releaseConnection();
      }
      if (currentProxy != null && !currentProxy.isDiscardProxy()) {
        currentProxy.setTimeInterval(Constants.TIME_INTERVAL);
        proxyPoolManager.addProxy(currentProxy);
      }
    }
  }

  /**
   * 如果下载page失败，retry
   */
  protected void retry() {
    this.fetcherManager.addFetchTask(fetcherTask, true);
  }

  /**
   * 下载page成功后的解析，子类实现page的处理
   */
  protected void parse(Page page) {
    // 加入待解析队列
    this.fetcherManager
        .addParseTask(new ParseTask(page.getHtml(), this.fetcherTask.getParserClassName()));
  }
}
