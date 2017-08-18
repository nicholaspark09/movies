package com.example.about;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.about.di.AboutInjector;
import com.example.about.ui.AboutFragment;

public class AboutActivity extends AppCompatActivity
        implements LifecycleRegistryOwner {

  private static final String FAKE_UPC = "035000521019";
  private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    AboutInjector.initAndGetComponent(this).inject(this);
    super.onCreate(savedInstanceState);

    Log.d("AboutFragment", "In the activity");
    setContentView(R.layout.activity_about);


    AboutFragment fragment =
            (AboutFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
    if (fragment == null) {
      fragment = AboutFragment.create(FAKE_UPC);
      getSupportFragmentManager()
              .beginTransaction()
              .replace(R.id.contentFrame, fragment)
              .commit();
    }
  }

  @Override
  public LifecycleRegistry getLifecycle() {
    return lifecycleRegistry;
  }

}
