package com.example.vn008xw.golf;

import android.app.Application;
import android.content.Context;

import com.example.vn008xw.golf.data.DataModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {DataModule.class})
public final class AppModule {

}