package com.example.vn008xw.golf.util;

import android.util.Log;

import com.example.vn008xw.golf.BuildConfig;

public final class DaggerUtil {

  private DaggerUtil() {
    throw new AssertionError("No instances");
  }

  public static <T> T track(T object) {
    if (BuildConfig.DEBUG) {
      Log.d("Dagger Created: ", object.getClass().getSimpleName());
    }
    return object;
  }
}
