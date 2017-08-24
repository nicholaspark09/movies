package com.example.movies.di;

import android.content.Context;

import com.example.movies.MovieActivity;
import com.example.movies.ui.MoviesFragment;
import com.example.movies.ui.MoviesViewModel;
import com.example.vn008xw.golf.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface MoviesComponent {

  void inject(MovieActivity activity);

  void inject(MoviesFragment moviesFragment);

  void inject(MoviesViewModel moviesVieModel);

  @Component.Builder
  interface Builder {
    MoviesComponent build();

    @BindsInstance
    Builder bindContext(Context context);
  }
}
