package com.example.vn008xw.golf.data.api;


import android.arch.lifecycle.LiveData;

import com.example.vn008xw.carbeat.data.vo.ImageResult;
import com.example.vn008xw.carbeat.data.vo.SearchResult;
import com.example.vn008xw.golf.vo.ApiResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

  @GET("/3/discover/movie")
  LiveData<ApiResponse<SearchResult>> discoverByYear(@Query("api_key") String apiKey,
                                                     @Query("page") Integer page,
                                                     @Query("primary_release_year") String year);

  @GET("/3/movie/{movieId}/images")
  LiveData<ApiResponse<ImageResult>> getImages(@Path("movieId") Integer movieId,
                                               @Query("api_key") String apiKey);
}
