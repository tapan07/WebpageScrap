package com.truecaller.core.rx;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public abstract class BaseObserver<T> extends AtomicReference<Disposable>
    implements SingleObserver<T>, Disposable {

  @Override
  public void onSubscribe(Disposable d) {
    DisposableHelper.setOnce(this, d);
  }

  @Override
  public void dispose() {
    DisposableHelper.dispose(this);
  }

  @Override
  public boolean isDisposed() {
    return false;
  }
}
