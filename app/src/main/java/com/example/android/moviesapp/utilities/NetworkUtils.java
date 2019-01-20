package com.example.android.moviesapp.utilities;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {

    //Tag for Logging
    private static final String TAG = NetworkUtils.class.getSimpleName();

    //Base URL for API request
    private static final String MOVIE_DATABASE_URL_POPULAR =
            "http://api.themoviedb.org/3/movie/popular";

    //API Key
    private static final String API_KEY =
            "673ca6ed38cbb77de6d3a17df821abd5";

    //Query Parameters
    final static String KEY_PARAM = "api_key";

    //Build Url to query
    public static URL builtUrl(){
        Uri builtUri = Uri.parse(MOVIE_DATABASE_URL_POPULAR).buildUpon()
                .appendQueryParameter(KEY_PARAM, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        Log.v(TAG, "Built Url " + url);
        return url;
    }


}
