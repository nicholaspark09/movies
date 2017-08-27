package com.example.about.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.about.R;
import com.example.about.di.AboutInjector;
import com.example.vn008xw.golf.ui.base.BaseFragment;
import com.example.vn008xw.golf.vo.AutoClearedValue;
import com.example.vn008xw.golf.vo.Resource;

public class AboutFragment extends BaseFragment {

  private static final String TAG = AboutFragment.class.getSimpleName();
  private static final String MOVIE_KEY = "args:movie_id";

  // Instant app feature modules don't actually support databinding for now
  private AboutViewModel viewModel;
  private AutoClearedValue<Toolbar> toolbar;
  private AutoClearedValue<ViewPager> viewPager;
  private AutoClearedValue<ProgressBar> progressBar;
  private AutoClearedValue<TextView> description;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_about, container, false);
    setHasOptionsMenu(true);
    return root;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    AboutInjector.initAndGetComponent(getContext()).inject(this);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    toolbar = new AutoClearedValue<>(this, view.findViewById(R.id.toolbar));
    viewPager = new AutoClearedValue<>(this, view.findViewById(R.id.view_pager));
    progressBar = new AutoClearedValue<>(this, view.findViewById(R.id.progress_bar));
    description = new AutoClearedValue<>(this, view.findViewById(R.id.description));
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {

    viewModel = ViewModelProviders.of(this).get(AboutViewModel.class);
    viewModel.loadMovie().observe(this, response -> {

      setLoading(response.status);
      if (response.status == Resource.Status.SUCCESS) {
        toolbar.get().setTitle(response.data.getTitle());
        description.get().setText(response.data.getOverview());
        Log.d(TAG, "You got the movie");
      } else if (response.status == Resource.Status.ERROR) {
        handleError(response.message);
      }
    });

    viewModel.setMovieId(getArguments().getInt(MOVIE_KEY));

    super.onActivityCreated(savedInstanceState);
  }

  private void setLoading(@NonNull Resource.Status status) {
    progressBar.get().setVisibility(status == Resource.Status.LOADING ? View.VISIBLE : View.GONE);
  }

  public static AboutFragment create(@NonNull Integer movieId) {
    final AboutFragment fragment = new AboutFragment();
    final Bundle bundle = new Bundle();
    bundle.putInt(MOVIE_KEY, movieId);
    fragment.setArguments(bundle);
    return fragment;
  }
}