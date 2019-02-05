package com.example.android.moviesapp.model;

import android.arch.lifecycle.LiveData;

import com.example.android.moviesapp.PopularViewModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesList {

    @SerializedName("results")
    @Expose
    private List<Movie> movies = null;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
