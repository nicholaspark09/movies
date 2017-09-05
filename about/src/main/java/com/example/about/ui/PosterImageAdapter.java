package com.example.about.ui;


import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.about.R;
import com.example.vn008xw.carbeat.data.vo.Poster;
import com.example.vn008xw.golf.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

public final class PosterImageAdapter extends PagerAdapter {

  private final List<Poster> mItems = new ArrayList<>();
  private final PosterListener mListener;

  public PosterImageAdapter(@NonNull List<Poster> posters,
                            @NonNull PosterListener listener) {
    mItems.addAll(posters);
    mListener = listener;
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    final ImageView view = (ImageView) LayoutInflater.from(container.getContext()).inflate(R.layout.about_poster_layout, container, false);
    final Poster poster = mItems.get(position);
    container.addView(view);
    ImageUtil.loadLargeImage(view, poster);
    view.setOnClickListener(v -> mListener.onPosterClicked((ImageView) v, position));
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

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((View) object);
  }

  interface PosterListener {
    void onPosterClicked(@NonNull ImageView imageView,
                         @NonNull int position);
  }
}