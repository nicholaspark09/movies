package com.example.vn008xw.golf;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.example.vn008xw.golf.di.ApplicationScope;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppExecutors {

  @NonNull
  private final static int NETWORK_THREAD_POOL = 3;
  private final Executor diskIO;
  private final Executor networkIO;
  private final Executor mainThread;

  public AppExecutors(Executor diskIO,
                      Executor networkIo,
                      Executor mainThread) {
    this.diskIO = diskIO;
    this.networkIO = networkIo;
    this.mainThread = mainThread;
  }

  @Inject
  public AppExecutors() {
    this(Executors.newSingleThreadExecutor(),
            Executors.newFixedThreadPool(NETWORK_THREAD_POOL),
            new MainThreadExecutor());
  }

  public Executor diskIO() {
    return diskIO;
  }

  public Executor networkIO() {
    return networkIO;
  }

  public Executor mainThread() {
    return mainThread;
  }

  private static class MainThreadExecutor implements Executor {

    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(@NonNull Runnable runnable) {
      mainThreadHandler.post(runnable);
    }
  }
}
