package com.example.about.di;

import android.support.annotation.NonNull;

import com.example.about.data.ImageRepository;
import com.example.vn008xw.golf.AppExecutors;
import com.example.vn008xw.golf.data.api.MovieService;
import com.example.vn008xw.golf.util.DaggerUtil;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class AboutModule {

  @Provides
  @Singleton
  ImageRepository providesImageRepository(@NonNull MovieService movieService,
                                          @NonNull AppExecutors appExecutors,
                                          @Named("ApiKey") String apiKey) {
    return DaggerUtil.track(
        new ImageRepository(appExecutors, movieService, apiKey)
    );
  }
}
