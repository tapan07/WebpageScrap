package com.truecaller.core.base.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import com.truecaller.life.R;

public class TrueCallerDialog extends Dialog {
  private AppCompatTextView mErrorMessage;

  public TrueCallerDialog(@NonNull Context context) {
    super(context);
  }

  public TrueCallerDialog(@NonNull Context context, int themeResId) {
    super(context, themeResId);
  }

  protected TrueCallerDialog(@NonNull Context context, boolean cancelable,
      @Nullable OnCancelListener cancelListener) {
    super(context, cancelable, cancelListener);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.dialog_truecaller);
    mErrorMessage = findViewById(R.id.error_message);
  }

  public void setMessage(String message) {
    mErrorMessage.setText(message);
  }
}
