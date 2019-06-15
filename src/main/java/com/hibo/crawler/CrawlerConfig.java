package com.hibo.crawler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 加载配置文件
 */
@Configuration
public class CrawlerConfig {

  /**
   * 是否使用代理抓取
   */
  @Value("${spring.proxy.useProxy}")
  public boolean useProxy;
  /**
   * 代理文件
   */
  @Value("${spring.proxy.proxyPath}")
  public String proxyPath;

  /**
   * 下载网页线程数
   */
  @Value("${spring.fetch.threadSize}")
  public int fetchThreadSize;

  /**
   * 解析网页线程书
   */
  @Value("${spring.parse.threadSize}")
  public int parseThreadSize;

  /**
   * 爬虫入口
   */
  @Value("${spring.application.startUrl}")
  public String startURL;

  /**
   * user 保存路径
   */
  @Value("${spring.application.savePath}")
  public String savePath;

  @Bean
  public TaskExecutor parseTaskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(4);
    executor.setMaxPoolSize(4);
    executor.setThreadNamePrefix("default_task_executor_thread");
    executor.initialize();
    return executor;
  }

  @Bean
  public TaskExecutor fetchTaskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(4);
    executor.setMaxPoolSize(4);
    executor.setThreadNamePrefix("default_task_executor_thread");
    executor.initialize();
    return executor;
  }
}
