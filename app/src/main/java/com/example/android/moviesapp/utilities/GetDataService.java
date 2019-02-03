package com.example.android.moviesapp.utilities;

import com.example.android.moviesapp.BuildConfig;
import com.example.android.moviesapp.model.Movie;
import com.example.android.moviesapp.model.MoviesList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetDataService {

    @GET("{path}?api_key=" + BuildConfig.API_KEY)
    Call<MoviesList> getAllMovies(@Path("path") String path);
}
