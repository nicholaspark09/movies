package com.example.about.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.about.R;
import com.example.about.di.AboutInjector;
import com.example.vn008xw.golf.ui.base.BaseFragment;
import com.example.vn008xw.golf.vo.AutoClearedValue;
import com.example.vn008xw.golf.vo.Resource;

public class AboutFragment extends BaseFragment {

  private static final String TAG = AboutFragment.class.getSimpleName();
  private static final String UPC_KEY = "args:item_upc";

  // Instant app feature modules don't actually support databinding for now
  private AboutViewModel viewModel;
  private AutoClearedValue<Toolbar> toolbar;
  private AutoClearedValue<ImageView> imageView;
  private AutoClearedValue<ProgressBar> progressBar;

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
    imageView = new AutoClearedValue<>(this, view.findViewById(R.id.image));
    progressBar = new AutoClearedValue<>(this, view.findViewById(R.id.progress_bar));
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {

    viewModel = ViewModelProviders.of(this).get(AboutViewModel.class);
    viewModel.loadItem().observe(this, response -> {

      setLoading(response.status);
      if (response.status == Resource.Status.SUCCESS) {
        toolbar.get().setTitle(response.data.getName());
      }else if (response.status == Resource.Status.ERROR) {
        handleError(response.message);
      }
    });

    viewModel.setUpc(getArguments().getString(UPC_KEY, ""));

    super.onActivityCreated(savedInstanceState);
  }

  private void setLoading(@NonNull Resource.Status status) {
    progressBar.get().setVisibility(status == Resource.Status.LOADING ? View.VISIBLE : View.GONE);
  }

  public static AboutFragment create(@NonNull String upc) {
    Log.d("AboutFragment", "Creating fragment");
    final AboutFragment fragment = new AboutFragment();
    final Bundle bundle = new Bundle();
    bundle.putString(UPC_KEY, upc);
    fragment.setArguments(bundle);
    return fragment;
  }

}
