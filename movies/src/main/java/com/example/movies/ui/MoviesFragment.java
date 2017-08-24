package com.example.movies.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movies.R;

public class MoviesFragment extends Fragment {


  public MoviesFragment() {
    // Required empty public constructor
  }

  public static MoviesFragment newInstance(String param1, String param2) {
    MoviesFragment fragment = new MoviesFragment();
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_movies, container, false);
  }


  @Override
  public void onDetach() {
    super.onDetach();
  }

}
