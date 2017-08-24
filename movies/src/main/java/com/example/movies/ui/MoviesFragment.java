package com.example.movies.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movies.R;
import com.example.movies.di.MoviesInjector;
import com.example.vn008xw.golf.ui.base.BaseFragment;

public class MoviesFragment extends BaseFragment {


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
  public void onAttach(Context context) {
    super.onAttach(context);
    MoviesInjector.initAndGetComponent(context).inject(this);
  }

  @Override
  public void onDetach() {
    super.onDetach();
  }

}
