package com.truecaller.core.base.view;

import androidx.annotation.StringRes;

public interface BaseView {

  void showLoading();

  void hideLoading();

  void showError(@StringRes int resId);

  void showError(String message);

  void showLoadingDialog();

  boolean isNetworkConnected();
}
