package com.crawl.proxy.site;


import com.crawl.proxy.parser.ProxyListPageParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.crawl.Constants.TIME_INTERVAL;

import java.util.ArrayList;
import java.util.List;
import com.crawl.proxy.Proxy;

/**
 * http://www.66ip.cn/
 */
public class Ip66ProxyListPageParser implements ProxyListPageParser {

    @Override
    public List<Proxy> parse(String content) {
        List<Proxy> proxyList = new ArrayList<Proxy>();
        if (content == null || content.equals("")) {
            return proxyList;
        }
        Document document = Jsoup.parse(content);
        Elements elements = document.select("table tr:gt(1)");
        for (Element element : elements) {
            String ip = element.select("td:eq(0)").first().text();
            String port = element.select("td:eq(1)").first().text();
            String isAnonymous = element.select("td:eq(3)").first().text();
//            if(!anonymousFlag || isAnonymous.contains("匿")){
            proxyList.add(new Proxy(ip, Integer.valueOf(port), TIME_INTERVAL));
//            }
        }
        return proxyList;
    }
}
