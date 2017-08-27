package com.example.vn008xw.golf.view;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Instant apps doesn't yet support databinding
 * <p>
 * Need an adapter that uses the least amount of changes
 * Pushes the changes to the views
 */
public abstract class LiveBoundAdapter<T, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  @Nullable
  protected final List<T> items = new ArrayList<>();

  private int dataVersion = 0;

  @SuppressLint("StaticFieldLeak")
  @MainThread
  public void replace(List<T> update) {
    ++dataVersion;
    if (items.size() == 0) {
      if (update == null) return;
      items.addAll(update);
      notifyDataSetChanged();
    } else if (update == null) {
      int oldSize = items.size();
      items.clear();
      notifyItemRangeRemoved(0, oldSize);
    } else {

      final int startVersion = dataVersion;
      final List<T> oldItems = items;

      new AsyncTask<Void, Void, DiffUtil.DiffResult>() {

        /**
         *  Calculates what needs to be changed on a worker thread
         * @param voids
         * @return
         */
        @Override
        protected DiffUtil.DiffResult doInBackground(Void... voids) {

          return DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
              return oldItems.size();
            }

            @Override
            public int getNewListSize() {
              return update.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
              T oldItem = oldItems.get(oldItemPosition);
              T newItem = update.get(newItemPosition);
              return LiveBoundAdapter.this.areItemsTheSame(oldItem, newItem);
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
              T oldItem = oldItems.get(oldItemPosition);
              T newItem = update.get(newItemPosition);
              return LiveBoundAdapter.this.areContentsTheSame(oldItem, newItem);
            }
          });
        }

        @Override
        protected void onPostExecute(DiffUtil.DiffResult diffResult) {
          if (startVersion != dataVersion) return;
          items.clear();
          items.addAll(update);
          diffResult.dispatchUpdatesTo(LiveBoundAdapter.this);
        }
      }.execute();
    }
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  protected abstract boolean areItemsTheSame(T oldItem, T newItem);

  protected abstract boolean areContentsTheSame(T oldItem, T newItem);
}