package com.example.vn008xw.golf.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.vn008xw.golf.AppExecutors;
import com.example.vn008xw.golf.BuildConfig;
import com.example.vn008xw.golf.data.api.WalmartService;
import com.example.vn008xw.golf.di.LiveDataAdapterFactory;
import com.example.vn008xw.golf.util.DaggerUtil;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public final class DataModule {

  private static final long DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB
  private static final int TIMEOUT_LIMIT = 10;

  static OkHttpClient.Builder createOkHttpClient(Context context) {
    //Install a HTTP cache in the application cache directory.
    File cacheDir = new File(context.getCacheDir(), "https");
    Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
    return DaggerUtil.track(new OkHttpClient.Builder().cache(cache)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS));
  }

  @NonNull
  private static final String PREFERENCES_KEY = "key:golf_preferences";

  @Provides
  @Singleton
  public Gson provideGson() {
    return DaggerUtil.track(new Gson());
  }

  @Provides
  @Singleton
  public SharedPreferences provideSharedPreferences(@NonNull Context context) {
    return DaggerUtil.track(context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE));
  }

  @Provides
  @Singleton
  OkHttpClient provideOkHttpClient(@NonNull Context context) {
    final OkHttpClient.Builder builder = createOkHttpClient(context);
    return DaggerUtil.track(builder.build());
  }

  @Provides
  @Singleton
  @Named("Endpoint")
  String provideEndpoint() {
    return BuildConfig.ENDPOINT;
  }

  @Provides
  @Singleton
  @Named("ApiKey")
  String provideAPiKey() {
    return BuildConfig.API_KEY;
  }

  @Provides
  @Singleton
  Retrofit provideRetrofit(OkHttpClient okHttpClient, @Named("Endpoint") String endpoint) {

    return DaggerUtil.track(
            new Retrofit.Builder()
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(new LiveDataAdapterFactory())
                    .baseUrl(endpoint)
                    .build()
    );
  }

  @Provides
  @Singleton
  WalmartService provideWalmartService(Retrofit retrofit) {
    return DaggerUtil.track(
            retrofit.create(WalmartService.class)
    );
  }

  @Provides
  @Singleton
  AppExecutors provideAppExecutors() {
    return DaggerUtil.track(new AppExecutors());
  }

  @Provides
  @Singleton
  AboutRepository provideAboutRepository(AppExecutors appExecutors,
                                         WalmartService walmartService,
                                         @Named("ApiKey") String apiKey) {
    return DaggerUtil.track(
            new AboutRepository(appExecutors, walmartService, apiKey)
    );
  }
}
