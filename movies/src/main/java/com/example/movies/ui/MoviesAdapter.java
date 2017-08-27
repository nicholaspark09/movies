package com.example.movies.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movies.R;
import com.example.vn008xw.carbeat.data.vo.Movie;
import com.example.vn008xw.golf.util.ImageUtil;
import com.example.vn008xw.golf.view.LiveBoundAdapter;

public class MoviesAdapter extends LiveBoundAdapter<Movie, RecyclerView.ViewHolder> {

  private final MovieClickCallback mCallback;

  public MoviesAdapter(@NonNull MovieClickCallback callback) {
    mCallback = callback;
  }


  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movie_row, parent, false);
    final MovieViewHolder viewHolder = new MovieViewHolder(view);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    final Movie movie = items.get(position);
    final MovieViewHolder viewHolder = (MovieViewHolder) holder;
    viewHolder.titleView.setText(movie.getTitle());
    viewHolder.itemView.setOnClickListener(v -> {
      mCallback.onClick(movie);
    });

    // Load Image
    ImageUtil.loadImage(viewHolder.imageView, movie.getPosterPath());
  }

  @Override
  protected boolean areItemsTheSame(Movie oldItem, Movie newItem) {
    return oldItem.getId() == newItem.getId() && oldItem.equals(newItem);
  }

  @Override
  protected boolean areContentsTheSame(Movie oldItem, Movie newItem) {
    return oldItem.getTitle() == newItem.getTitle() && oldItem.equals(newItem);
  }

  public final class MovieViewHolder extends RecyclerView.ViewHolder {

    final TextView titleView;
    final ImageView imageView;

    public MovieViewHolder(View itemView) {
      super(itemView);
      titleView = itemView.findViewById(R.id.title);
      imageView = itemView.findViewById(R.id.image_view);
    }
  }

  public interface MovieClickCallback {
    void onClick(@NonNull Movie movie);
  }
}
