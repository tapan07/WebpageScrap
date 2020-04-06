package com.truecaller.life.activity;

import android.os.Bundle;
import androidx.appcompat.widget.AppCompatTextView;
import com.truecaller.core.base.activity.BaseActivity;
import com.truecaller.life.R;
import com.truecaller.life.presenter.HomePresenter;
import com.truecaller.life.view.HomeView;

public class HomeActivity extends BaseActivity implements HomeView {

  private AppCompatTextView requestContent1;
  private AppCompatTextView requestContent2;
  private AppCompatTextView requestContent3;

  private HomePresenter<HomeView> mHomePresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setUp();
    mHomePresenter = new HomePresenter<>();
    mHomePresenter.onAttach(this);
  }

  @Override
  protected int getLayoutResource() {
    return R.layout.activity_home;
  }

  @Override
  protected void setUp() {
    requestContent1 = findViewById(R.id.content1);
    requestContent2 = findViewById(R.id.content2);
    requestContent3 = findViewById(R.id.content3);
    findViewById(R.id.truecaller_life).setOnClickListener(v -> mHomePresenter.getWebContent());
  }

  @Override
  public void updateFirstRequest(String result) {
    requestContent1.setText(String.format("10th Character of the content : %s", result));
  }

  @Override
  public void updateSecondRequest(String result) {
    requestContent2.setText(result);
  }

  @Override
  public void updateThirdRequest(String result) {
    requestContent3.setText(result);
  }
}
