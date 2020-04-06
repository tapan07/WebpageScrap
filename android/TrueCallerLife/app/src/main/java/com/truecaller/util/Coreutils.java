package com.truecaller.util;

import androidx.annotation.Nullable;

public class Coreutils {

  public static boolean isNotEmpty(@Nullable String str) {
    return str != null && str.length() != 0;
  }
}
