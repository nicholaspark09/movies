package com.example.vn008xw.golf.data;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.example.vn008xw.golf.AppExecutors;
import com.example.vn008xw.golf.vo.ApiResponse;
import com.example.vn008xw.golf.vo.Resource;


public abstract class NetworkBoundLocalResource<ResultType, RequestType> {

  private final AppExecutors appExecutors;

  private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

  @MainThread
  public NetworkBoundLocalResource(AppExecutors appExecutors) {
    this.appExecutors = appExecutors;
    LiveData<ResultType> localSource = loadFromLocalSource();
    result.addSource(localSource, data -> {
      result.removeSource(localSource);

      if (shouldFetch(data)) {
        fetchFromNetwork(localSource);
      } else {
        // You don't need to hit the network again, so load it from cache
        result.addSource(localSource, oldData -> result.setValue(Resource.success(oldData)));
      }
    });
  }

  private void fetchFromNetwork(final LiveData<ResultType> localSource) {
    LiveData<ApiResponse<RequestType>> apiResponse = createCall();
    result.addSource(localSource, oldData -> result.setValue(Resource.loading(oldData)));
    result.addSource(apiResponse, newData -> {
      result.removeSource(apiResponse);
      result.removeSource(localSource);

      if (newData.isSuccessful()) {
        appExecutors.diskIO().execute(() -> {

          saveLocalSource(processResponse(newData));

          appExecutors.mainThread().execute(() -> {

            result.addSource(loadFromLocalSource(),
                    freshData -> {
                      result.setValue(Resource.success(freshData));
                    });

          });

        });
      } else {
        onFetchFailed();
        result.addSource(localSource, data -> {
          result.setValue(Resource.error(newData.getErrorMessage(), data));
        });
      }
    });
  }

  @NonNull
  @MainThread
  protected abstract LiveData<ResultType> loadFromLocalSource();

  @MainThread
  protected abstract boolean shouldFetch(@Nullable ResultType data);

  @NonNull
  @MainThread
  protected abstract LiveData<ApiResponse<RequestType>> createCall();

  @WorkerThread
  protected RequestType processResponse(ApiResponse<RequestType> response) {
    return response.getBody();
  }

  @WorkerThread
  protected void saveLocalSource(@NonNull RequestType item) {
  }

  protected void onFetchFailed() {
  }

  public LiveData<Resource<ResultType>> asLiveData() {
    return result;
  }
}

