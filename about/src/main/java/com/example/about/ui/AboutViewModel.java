package com.example.about.ui;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.example.about.di.AboutComponent;
import com.example.about.di.AboutInjector;
import com.example.vn008xw.carbeat.data.vo.Movie;
import com.example.vn008xw.golf.data.movie.MovieRepository;
import com.example.vn008xw.golf.util.AbsentData;
import com.example.vn008xw.golf.vo.Resource;

import javax.inject.Inject;

public class AboutViewModel extends AndroidViewModel {

  private static final String TAG = AboutViewModel.class.getSimpleName();

  @Inject
  MovieRepository movieRepository;
  @VisibleForTesting
  final MutableLiveData<Integer> movieId = new MutableLiveData<>();
  @VisibleForTesting
  final LiveData<Resource<Movie>> movie = Transformations.switchMap(movieId, id -> {
    if (id == null) return AbsentData.create();
    return movieRepository.getMovie(id);
  });

  public AboutViewModel(Application application) {
    this(AboutInjector.initAndGetComponent(application), application);
  }

  public AboutViewModel(AboutComponent aboutComponent, Application application) {
    super(application);
    aboutComponent.inject(this);
  }

  LiveData<Resource<Movie>> loadMovie() {
    return movie;
  }

  public void setMovieId(@NonNull Integer id) {
    movieId.setValue(id);
  }
}