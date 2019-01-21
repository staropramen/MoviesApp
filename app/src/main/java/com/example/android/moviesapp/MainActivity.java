package com.example.android.moviesapp;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.moviesapp.model.Movie;
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
    private String sortOrder;

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

        //Load the movie data, default sorted by popularity
        sortOrder = getString(R.string.popular);
        loadMovieData(sortOrder);
    }

    //Method to kick off the Background task
    private void loadMovieData(String sortOrder){
        new FetchMovieTask().execute(sortOrder);
    }

    //Asynch Task to Load Data from API
    public class FetchMovieTask extends AsyncTask<String, Void, ArrayList<Movie>>{

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {
            //Return null if param is empty
            if(params.length == 0){
                return null;
            }

            String preferredOrder = params[0];
            URL moviesRequestUrl = NetworkUtils.builtUrl(preferredOrder);

            try {
                Log.v(TAG, "Im in try in do in Background");
                String jsonResponse = NetworkUtils.getHttpResponse(moviesRequestUrl);
                ArrayList<Movie> movieJsonData = OpenMovieJsonUtils.getStringsFromJson(MainActivity.this, jsonResponse);
                return movieJsonData;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> moviesData) {
            if(moviesData != null){
                Log.v(TAG, "Im in PostExecute if statement");
                movieAdapter.setMoviesArray(moviesData);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sort_popular:
                if(item.isChecked()){
                    return false;
                }else {
                    item.setChecked(true);
                    sortOrder = getString(R.string.popular);
                    loadMovieData(sortOrder);
                    return true;
                }
            case R.id.sort_top_rated:
                if(item.isChecked()){
                    return false;
                }else {
                    item.setChecked(true);
                    sortOrder = getString(R.string.top_rated);
                    loadMovieData(sortOrder);
                    return true;
                }
                default:
                    return super.onOptionsItemSelected(item);
        }


    }
}
