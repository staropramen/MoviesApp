package com.example.android.moviesapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.android.moviesapp.model.Movie;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> movies;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }
}
