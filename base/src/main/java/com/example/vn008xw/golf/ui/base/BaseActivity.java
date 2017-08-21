package com.example.vn008xw.golf.ui.base;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import static android.arch.lifecycle.Lifecycle.Event.*;

/**
 * Created by vn008xw on 8/20/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements LifecycleRegistryOwner {

  private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);

  @Override
  public LifecycleRegistry getLifecycle() {
    return mRegistry;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mRegistry.handleLifecycleEvent(ON_CREATE);
  }

  @Override
  protected void onStart() {
    super.onStart();
    mRegistry.handleLifecycleEvent(ON_START);
  }

  @Override
  protected void onResume() {
    super.onResume();
    mRegistry.handleLifecycleEvent(ON_RESUME);
  }

  @Override
  protected void onPause() {
    super.onPause();
    mRegistry.handleLifecycleEvent(ON_PAUSE);
  }

  @Override
  protected void onStop() {
    super.onStop();
    mRegistry.handleLifecycleEvent(ON_STOP);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mRegistry.handleLifecycleEvent(ON_DESTROY);
  }
}
