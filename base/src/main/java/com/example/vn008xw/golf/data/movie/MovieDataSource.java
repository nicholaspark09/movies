package com.example.vn008xw.golf.data.movie;


import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.vn008xw.carbeat.data.vo.Movie;
import com.example.vn008xw.carbeat.data.vo.Poster;
import com.example.vn008xw.golf.vo.Resource;

import java.util.List;

public interface MovieDataSource {

  LiveData<Resource<List<Movie>>> getMovies(@NonNull Integer page);

  LiveData<Resource<List<Poster>>> getImages(@NonNull Integer movieId);
}
