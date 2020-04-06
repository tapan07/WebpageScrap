package com.truecaller.life.presenter;

import android.util.Log;
import androidx.annotation.VisibleForTesting;
import com.truecaller.core.base.presenter.BasePresenter;
import com.truecaller.core.network.WebScrapAPIHandler;
import com.truecaller.core.rx.BaseObserver;
import com.truecaller.life.R;
import com.truecaller.life.callback.HomeListener;
import com.truecaller.life.view.HomeView;
import com.truecaller.util.CoreConstants;
import com.truecaller.util.Coreutils;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HomePresenter<V extends HomeView> extends BasePresenter<V>
    implements HomeListener<V> {

  private static final String TAG = "HomePresenter";

  public HomePresenter() {
  }

  @Override
  public void getWebContent() {
    if (getView() != null) {
      if (getView().isNetworkConnected()) {
        processWebPageScrap();
      } else {
        getView().showError(R.string.msg_network_error);
      }
    }
  }

  /**
   * Make all the 3 api calls which is running independently without each interaction.
   */
  private void processWebPageScrap() {
    getView().showLoading();
    WebScrapAPIHandler scrapAPIHandler = new WebScrapAPIHandler(CoreConstants.WEB_URL);
    truecaller10thCharacterRequest(scrapAPIHandler);
    truecallerEvery10thCharacterRequest(scrapAPIHandler);
    truecallerWordCounterRequest(scrapAPIHandler);
  }

  /**
   * Subscribing the 10th character of web page content
   *
   * @param scrapAPIHandler API handler
   */
  private void truecaller10thCharacterRequest(WebScrapAPIHandler scrapAPIHandler) {
    scrapAPIHandler.getWebContent()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(truecaller10thCharacterObserver());
  }

  /**
   * Subscribing every 10th character of web page content
   *
   * @param scrapAPIHandler API handler
   */
  private void truecallerEvery10thCharacterRequest(WebScrapAPIHandler scrapAPIHandler) {
    scrapAPIHandler.getWebContent()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(truecallerEvery10thCharacterObserver());
  }

  /**
   * Subscribing the word count of web page content
   *
   * @param scrapAPIHandler API handler
   */
  private void truecallerWordCounterRequest(WebScrapAPIHandler scrapAPIHandler) {
    scrapAPIHandler.getWebContent()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(truecallerWordCounterRequestObserver());
  }

  /**
   * Handle the success/error response of 10th character fetching from web page content
   *
   * @return Single observer of result
   */
  private SingleObserver<? super String> truecaller10thCharacterObserver() {
    return new BaseObserver<String>() {
      @Override
      public void onSuccess(String response) {
        getView().hideLoading();
        lazySet(DisposableHelper.DISPOSED);
        String result = onHandle10CharacterResponse(response);
        getView().updateFirstRequest(result);
      }

      @Override
      public void onError(Throwable ex) {
        getView().hideLoading();
        Log.e(TAG, ex.getMessage());
      }
    };
  }

  /**
   * Data parsing to get the 10th character from the response
   *
   * @param response API response
   * @return the 10th character of result
   */
  @VisibleForTesting
  public String onHandle10CharacterResponse(String response) {
    Document doc = Jsoup.parse(response);
    response = doc.text();
    response = response.replaceAll(CoreConstants.UNKNOWN_REGEX, " ")
        .replaceAll(CoreConstants.SPACE_REGEX, "");
    return get10CharacterResult(response);
  }

  /**
   * Handle the success/error response of every 10th character fetching from web page content
   *
   * @return Single observer of result
   */
  private SingleObserver<? super String> truecallerEvery10thCharacterObserver() {
    return new BaseObserver<String>() {
      @Override
      public void onSuccess(String response) {
        getView().hideLoading();
        lazySet(DisposableHelper.DISPOSED);
        String result = onHandleEvery10CharacterResponse(response);
        getView().updateSecondRequest(result);
      }

      @Override
      public void onError(Throwable ex) {
        getView().hideLoading();
        Log.e(TAG, ex.getMessage());
      }
    };
  }

  /**
   * Data parsing to get every 10th character from the response
   *
   * @param response API response
   * @return the result of every 10th character from the response
   */
  @VisibleForTesting
  public String onHandleEvery10CharacterResponse(String response) {
    String result = "";
    Document doc = Jsoup.parse(response);
    response = doc.text();
    response = response.replaceAll(CoreConstants.UNKNOWN_REGEX, " ")
        .replaceAll(CoreConstants.SPACE_REGEX, "");
    if (Coreutils.isNotEmpty(response)) {
      result = getEvery10CharacterResult(response);
    }
    return result;
  }

  /**
   * Handle the success/error response of word counting from web page content
   *
   * @return Single observer of result
   */
  private SingleObserver<? super String> truecallerWordCounterRequestObserver() {
    return new BaseObserver<String>() {
      @Override
      public void onSuccess(String response) {
        getView().hideLoading();
        lazySet(DisposableHelper.DISPOSED);
        String result = onHandleWordCounterResponse(response);
        getView().updateThirdRequest(result);
      }

      @Override
      public void onError(Throwable ex) {
        getView().hideLoading();
        Log.e(TAG, ex.getMessage());
      }
    };
  }

  /**
   * Data parsing to get the word counts from the response
   *
   * @param response API response
   * @return count of each words
   */
  @VisibleForTesting
  public String onHandleWordCounterResponse(String response) {
    String result = "";
    Document doc = Jsoup.parse(response);
    response = doc.text();
    response = response.replaceAll(CoreConstants.UNKNOWN_REGEX, " ")
        .replaceAll(CoreConstants.SPACE_REGEX, " ");

    if (Coreutils.isNotEmpty(response)) {
      result = getWordCountResult(response);
    }
    return result;
  }

  @Override
  public String get10CharacterResult(String response) {
    if (Coreutils.isNotEmpty(response) && response.length() >= 10) {
      return String.valueOf(response.charAt(9));
    }
    return "";
  }

  @Override
  public String getEvery10CharacterResult(String response) {
    List<Character> result = new ArrayList<>();
    for (int index = 9; index < response.length(); ) {
      result.add(response.charAt(index));
      index = index + 10;
    }
    return result.toString();
  }

  @Override
  public String getWordCountResult(String response) {
    String[] words = response.split(" ");
    Map<String, Integer> wordCount = new HashMap<>(words.length);
    for (String word : words) {
      Integer count = wordCount.get(word);
      if (count == null) {
        count = 0;
      }
      wordCount.put(word, count + 1);
    }

    StringBuilder result = new StringBuilder();
    for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
      result.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
    }
    return result.toString();
  }
}
