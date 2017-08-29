package com.example.about.data;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.example.vn008xw.carbeat.data.vo.ImageResult;
import com.example.vn008xw.carbeat.data.vo.Poster;
import com.example.vn008xw.golf.AppExecutors;
import com.example.vn008xw.golf.data.NetworkBoundLocalResource;
import com.example.vn008xw.golf.data.api.MovieService;
import com.example.vn008xw.golf.vo.ApiResponse;
import com.example.vn008xw.golf.vo.Resource;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

public final class ImageRepository {

  private static final String TAG = ImageRepository.class.getSimpleName();

  @VisibleForTesting
  final AppExecutors appExecutors;
  @VisibleForTesting
  final MovieService movieService;
  @VisibleForTesting
  final Map<Integer, List<Poster>> cache = new LinkedHashMap<>();
  final String apiKey;

  @Inject
  public ImageRepository(@NonNull AppExecutors appExecutors,
                         @NonNull MovieService movieService,
                         @NonNull @Named("ApiKey") String apiKey) {
    this.appExecutors = appExecutors;
    this.movieService = movieService;
    this.apiKey = apiKey;
  }

  public LiveData<Resource<List<Poster>>> getPosters(@NonNull Integer movieId) {
    return new NetworkBoundLocalResource<List<Poster>, ImageResult>(appExecutors) {

      @Override
      protected void saveLocalSource(@NonNull ImageResult item) {
        cache.put(movieId, item.getPosters());
      }

      @NonNull
      @Override
      protected LiveData<List<Poster>> loadFromLocalSource() {
        final MediatorLiveData<List<Poster>> localResult = new MediatorLiveData<>();
        appExecutors.diskIO().execute(() -> {
          if (cache.containsKey(movieId)) {
            appExecutors.mainThread().execute(() -> localResult.setValue(cache.get(movieId)));
          } else {
            appExecutors.mainThread().execute(() -> localResult.setValue(null));
          }
        });
        return localResult;
      }

      @Override
      protected boolean shouldFetch(@Nullable List<Poster> data) {
        return data == null || data.isEmpty();
      }

      @NonNull
      @Override
      protected LiveData<ApiResponse<ImageResult>> createCall() {
        return movieService.getImages(movieId, apiKey);
      }
    }.asLiveData();
  }

}
