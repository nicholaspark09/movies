package com.example.about.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
  private static final String TITLE_KEY = "args:movie_title";

  // Instant app feature modules don't actually support databinding for now
  private AboutViewModel mViewModel;
  private AutoClearedValue<Toolbar> mToolbar;
  private AutoClearedValue<ViewPager> mViewPager;
  private AutoClearedValue<ProgressBar> mProgressBar;
  private AutoClearedValue<TextView> mDescription;
  private AutoClearedValue<TabLayout> mTabLayout;
  private AutoClearedValue<PosterImageAdapter> mAdapter;

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


    mToolbar = new AutoClearedValue<>(this, view.findViewById(R.id.toolbar));
    mViewPager = new AutoClearedValue<>(this, view.findViewById(R.id.view_pager));
    mProgressBar = new AutoClearedValue<>(this, view.findViewById(R.id.progress_bar));
    mDescription = new AutoClearedValue<>(this, view.findViewById(R.id.description));
    mTabLayout = new AutoClearedValue<>(this, view.findViewById(R.id.tab_layout));
    setActionBar(mToolbar.get());
    getActionBar().setDisplayShowHomeEnabled(true);
    getActionBar().setDisplayHomeAsUpEnabled(true);
    getActionBar().setDisplayShowTitleEnabled(false);
    mToolbar.get().setTitle(getArguments().getString(TITLE_KEY));
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {

    mViewModel = ViewModelProviders.of(this).get(AboutViewModel.class);
    mViewModel.loadMovie().observe(this, response -> {

      setLoading(response.status);
      if (response.status == Resource.Status.SUCCESS) {
        mDescription.get().setText(response.data.getOverview());

      } else if (response.status == Resource.Status.ERROR) {
        handleError(response.message);
      }
    });

    mViewModel.loadPosters().observe(this, response -> {

      if (response.status == Resource.Status.SUCCESS) {
        if (mViewPager.get() != null) {
          final PosterImageAdapter posterImageAdapter = new PosterImageAdapter(response.data.subList(0, 7));
          mAdapter = new AutoClearedValue<>(AboutFragment.this, posterImageAdapter);
          mViewPager.get().setAdapter(posterImageAdapter);
          mTabLayout.get().setupWithViewPager(mViewPager.get());
        }
      } else if (response.status == Resource.Status.ERROR) {
        handleError(response.message);
      }
    });

    mViewModel.setMovieId(getArguments().getInt(MOVIE_KEY));

    super.onActivityCreated(savedInstanceState);
  }

  private void setLoading(@NonNull Resource.Status status) {
    mProgressBar.get().setVisibility(status == Resource.Status.LOADING ? View.VISIBLE : View.GONE);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        getActivity().finish();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  public static AboutFragment create(@NonNull Integer movieId, @NonNull String movieTitle) {
    final AboutFragment fragment = new AboutFragment();
    final Bundle bundle = new Bundle();
    bundle.putInt(MOVIE_KEY, movieId);
    bundle.putString(TITLE_KEY, movieTitle);
    fragment.setArguments(bundle);
    return fragment;
  }
}