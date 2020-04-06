package com.truecaller.life.callback;

import com.truecaller.core.base.callback.BaseListener;
import com.truecaller.life.view.HomeView;

public interface HomeListener<V extends HomeView> extends BaseListener<V> {

  void getWebContent();

  String get10CharacterResult(String response);

  String getEvery10CharacterResult(String response);

  String getWordCountResult(String response);
}
