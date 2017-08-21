package com.example.about;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.example.about.ui.AboutFragment;
import com.example.vn008xw.golf.ui.base.BaseActivity;

public class AboutActivity extends BaseActivity {

  private static final String FAKE_UPC = "035000521019";


  @Override
  protected void onCreate(Bundle savedInstanceState) {


    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_about);

    AboutFragment fragment =
            (AboutFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
    if (fragment == null) {
      fragment = AboutFragment.create(FAKE_UPC);
    }
    getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.contentFrame, fragment)
            .commitAllowingStateLoss();
  }
}
