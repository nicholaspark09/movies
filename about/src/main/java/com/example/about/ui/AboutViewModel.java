package com.example.about.ui;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.example.about.data.AboutRepository;
import com.example.about.di.AboutComponent;
import com.example.about.di.AboutInjector;
import com.example.vn008xw.golf.util.AbsentData;
import com.example.vn008xw.golf.vo.Item;
import com.example.vn008xw.golf.vo.Resource;

import javax.inject.Inject;

public class AboutViewModel extends AndroidViewModel {

  private static final String TAG = AboutViewModel.class.getSimpleName();

  @Inject AboutRepository aboutRepository;
  @VisibleForTesting
  LiveData<Resource<Item>> item;
  @VisibleForTesting
  final MutableLiveData<String> upc = new MutableLiveData<>();

  public AboutViewModel(Application application) {
    this(AboutInjector.initAndGetComponent(application), application);
  }

  public AboutViewModel(AboutComponent aboutComponent, Application application) {
    super(application);

    aboutComponent.inject(this);

    item = Transformations.switchMap(upc, input -> {
      if (upc == null || upc.getValue() == null) {
        Log.d(TAG, "Returning null");
        return AbsentData.create();
      }
      Log.d(TAG, "This is being called");
      return aboutRepository.findItem(upc.getValue());
    });
  }

  LiveData<Resource<Item>> loadItem() {
    return item;
  }

  public void setUpc(@NonNull String query) {
    Log.d(TAG, "Setting the upc to " + query);
    upc.setValue(query);
    Log.d(TAG, "The value of the upc is: " + upc.getValue());
    Log.d(TAG, "Upc has observers: " + upc.hasActiveObservers());
  }
}
