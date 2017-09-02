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

  private final List<Poster> items = new ArrayList<>();

  public PosterImageAdapter(@NonNull List<Poster> posters) {
    items.addAll(posters);
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    final ImageView view = (ImageView) LayoutInflater.from(container.getContext()).inflate(R.layout.about_poster_layout, container, false);
    final Poster poster = items.get(position);
    container.addView(view);
    ImageUtil.loadLargeImage(view, poster);
    view.setOnClickListener(v -> {

    });
    return view;
  }

  @Override
  public int getCount() {
    return items.size();
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((View) object);
  }
}