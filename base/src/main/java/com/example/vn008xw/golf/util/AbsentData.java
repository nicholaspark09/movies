package com.example.vn008xw.golf.util;


import android.arch.lifecycle.LiveData;

public class AbsentData extends LiveData {

  private AbsentData() {
    postValue(null);
  }

  public static <T> LiveData<T> create() {
    //noinspection unchecked
    return new AbsentData();
  }
}
