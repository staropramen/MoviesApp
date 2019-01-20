package com.example.android.moviesapp.utilities;

import android.util.Log;

public class NetworkUtils {

    //Tag for Logging
    private static final String TAG = NetworkUtils.class.getSimpleName();

    //Base URL for API request
    private static final String MOVIE_DATABASE_URL_POPULAR =
            "http://api.themoviedb.org/3/movie/popular";

    public static void check(){
        Log.v(TAG, MOVIE_DATABASE_URL_POPULAR);
    }
}
