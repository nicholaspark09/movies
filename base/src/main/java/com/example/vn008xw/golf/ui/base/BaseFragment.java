package com.example.vn008xw.golf.ui.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.example.vn008xw.golf.R;

import static android.arch.lifecycle.Lifecycle.Event.ON_CREATE;
import static android.arch.lifecycle.Lifecycle.Event.ON_DESTROY;
import static android.arch.lifecycle.Lifecycle.Event.ON_PAUSE;
import static android.arch.lifecycle.Lifecycle.Event.ON_RESUME;
import static android.arch.lifecycle.Lifecycle.Event.ON_START;
import static android.arch.lifecycle.Lifecycle.Event.ON_STOP;

/**
 * Base Fragment class
 * - Instant Apps lifecyclefragments don't actually register the lifecycle for some reason
 * - Need to make it a lifecycle owner manually
 */

public class BaseFragment extends Fragment implements LifecycleOwner {

  private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);

  @Override
  public Lifecycle getLifecycle() {
    return mRegistry;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mRegistry.handleLifecycleEvent(ON_CREATE);
  }

  @Override
  public void onStart() {
    super.onStart();
    mRegistry.handleLifecycleEvent(ON_START);
  }

  @Override
  public void onResume() {
    super.onResume();
    mRegistry.handleLifecycleEvent(ON_RESUME);
  }

  @Override
  public void onPause() {
    super.onPause();
    mRegistry.handleLifecycleEvent(ON_PAUSE);
  }

  @Override
  public void onStop() {
    super.onStop();
    mRegistry.handleLifecycleEvent(ON_STOP);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mRegistry.handleLifecycleEvent(ON_DESTROY);
  }

  public void handleError(@Nullable String message) {
    final String errorMessage;
    errorMessage = message == null ? getString(R.string.error_generic) : message;
    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    Log.d("Error", errorMessage);
  }
}
