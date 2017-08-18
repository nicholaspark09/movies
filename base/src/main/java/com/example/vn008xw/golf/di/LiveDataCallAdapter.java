package com.example.vn008xw.golf.di;

import android.arch.lifecycle.LiveData;

import com.example.vn008xw.golf.vo.ApiResponse;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<ApiResponse<R>>> {

  private final Type responseType;

  public LiveDataCallAdapter(Type responseType) {
    this.responseType = responseType;
  }

  @Override
  public Type responseType() {
    return responseType;
  }

  @Override
  public LiveData<ApiResponse<R>> adapt(final Call<R> call) {
    return new LiveData<ApiResponse<R>>() {
      // keep it threadsafe
      AtomicBoolean didStart = new AtomicBoolean(false);
      @Override
      protected void onActive() {
        super.onActive();
        if (didStart.compareAndSet(false, true)) {
          call.enqueue(new Callback<R>() {
            @Override
            public void onResponse(Call<R> call, Response<R> response) {
              postValue(new ApiResponse<>(response));
            }

            @Override
            public void onFailure(Call<R> call, Throwable t) {
              postValue(new ApiResponse<R>(t));
            }
          });
        }
      }
    };
  }
}