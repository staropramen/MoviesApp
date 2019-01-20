package com.example.android.moviesapp.utilities;

import android.util.Log;

public class NetworkUtils {

    //Tag for Logging
    private static final String TAG = NetworkUtils.class.getSimpleName();

    //Base URL for API request
    private static final String MOVIE_DATABASE_URL_POPULAR =
            "http://api.themoviedb.org/3/movie/popular";

    //API Key
    private static final String API_KEY =
            "673ca6ed38cbb77de6d3a17df821abd5";

    // TODO Delete Debug Function
    public static void check(){
        Log.v(TAG, MOVIE_DATABASE_URL_POPULAR);
    }
}
