package com.example.android.moviesapp.utilities;

import android.content.Context;

import com.example.android.moviesapp.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OpenMovieJsonUtils {

    //Get Strings from JSON
    public static ArrayList<Movie> getStringsFromJson(Context context, String movieJsonStr) throws JSONException {

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
        ArrayList<Movie> moviesArray = new ArrayList<>();

        //Get Array with the movies inside
        JSONArray moviesJsonArray = movieJson.getJSONArray(MAIN_ARRAY);

        //Store Movies Inormation in Array inside moviesArray
        for (int i = 0; i < moviesJsonArray.length(); i++){


            JSONObject currentMovie = moviesJsonArray.getJSONObject(i);

            String movieTitle = currentMovie.getString(MOVIE_TITLE);
            String releaseDate = currentMovie.getString(RELEASE_DATE);
            String posterPath = currentMovie.getString(POSTER_PATH);
            String averageVote = currentMovie.getString(AVERAGE_VOTE);
            String plotSynopsis = currentMovie.getString(PLOT_SYNOPSIS);

            Movie movie = new Movie(movieTitle, releaseDate,posterPath,averageVote,plotSynopsis);

            moviesArray.add(movie);
        }

        return moviesArray;
    }
}
