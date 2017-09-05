package com.example.about.di;

import android.content.Context;

import com.example.about.AboutActivity;
import com.example.about.ui.AboutFragment;
import com.example.about.ui.AboutViewModel;
import com.example.about.ui.movieimages.MovieImagesFragment;
import com.example.vn008xw.golf.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AppModule.class, AboutModule.class})
public interface AboutComponent {

  void inject(AboutActivity aboutActivity);

  void inject(AboutFragment aboutFragment);

  void inject(AboutViewModel aboutViewModel);

  void inject(MovieImagesFragment movieImagesFragment);

  @Component.Builder
  interface Builder {
    AboutComponent build();

    @BindsInstance
    Builder bindContext(Context context);
  }
}
