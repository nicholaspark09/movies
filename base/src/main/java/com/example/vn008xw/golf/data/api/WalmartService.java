package com.example.vn008xw.golf.data.api;

import android.arch.lifecycle.LiveData;

import com.example.vn008xw.golf.vo.ApiResponse;
import com.example.vn008xw.golf.vo.ItemsResult;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WalmartService {
  @GET("/items")
  LiveData<ApiResponse<ItemsResult>> findItemByUpc(@Query("apiKey") String apiKey, @Query("upc") String upc);
}
