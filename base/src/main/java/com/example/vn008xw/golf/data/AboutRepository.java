package com.example.vn008xw.golf.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.example.vn008xw.golf.AppExecutors;
import com.example.vn008xw.golf.data.api.WalmartService;
import com.example.vn008xw.golf.vo.ApiResponse;
import com.example.vn008xw.golf.vo.Item;
import com.example.vn008xw.golf.vo.ItemsResult;
import com.example.vn008xw.golf.vo.Resource;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

public class AboutRepository {

  private static final String TAG = AboutRepository.class.getSimpleName();

  @VisibleForTesting
  boolean cacheIsDirty = false;
  @VisibleForTesting
  final AppExecutors appExecutors;
  @VisibleForTesting
  final WalmartService service;
  @VisibleForTesting
  final Map<String, Item> cache;
  private final String apiKey;

  @Inject
  public AboutRepository(@NonNull AppExecutors appExecutors,
                         @NonNull WalmartService walmartService,
                         @Named("ApiKey") String apiKey) {
    this.appExecutors = appExecutors;
    service = walmartService;
    this.apiKey = apiKey;
    cache = new LinkedHashMap<>();
  }

  public LiveData<Resource<Item>> findItem(@NonNull String upc) {
    final MediatorLiveData<Resource<Item>> result = new MediatorLiveData<>();
    // check cache first
    if (cache.containsKey(upc)) {
      appExecutors.mainThread().execute(() -> result.setValue(Resource.success(cache.get(upc))));
    } else {
      Log.d(TAG, "Making call");
      // Call the api
      appExecutors.mainThread().execute(() -> {

        final LiveData<ApiResponse<ItemsResult>> apiCall = service.findItemByUpc(apiKey, upc);
        result.addSource(apiCall, data -> {
          result.removeSource(apiCall);

          if (data.isSuccessful() && !data.body.getItems().isEmpty()) {
            Log.d(TAG, "You got an item back successfully");
            final Item item = data.body.getItems().get(0);
            // Update cache
            appExecutors.diskIO().execute(() -> cache.put(upc, item));
            result.setValue(Resource.success(item));
          }else {
            Log.d(TAG, "Error: " + data.errorMessage);
            result.setValue(Resource.error(data.errorMessage, null));
          }
        });
      });
    }
    return result;
  }

  public void refresh() {
    cacheIsDirty = true;
  }
}