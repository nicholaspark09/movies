package com.example.vn008xw.golf.data.movie;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.example.vn008xw.carbeat.data.vo.Movie;
import com.example.vn008xw.carbeat.data.vo.Poster;
import com.example.vn008xw.carbeat.data.vo.SearchResult;
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

public final class MovieRepository implements MovieDataSource {

  private static final String TAG = MovieRepository.class.getSimpleName();
  private static final String YEAR = "2017";

  @VisibleForTesting
  boolean cacheIsDirty = true;
  @VisibleForTesting
  final AppExecutors appExecutors;
  @VisibleForTesting
  final MovieService movieService;
  @VisibleForTesting
  final Map<Integer, List<Movie>> cache;
  @VisibleForTesting
  Movie cachedMovie;
  private final String API_KEY;

  @Inject
  public MovieRepository(@NonNull AppExecutors appExecutors,
                         @NonNull MovieService movieService,
                         @NonNull @Named("ApiKey") String apiKey) {
    this.appExecutors = appExecutors;
    this.movieService = movieService;
    API_KEY = apiKey;
    cache = new LinkedHashMap<>();
  }

  public LiveData<Resource<Movie>> getMovie(@NonNull Integer movieId) {
    return new NetworkBoundLocalResource<Movie, Movie>(appExecutors) {

      @Override
      protected void saveLocalSource(@NonNull Movie item) {
        cachedMovie = item;
      }

      @NonNull
      @Override
      protected LiveData<Movie> loadFromLocalSource() {
        final MediatorLiveData<Movie> localResult = new MediatorLiveData<>();
        if (cachedMovie != null) {
          localResult.setValue(cachedMovie);
        } else {
          localResult.setValue(null);
        }
        return localResult;
      }

      @Override
      protected boolean shouldFetch(@Nullable Movie data) {
        return data == null;
      }

      @NonNull
      @Override
      protected LiveData<ApiResponse<Movie>> createCall() {
        return movieService.getMovie(movieId, API_KEY);
      }
    }.asLiveData();
  }

  public LiveData<Resource<List<Movie>>> getMovies(@NonNull Integer page) {
    return new NetworkBoundLocalResource<List<Movie>, SearchResult>(appExecutors) {

      @Override
      protected void saveLocalSource(@NonNull SearchResult item) {
        appExecutors.diskIO().execute(() -> {
          cache.put(page, item.getResults());
          cacheIsDirty = false;
        });
      }

      @NonNull
      @Override
      protected LiveData<List<Movie>> loadFromLocalSource() {
        final MediatorLiveData<List<Movie>> result = new MediatorLiveData<>();

        appExecutors.diskIO().execute(() -> {
          if (cache.containsKey(page)) {
            appExecutors.mainThread().execute(() -> result.setValue(cache.get(page)));
          } else {
            appExecutors.mainThread().execute(() -> result.setValue(null));
            cacheIsDirty = true;
          }
        });

        return result;
      }

      @Override
      protected boolean shouldFetch(@Nullable List<Movie> data) {
        return data == null || data.isEmpty() || cacheIsDirty;
      }

      @NonNull
      @Override
      protected LiveData<ApiResponse<SearchResult>> createCall() {
        return movieService.discoverByYear(API_KEY, page, YEAR);
      }

    }.asLiveData();
  }

  @Override
  public LiveData<Resource<List<Poster>>> getImages(@NonNull Integer movieId) {
    return null;
  }

}
