package com.example.android.moviesapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.android.moviesapp.utilities.NetworkUtils;
import com.example.android.moviesapp.utilities.OpenMovieJsonUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private MovieAdapter movieAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get a reference to Recycler View and set GridlayoutManager
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(gridLayoutManager);

        //Set Adapter
        movieAdapter = new MovieAdapter();

        mRecyclerView.setAdapter(movieAdapter);

        //Load the movie data
        loadMovieData();
    }

    //Method to kick off the Background task
    private void loadMovieData(){
        //Todo delete later
        String myPreference = "popular";

        // TODO Debug Function Delete later
        new FetchMovieTask().execute(myPreference);
    }

    //Asynch Task to Load Data from API
    public class FetchMovieTask extends AsyncTask<String, Void, ArrayList<String[]>>{

        @Override
        protected ArrayList<String[]> doInBackground(String... params) {
            //Return null if param is empty
            if(params.length == 0){
                return null;
            }

            String preferredOrder = params[0];
            URL moviesRequestUrl = NetworkUtils.builtUrl(preferredOrder);

            try {
                Log.v(TAG, "Im in try in do in Background");
                String jsonResponse = NetworkUtils.getHttpResponse(moviesRequestUrl);
                ArrayList<String[]> movieJsonData = OpenMovieJsonUtils.getStringsFromJson(MainActivity.this, jsonResponse);
                return movieJsonData;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<String[]> moviesData) {
            if(moviesData != null){
                Log.v(TAG, "Im in PostExecute if statement");
                movieAdapter.setMoviesArray(moviesData);
            }
        }
    }
}
