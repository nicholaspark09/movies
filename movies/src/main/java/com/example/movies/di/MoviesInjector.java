package com.example.movies.di;


import android.content.Context;

/**
 *  Helper class to automatically inject into fragments, viewmodels, etc
 */
public class MoviesInjector {

  private static MoviesComponent moviesComponent;

  private MoviesInjector() {
  }

  public static MoviesComponent initAndGetComponent(Context context) {
    if (moviesComponent == null) {
      moviesComponent =
              DaggerMoviesComponent
                      .builder()
                      .bindContext(context)
                      .build();
    }
    return moviesComponent;
  }
}
