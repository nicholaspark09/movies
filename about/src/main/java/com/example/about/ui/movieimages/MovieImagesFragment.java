package com.example.about.ui.movieimages;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.about.R;
import com.example.about.di.AboutInjector;
import com.example.about.ui.AboutViewModel;
import com.example.vn008xw.golf.ui.base.BaseFragment;
import com.example.vn008xw.golf.vo.AutoClearedValue;

public class MovieImagesFragment extends BaseFragment {

  private static final String TAG = MovieImagesFragment.class.getSimpleName();
  private static final String MOVIE_ID_KEY = "args:movie_id";
  private static final String MOVIE_TITLE_KEY = "args:movie_title";
  private static final String TRANSITION_KEY = "args:transition_name";
  private static final String POSITION_KEY = "args:position";

  private AutoClearedValue<ViewPager> mViewPager;
  private AutoClearedValue<ScaledImageAdapter> mImageAdapter;
  private AboutViewModel mViewModel;
  private String mTransitionName;
  private int mMovieId;
  private int mStartingPosition;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mStartingPosition = getArguments().getInt(POSITION_KEY, 0);
    mTransitionName = getArguments().getString(TRANSITION_KEY);
    mMovieId = getArguments().getInt(MOVIE_ID_KEY);
    postponeEnterTransition();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    final View root = inflater.inflate(R.layout.fragment_movie_images, container, false);
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

    mViewPager = new AutoClearedValue<>(this, view.findViewById(R.id.view_pager));
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {

    mViewModel = ViewModelProviders.of(this).get(AboutViewModel.class);
    mViewModel.loadPosters().observe(this, response -> {
      if (mViewPager.get() != null && response.data != null) {
        final ScaledImageAdapter adapter =
                new ScaledImageAdapter(response.data.subList(0, 6), mTransitionName, mStartingPosition, () -> {
                  // ready to begin transition
                  startPostponedEnterTransition();
                });
        mImageAdapter = new AutoClearedValue<ScaledImageAdapter>(this, adapter);
        mViewPager.get().setAdapter(adapter);
        mViewPager.get().setCurrentItem(mStartingPosition);
      }
    });

    mViewModel.setMovieId(mMovieId);
    super.onActivityCreated(savedInstanceState);
  }

  public static MovieImagesFragment create(@NonNull Integer movieId,
                                           @NonNull String movieTitle,
                                           @NonNull String transitionName,
                                           @NonNull int position) {
    final MovieImagesFragment fragment = new MovieImagesFragment();
    final Bundle bundle = new Bundle();
    bundle.putInt(MOVIE_ID_KEY, movieId);
    bundle.putString(MOVIE_TITLE_KEY, movieTitle);
    bundle.putString(TRANSITION_KEY, transitionName);
    bundle.putInt(POSITION_KEY, position);
    fragment.setArguments(bundle);
    return fragment;
  }
}