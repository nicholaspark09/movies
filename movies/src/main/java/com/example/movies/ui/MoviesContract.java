package com.example.movies.ui;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.vn008xw.carbeat.data.vo.Movie;
import com.example.vn008xw.golf.vo.Resource;

import java.util.List;

/**
 *  Specifies methods viewmodel of Movies must have
 */
public interface MoviesContract {

    LiveData<Resource<List<Movie>>> loadMovies();

    void setPage(@NonNull Integer page);
}
