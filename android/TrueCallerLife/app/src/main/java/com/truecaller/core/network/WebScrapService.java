package com.truecaller.core.network;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Handle the API services
 *
 * @author Tapan Rana (ttapan.rana@gmail.com)
 */
public interface WebScrapService {

  @Headers({ "Content-Type:application/json" })
  @GET(".")
  Single<String> getWebContent();
}
