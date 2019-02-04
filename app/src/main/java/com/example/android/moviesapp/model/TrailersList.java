package com.example.android.moviesapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailersList {

    @SerializedName("results")
    @Expose
    private List<MovieTrailer> trailers = null;

    public List<MovieTrailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<MovieTrailer> trailers) {
        this.trailers = trailers;
    }
}
