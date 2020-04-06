package com.truecaller.core.base.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.truecaller.core.base.custom.TrueCallerDialog;
import com.truecaller.core.base.view.BaseView;
import com.truecaller.life.R;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

  private TrueCallerDialog mProgressDialog;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutResource());
  }

  @LayoutRes
  protected abstract int getLayoutResource();

  protected abstract void setUp();

  @Override
  public void showLoading() {
    if (mProgressDialog == null) {
      showLoadingDialog();
    } else if (!mProgressDialog.isShowing()) {
      mProgressDialog.show();
    }
    mProgressDialog.setMessage(getString(R.string.msg_loading));
  }

  @Override
  public void showLoadingDialog() {
    if (mProgressDialog == null) {
      mProgressDialog = new TrueCallerDialog(this);
      mProgressDialog.setCancelable(false);
      mProgressDialog.setCanceledOnTouchOutside(false);
      if (mProgressDialog.getWindow() != null) {
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
      }
      mProgressDialog.show();
    }
  }

  @Override
  public void hideLoading() {
    if (mProgressDialog != null && mProgressDialog.isShowing()) {
      mProgressDialog.cancel();
    }
  }

  @Override
  public void showError(String message) {
    if (message != null) {
      Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    } else {
      Toast.makeText(this, getString(R.string.unknown_error), Toast.LENGTH_LONG).show();
    }
  }

  @Override
  public void showError(int resId) {
    showError(getString(resId));
  }

  @Override
  public boolean isNetworkConnected() {
    NetworkInfo networkInfo = getNetworkInformation(this);
    return networkInfo != null && networkInfo.isConnected();
  }

  private NetworkInfo getNetworkInformation(Context context) {
    ConnectivityManager connectivityManager =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    return connectivityManager.getActiveNetworkInfo();
  }
}
