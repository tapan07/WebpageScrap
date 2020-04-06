package com.truecaller.core.network;

import io.reactivex.Single;

/**
 * API handler to scrap the web pages
 *
 * @author Tapan Rana (ttapan.rana@gmail.com)
 */
public class WebScrapAPIHandler {

  private String baseUrl;

  public WebScrapAPIHandler(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  /**
   * Retrieve the content of a web page
   *
   * @return {@link Single} result of the content
   */
  public Single<String> getWebContent() {
    WebScrapService scrapService = RetrofitHandler.getRetrofitInstance(baseUrl)
        .create(WebScrapService.class);

    return scrapService.getWebContent();
  }
}
