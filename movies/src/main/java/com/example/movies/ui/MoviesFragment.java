package com.example.movies.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.movies.R;
import com.example.movies.di.MoviesInjector;
import com.example.vn008xw.golf.ui.base.BaseFragment;
import com.example.vn008xw.golf.util.IntentUtil;
import com.example.vn008xw.golf.vo.AutoClearedValue;
import com.example.vn008xw.golf.vo.Resource;

import timber.log.Timber;


/**
 * Acts as the `view` for the viewmodel
 * Contains the UI Logic
 */
public class MoviesFragment extends BaseFragment {

  private static final String TAG = MoviesFragment.class.getSimpleName();

  private AutoClearedValue<RecyclerView> mRecyclerView;
  private AutoClearedValue<ProgressBar> mProgressBar;
  private AutoClearedValue<MoviesAdapter> mMoviesAdapter;
  private MoviesViewModel mViewModel;

  public MoviesFragment() {
    // Required empty public constructor
  }

  public static MoviesFragment newInstance() {
    final MoviesFragment fragment = new MoviesFragment();
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
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    mRecyclerView = new AutoClearedValue<>(this, getView().findViewById(R.id.recyclerView));
    mProgressBar = new AutoClearedValue<>(this, getView().findViewById(R.id.progress_bar));
    setupAdapter();

    mViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
    mViewModel.loadMovies().observe(this, response -> {
      setLoading(response.status);
      if (response.status == Resource.Status.SUCCESS) {
        if (mMoviesAdapter.get() != null) {
          mMoviesAdapter.get().replace(response.data);
        }
      } else if (response.status == Resource.Status.ERROR) {
        Log.d(TAG, "Error: " + response.message);
        Toast.makeText(getContext(), response.message, Toast.LENGTH_LONG).show();
      }
    });
    mViewModel.setPage(1);
  }

  @Override
  public void onDetach() {
    super.onDetach();
  }

  private void setLoading(@NonNull Resource.Status status) {
    if (mProgressBar.get() != null) {
      mProgressBar.get().setVisibility(status == Resource.Status.LOADING ? View.VISIBLE : View.GONE);
    }
  }

  private void setupAdapter() {
    final MoviesAdapter adapter = new MoviesAdapter(movie -> {
      // Send them to another instant app just to see if it works
      final Intent intent = IntentUtil.getInstantAppIntent(getContext(), "https://golfstory.com/about");
      startActivity(intent);
    });
    mMoviesAdapter = new AutoClearedValue<>(this, adapter);
    mRecyclerView.get().setLayoutManager(new LinearLayoutManager(getContext()));
    mRecyclerView.get().setAdapter(adapter);
  }
}