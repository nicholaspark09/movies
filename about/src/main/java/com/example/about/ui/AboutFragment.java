package com.example.about.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.about.R;
import com.example.about.data.AboutRepository;
import com.example.about.di.AboutInjector;

import javax.inject.Inject;

public class AboutFragment extends LifecycleFragment {

  private static final String UPC_KEY = "args:item_upc";

  @Inject
  AboutRepository aboutRepository;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    Log.d("AboutFragment", "In the on create");
    return inflater.inflate(R.layout.fragment_blank, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // Inject here
    AboutInjector.initAndGetComponent(getContext()).inject(this);
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    AboutViewModel viewModel = ViewModelProviders.of(this).get(AboutViewModel.class);
    viewModel.loadItem().observe(this, response -> {
      Log.d("Fragment", "You got a response: " + response.data);
    });
    viewModel.setUpc(getArguments().getString(UPC_KEY, ""));
  }

  public static AboutFragment create() {
    final AboutFragment fragment = new AboutFragment();
    return fragment;
  }

  public static AboutFragment create(@NonNull String upc) {
    Log.d("AboutFragment", "Creating fragment");
    final AboutFragment fragment = create();
    final Bundle bundle = new Bundle();
    bundle.putString(UPC_KEY, upc);
    fragment.setArguments(bundle);
    return fragment;
  }
}
