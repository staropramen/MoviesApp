package com.example.android.moviesapp.utilities;

import android.content.Context;

import com.example.android.moviesapp.model.Movie;
import com.example.android.moviesapp.model.MovieReview;
import com.example.android.moviesapp.model.MovieTrailer;
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
        ArrayList<Movie> moviesArray = new ArrayList<Movie>();

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

    //Get Trailer Strings from JSON
    public static ArrayList<MovieTrailer> getTrailerStingsFromJson(Context context, String movieJsonStr) throws JSONException {

        //Objects to retrieve in variables
        final String MAIN_ARRAY = "results";

        //Make String a JsonObject
        JSONObject movieTrailerJson = new JSONObject(movieJsonStr);

        //Initialize String Array to store Data and set it to null
        ArrayList<MovieTrailer> movieTrailerArray = new ArrayList<>();

        //Get Array with the movies inside
        JSONArray moviesJsonArray = movieTrailerJson.getJSONArray(MAIN_ARRAY);

        //Store Movies Inormation in Array inside moviesArray
        for (int i = 0; i < moviesJsonArray.length(); i++){

            JSONObject currentMovie = moviesJsonArray.getJSONObject(i);

            MovieTrailer movieTrailer = generateMovieTrailerFromJson(currentMovie);

            movieTrailerArray.add(movieTrailer);
        }

        return movieTrailerArray;
    }

    private  static MovieTrailer generateMovieTrailerFromJson(JSONObject jsonObject){
        MovieTrailer movieTrailer = new Gson().fromJson(jsonObject.toString(), MovieTrailer.class);
        return movieTrailer;
    }

    //Get Trailer Strings from JSON
    public static ArrayList<MovieReview> getReviewStingsFromJson(Context context, String movieJsonStr) throws JSONException {

        //Objects to retrieve in variables
        final String MAIN_ARRAY = "results";

        //Make String a JsonObject
        JSONObject movieReviewJson = new JSONObject(movieJsonStr);

        //Initialize String Array to store Data and set it to null
        ArrayList<MovieReview> movieReviewArray = new ArrayList<>();

        //Get Array with the movies inside
        JSONArray moviesJsonArray = movieReviewJson.getJSONArray(MAIN_ARRAY);

        //Store Movies Inormation in Array inside moviesArray
        for (int i = 0; i < moviesJsonArray.length(); i++){

            JSONObject currentMovie = moviesJsonArray.getJSONObject(i);

            MovieReview movieReview = generateMovieReviewFromJson(currentMovie);

            movieReviewArray.add(movieReview);
        }

        return movieReviewArray;
    }

    private  static MovieReview generateMovieReviewFromJson(JSONObject jsonObject){
        MovieReview movieReview = new Gson().fromJson(jsonObject.toString(), MovieReview.class);
        return movieReview;
    }
}
