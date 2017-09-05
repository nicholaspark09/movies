package com.example.about.ui.movieimages;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.about.R;
import com.example.vn008xw.carbeat.data.vo.Poster;
import com.example.vn008xw.golf.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

public final class ScaledImageAdapter extends PagerAdapter {

  private final List<Poster> mItems = new ArrayList<>();
  private final String mTransitionName;
  private final int mStartingPosition;
  private final OnAdapterReadyListener mAdapterListener;
  private boolean mHasSet = false;


  public ScaledImageAdapter(@NonNull List<Poster> posters,
                            @NonNull String transitionName,
                            @NonNull int startingPosition,
                            @NonNull OnAdapterReadyListener listener) {
    mTransitionName = transitionName;
    mItems.addAll(posters);
    mStartingPosition = startingPosition;
    mAdapterListener = listener;
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    final SubsamplingScaleImageView view =
            (SubsamplingScaleImageView) LayoutInflater.from(container.getContext()).inflate(R.layout.scaled_image_layout, container, false);
    final Poster poster = mItems.get(position);
    ImageUtil.loadScalableImage(view, poster);
    container.addView(view);
    doTransition(view, position);
    return view;
  }

  @Override
  public int getCount() {
    return mItems.size();
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  private void doTransition(@NonNull View view, int position) {
    if (position == mStartingPosition && !mHasSet) {
      view.setTransitionName(mTransitionName);
      view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
        @Override
        public boolean onPreDraw() {
          view.getViewTreeObserver().removeOnPreDrawListener(this);
          Log.d("ImageAdapter", "Should be trying to start the transition now");
          mAdapterListener.startTransition();
          return true;
        }
      });
      mHasSet = true;
    }
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((View) object);
  }

  interface OnAdapterReadyListener {
    void startTransition();
  }
}
