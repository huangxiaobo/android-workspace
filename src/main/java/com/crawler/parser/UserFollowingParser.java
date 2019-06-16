package com.crawler.parser;

import com.crawler.fetcher.FetcherTask;
import com.crawler.fetcher.UserDetailFetcher;
import com.jayway.jsonpath.JsonPath;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserFollowingParser extends Parser {

  private Logger logger = LoggerFactory.getLogger(UserDetailParser.class);

  public UserFollowingParser(ParseTask task) {
    super(task);
  }

  public void parse(ParseTask task) {
    // json 格式

    List<String> urlTokenList = JsonPath.parse(task.getContent()).read("$.data..url_token");
    logger.info("url token list: " + urlTokenList.toString());
    for (String s : urlTokenList) {
      if (s == null) {
        continue;
      }
      String url = "https://www.zhihu.com/people/" + s;
      getParserManager().addFetchTask(new FetcherTask(url, UserDetailFetcher.class.getName(),
          UserDetailParser.class.getName()));
    }
  }
}
