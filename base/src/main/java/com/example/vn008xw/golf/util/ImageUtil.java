package com.example.vn008xw.golf.util;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.vn008xw.carbeat.data.vo.Poster;
import com.example.vn008xw.golf.R;

/**
 * Created by vn008xw on 8/26/17.
 */

public final class ImageUtil {

  private static final String BASE = "https://image.tmdb.org/t/p";
  private static final String[] SIZES = {"/w185/", "/w500/"};

  private ImageUtil() {
    throw new AssertionError("No instances, poop.");
  }

  private static String getSmallPath() {
    return BASE + SIZES[0];
  }
  private static String getLargePath() { return BASE + SIZES[1]; }

  public static void loadImage(@NonNull ImageView imageView, @NonNull String url) {
    final String endpoint = getSmallPath() + url;
    Glide.with(imageView.getContext())
            .load(endpoint)
            .placeholder(R.drawable.loading)
            .error(R.drawable.error)
            .into(imageView);
  }

  public static void loadLargeImage(@NonNull ImageView imageView, @NonNull Poster poster) {
    final String endpoint = getLargePath() + poster.getFilePath();
    Glide.with(imageView.getContext())
            .load(endpoint)
            .placeholder(R.drawable.loading)
            .error(R.drawable.error)
            .override(poster.getWidth(), poster.getHeight())
            .diskCacheStrategy(DiskCacheStrategy.RESULT)
            .centerCrop()
            .into(imageView);
  }
}
