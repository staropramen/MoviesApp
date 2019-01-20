package com.example.android.moviesapp.utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OpenMovieJsonUtils {

    //Get Strings from JSON
    public static ArrayList<String[]> getStringsFromJson(Context context, String movieJsonStr) throws JSONException {

        //Objects to retrieve in variables
        final String MAIN_ARRAY = "results";
        final String MOVIE_TITLE = "original_title";
        final String RELEASE_DATE = "release_date";
        final String POSTER_PATH = "poster_path";
        final String AVERAGE_VOTE = "vote_average";
        final String PLOT_SYNOPSIS = "overview";

        //Make String a JsonObject
        JSONObject movieJson = new JSONObject(movieJsonStr);

        //Initialize String Array to store Data and set it to null
        ArrayList<String[]> moviesArray = new ArrayList<>();

        //Get Array with the movies inside
        JSONArray moviesJsonArray = movieJson.getJSONArray(MAIN_ARRAY);

        //Store Movies Inormation in Array inside moviesArray
        for (int i = 0; i < moviesJsonArray.length(); i++){
            String[] singleMovie = new String[5];

            JSONObject currentMovie = moviesJsonArray.getJSONObject(i);

            singleMovie[0] = currentMovie.getString(MOVIE_TITLE);
            singleMovie[1] = currentMovie.getString(RELEASE_DATE);
            singleMovie[2] = currentMovie.getString(POSTER_PATH);
            singleMovie[3] = currentMovie.getString(AVERAGE_VOTE);
            singleMovie[4] = currentMovie.getString(PLOT_SYNOPSIS);

            moviesArray.add(singleMovie);
        }

        return moviesArray;
    }
}
