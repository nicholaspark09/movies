package com.example.about.di;

import android.content.Context;

/**
 * Helper class to automatically inject fragments
 */
public class AboutInjector {

  private static AboutComponent aboutComponent;

  private AboutInjector() { }

  public static AboutComponent initAndGetComponent(Context context) {
    if (aboutComponent == null) {
      aboutComponent = DaggerAboutComponent
              .builder()
              .bindContext(context)
              .build();
    }
    return aboutComponent;
  }
}
