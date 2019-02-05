package com.example.android.moviesapp.utilities;

import android.arch.lifecycle.LiveData;

import com.example.android.moviesapp.BuildConfig;
import com.example.android.moviesapp.model.MoviesList;
import com.example.android.moviesapp.model.ReviewsList;
import com.example.android.moviesapp.model.TrailersList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetDataService {

    @GET("{path}?api_key=" + BuildConfig.API_KEY)
    Call<MoviesList> getAllMovies(@Path("path") String path);

    @GET("{movieId}/videos?api_key=" + BuildConfig.API_KEY)
    Call<TrailersList> getAllTrailers(@Path("movieId") String movieId);

    @GET("{movieId}/reviews?api_key=" + BuildConfig.API_KEY)
    Call<ReviewsList> getAllReviews(@Path("movieId") String movieId);
}
