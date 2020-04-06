package com.truecaller.core.rx;

import io.reactivex.Scheduler;

public interface AppScheduler {

  Scheduler ui();

  Scheduler computation();

  Scheduler io();
}
