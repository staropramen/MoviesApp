package com.example.android.moviesapp.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

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

    //Return response from Http request
    public static String getHttpresponse(URL url) throws IOException{
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream inputStream = httpURLConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);

            boolean hasInput = scanner.hasNext();

            if(hasInput){
                Log.v(TAG, "Sream " + scanner.next());
                return scanner.next();
            }else {
                return null;
            }
        }finally {
            httpURLConnection.disconnect();
        }
    }


}
