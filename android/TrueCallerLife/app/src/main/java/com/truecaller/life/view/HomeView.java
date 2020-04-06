package com.truecaller.life.view;

import com.truecaller.core.base.view.BaseView;

public interface HomeView extends BaseView {

  void updateFirstRequest(String response);

  void updateSecondRequest(String response);

  void updateThirdRequest(String response);
}
