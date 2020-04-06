package com.truecaller.life;

import com.truecaller.core.network.RetrofitHandler;
import com.truecaller.core.network.WebScrapAPIHandler;
import com.truecaller.core.network.WebScrapService;
import com.truecaller.life.presenter.HomePresenter;
import com.truecaller.life.view.HomeView;
import com.truecaller.util.CoreConstants;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import retrofit2.Retrofit;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ RetrofitHandler.class, Retrofit.class, WebScrapService.class })
public class HomePresenterTest {

  private static final String DUMMY_DATA = "We will use this dummy data to test the every scenario";

  @Mock
  private HomeView view;
  @Mock
  private Retrofit mRetrofit;
  @Mock
  private WebScrapService mService;
  private HomePresenter<HomeView> mHomePresenter;

  @Spy
  private WebScrapAPIHandler handler = Mockito.spy(new WebScrapAPIHandler(CoreConstants.WEB_URL));

  @Before
  public void setUp() throws Exception {
    mHomePresenter = new HomePresenter<>();
    mHomePresenter.onAttach(view);
    onHandleMockData();
  }

  private void onHandleMockData() {
    PowerMockito.mockStatic(RetrofitHandler.class);

    Mockito.when(view.isNetworkConnected())
        .thenReturn(true);

    PowerMockito.when(RetrofitHandler.getRetrofitInstance(Mockito.anyString()))
        .thenReturn(mRetrofit);

    PowerMockito.when(mRetrofit.create(Mockito.any()))
        .thenReturn(mService);

    PowerMockito.when(handler.getWebContent())
        .thenReturn(new Single<String>() {
          @Override
          protected void subscribeActual(SingleObserver<? super String> observer) {
            observer.onSuccess(DUMMY_DATA);
          }
        });
    RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
  }

  @Test
  public void getWebContentTest() {
    mHomePresenter.getWebContent();
    Mockito.verify(handler, Mockito.times(1)).getWebContent();
  }

  @Test
  public void onHandle10CharacterResponseTest() {
    String response = "LIFE AS AN ANDROID ENGINEER";
    String result = mHomePresenter.onHandle10CharacterResponse(response);
    Assert.assertEquals(String.valueOf(response.charAt(9)), result);
  }

  @Test
  public void onHandleEvery10CharacterResponseTest() {
    String response = "LIFE AS AN ANDROID ENGINEER Have you ever wondered "
        + "how it is to be an Android Developer that works on an app with over 250 Million users? ";

    String result = mHomePresenter.onHandleEvery10CharacterResponse(response);
    String expected = getEvery10Character(response);
    Assert.assertEquals(expected, result);
  }

  private String getEvery10Character(String response) {
    response = response.replaceAll(CoreConstants.SPACE_REGEX, "");
    List<Character> result = new ArrayList<>();
    for (int index = 9; index < response.length(); ) {
      result.add(response.charAt(index));
      index = index + 10;
    }
    return result.toString();
  }

  @Test
  public void onHandleWordCounterResponseTest() {
    String response = "LIFE AS AN ANDROID ENGINEER";
    String result = mHomePresenter.onHandleWordCounterResponse(response);
    String expected = getWordCounts(response);
    Assert.assertEquals(expected, result);
  }

  private String getWordCounts(String response) {
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
