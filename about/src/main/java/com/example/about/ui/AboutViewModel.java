package com.example.about.ui;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.example.about.data.AboutRepository;
import com.example.about.di.AboutComponent;
import com.example.about.di.AboutInjector;
import com.example.vn008xw.golf.util.AbsentData;
import com.example.vn008xw.golf.vo.Item;
import com.example.vn008xw.golf.vo.Resource;

import javax.inject.Inject;

public class AboutViewModel extends AndroidViewModel {

  private static final String TAG = AboutViewModel.class.getSimpleName();

  @Inject
  AboutRepository aboutRepository;
  @VisibleForTesting
  final MutableLiveData<String> upc = new MutableLiveData<>();
  @VisibleForTesting
  final LiveData<Resource<Item>> item = Transformations.switchMap(upc, upc -> {
    if (upc == null) return AbsentData.create();
    return aboutRepository.findItem("");
  });

  public AboutViewModel(Application application) {
    this(AboutInjector.initAndGetComponent(application), application);
  }

  public AboutViewModel(AboutComponent aboutComponent, Application application) {
    super(application);
    aboutComponent.inject(this);
  }

  LiveData<Resource<Item>> loadItem() {
    return item;
  }

  public void setUpc(@NonNull String query) {
    upc.setValue(query);
  }
}
