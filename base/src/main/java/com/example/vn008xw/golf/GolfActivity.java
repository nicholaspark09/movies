package com.example.vn008xw.golf;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by vn008xw on 8/11/17.
 */

public class GolfActivity extends AppCompatActivity
        implements LifecycleRegistryOwner{

  private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);

  @Override
  public LifecycleRegistry getLifecycle() {
    return mRegistry;
  }
}
