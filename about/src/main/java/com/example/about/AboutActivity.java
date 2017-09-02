package com.example.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Pair;
import android.widget.Toast;

import com.example.about.ui.AboutFragment;
import com.example.vn008xw.golf.ui.base.BaseActivity;

public class AboutActivity extends BaseActivity {

  private static final String FAKE_UPC = "035000521019";
  private static final String QUERY_MOVIE_KEY = "movieId";
  private static final String QUERY_MOVIE_TITLE = "title";
  private Integer movieId;
  private String movieTitle;


  @Override
  protected void onCreate(Bundle savedInstanceState) {


    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_about);


    // Get the movieId
    if (savedInstanceState == null) {
      try {
        final Pair<Integer, String> movieInfo = getMovieInfo(getIntent());
        movieId = movieInfo.first;
        movieTitle = movieInfo.second;
      } catch (NumberFormatException e) {
        e.printStackTrace();
        Toast.makeText(this, "Couldn't find the movie id. Sorry.", Toast.LENGTH_SHORT).show();
      }
    } else {
      movieId = savedInstanceState.getInt(QUERY_MOVIE_KEY);
      movieTitle = savedInstanceState.getString(QUERY_MOVIE_TITLE);
    }

    AboutFragment fragment =
            (AboutFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
    if (fragment == null) {
      fragment = AboutFragment.create(movieId, movieTitle);
    }
    getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.contentFrame, fragment)
            .commitAllowingStateLoss();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    if (movieId != null) {
      outState.putInt(QUERY_MOVIE_KEY, movieId);
      outState.putString(QUERY_MOVIE_TITLE, movieTitle);
    }
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    movieId = savedInstanceState.getInt(QUERY_MOVIE_KEY);
  }

  private Pair<Integer, String> getMovieInfo(@NonNull Intent intent) throws NumberFormatException {
    final Uri data = intent.getData();
    final String movieIdText = data.getQueryParameter(QUERY_MOVIE_KEY);
    final String title = data.getQueryParameter(QUERY_MOVIE_TITLE);
    final Integer movieId = Integer.valueOf(movieIdText);
    return Pair.create(movieId, title);
  }
}