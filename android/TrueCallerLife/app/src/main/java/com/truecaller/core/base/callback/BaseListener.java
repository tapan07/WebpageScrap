package com.truecaller.core.base.callback;

import com.truecaller.core.base.view.BaseView;

public interface BaseListener<V extends BaseView> {

  void onAttach(V view);

  void onDetach();

  void handleError(Throwable error);
}
