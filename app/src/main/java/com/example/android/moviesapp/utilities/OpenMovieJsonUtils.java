package com.example.android.moviesapp.utilities;

import android.content.Context;

import com.example.android.moviesapp.model.Movie;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OpenMovieJsonUtils {

    //Get Strings from JSON
    public static ArrayList<Movie> getStringsFromJson(Context context, String movieJsonStr) throws JSONException {

        //Objects to retrieve in variables
        final String MAIN_ARRAY = "results";

        //Make String a JsonObject
        JSONObject movieJson = new JSONObject(movieJsonStr);

        //Initialize String Array to store Data and set it to null
        ArrayList<Movie> moviesArray = new ArrayList<>();

        //Get Array with the movies inside
        JSONArray moviesJsonArray = movieJson.getJSONArray(MAIN_ARRAY);

        //Store Movies Inormation in Array inside moviesArray
        for (int i = 0; i < moviesJsonArray.length(); i++){


            JSONObject currentMovie = moviesJsonArray.getJSONObject(i);


            Movie movie = generateMovieFromJson(currentMovie);

            moviesArray.add(movie);
        }

        return moviesArray;
    }

    private  static Movie generateMovieFromJson(JSONObject jsonObject){
        Movie movie = new Gson().fromJson(jsonObject.toString(), Movie.class);
        return movie;
    }
}
