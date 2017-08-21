package com.example.about.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.example.vn008xw.golf.AppExecutors;
import com.example.vn008xw.golf.data.NetworkBoundLocalResource;
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
  boolean cacheIsDirty = true;
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
    return new NetworkBoundLocalResource<Item, ItemsResult>(appExecutors) {
      @Override
      protected void saveLocalSource(@NonNull ItemsResult item) {
        if (item.getItems().size() > 0) {
          final Item firstItem = item.getItems().get(0);
          if (!cache.containsKey(upc)) {
            appExecutors.mainThread().execute(() -> {
              cache.put(upc, firstItem);
              cacheIsDirty = false;
            });
          }
        }
      }

      @NonNull
      @Override
      protected LiveData<Item> loadFromLocalSource() {
        final MediatorLiveData<Item> result = new MediatorLiveData<>();
        if (cache.containsKey(upc)) {
          appExecutors.mainThread().execute(() -> result.setValue(cache.get(upc)));
        } else {
          appExecutors.mainThread().execute(() -> result.setValue(null));
        }
        return result;
      }

      @Override
      protected boolean shouldFetch(@Nullable Item data) {
        return data == null || cacheIsDirty;
      }

      @NonNull
      @Override
      protected LiveData<ApiResponse<ItemsResult>> createCall() {
        return service.findItemByUpc(apiKey, upc);
      }
    }.asLiveData();
  }

  public void refresh() {
    cacheIsDirty = true;
  }
}
