package com.example.movies.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.example.movies.di.MoviesInjector;
import com.example.vn008xw.carbeat.data.vo.Movie;
import com.example.vn008xw.golf.data.movie.MovieDataSource;
import com.example.vn008xw.golf.util.AbsentData;
import com.example.vn008xw.golf.vo.Resource;

import java.util.List;

import javax.inject.Inject;

/**
 * Implementation of MoviesContract
 * Acts as the intermediary between the repository and the view
 */
public final class MoviesViewModel extends AndroidViewModel implements MoviesContract {

  private static final String TAG = MoviesViewModel.class.getSimpleName();

  @Inject
  MovieDataSource repository;
  @VisibleForTesting
  final MutableLiveData<Integer> page = new MutableLiveData<>();
  @VisibleForTesting
  final LiveData<Resource<List<Movie>>> movies = Transformations.switchMap(page, page -> {
    if (page == null) return AbsentData.create();
    return repository.getMovies(page);
  });

  public MoviesViewModel(Application application) {
    super(application);
    MoviesInjector.initAndGetComponent(application).inject(this);
  }

  @Override
  public LiveData<Resource<List<Movie>>> loadMovies() {
    return movies;
  }

  @Override
  public void setPage(@NonNull Integer page) {
    this.page.setValue(page);
  }
}
