package com.truecaller.core.network;

import androidx.annotation.NonNull;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Create an instance of retrofit for API calls
 *
 * @author Tapan Rana (ttapan.rana@gmail.com)
 */
public class RetrofitHandler {

  private static Retrofit retrofitInstance = null;

  /**
   * Create an instance of {@link Retrofit} to make an API calls
   *
   * @param baseUrl API endpoint
   * @return an instance of {@link Retrofit}
   */
  public static Retrofit getRetrofitInstance(String baseUrl) {
    if (retrofitInstance == null) {
      createInstance(baseUrl);
    }

    return retrofitInstance;
  }

  private static void createInstance(String baseUrl) {
    retrofitInstance = new Retrofit.Builder().baseUrl(baseUrl)
        .client(getOkHttpClient())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
  }

  @NonNull
  private static OkHttpClient getOkHttpClient() {
    return new OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build();
  }
}
