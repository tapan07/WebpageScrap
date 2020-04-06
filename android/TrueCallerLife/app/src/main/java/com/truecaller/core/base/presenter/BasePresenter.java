package com.truecaller.core.base.presenter;

import com.truecaller.core.base.callback.BaseListener;
import com.truecaller.core.base.view.BaseView;
import com.truecaller.life.R;
import javax.net.ssl.HttpsURLConnection;
import retrofit2.HttpException;

public class BasePresenter<V extends BaseView> implements BaseListener<V> {

  private static final int API_STATUS_CODE_LOCAL_ERROR = 0;
  private V mView;

  public BasePresenter() {
  }

  @Override
  public void onAttach(V view) {
    mView = view;
  }

  @Override
  public void onDetach() {
    mView = null;
  }

  @Override
  public void handleError(Throwable error) {
    if (error instanceof HttpException) {
      switch (((HttpException) error).code()) {
        case HttpsURLConnection.HTTP_UNAUTHORIZED:
          mView.showError("Unauthorised User");
          break;
        case HttpsURLConnection.HTTP_FORBIDDEN:
        case HttpsURLConnection.HTTP_BAD_REQUEST:
        case HttpsURLConnection.HTTP_INTERNAL_ERROR:
          mView.showError(R.string.unknown_error);
          break;
        case API_STATUS_CODE_LOCAL_ERROR:
          mView.showError("No Internet Connection");
          break;
        default:
          mView.showError(error.getLocalizedMessage());
      }
    } else {
      mView.showError(error.getMessage());
    }
  }

  public V getView() {
    return mView;
  }

  public boolean isViewAttached() {
    return mView != null;
  }
}
