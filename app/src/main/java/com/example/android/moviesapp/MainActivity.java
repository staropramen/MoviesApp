package com.example.android.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.moviesapp.model.Movie;
import com.example.android.moviesapp.utilities.MovieDateUtils;
import com.example.android.moviesapp.utilities.NetworkUtils;
import com.example.android.moviesapp.utilities.OpenMovieJsonUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieOnClickHandler {

    private String TAG = MainActivity.class.getSimpleName();
    private MovieAdapter movieAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private TextView errorTextView;
    private String sortOrder;

    private ConnectivityManager connectivityManager;

    private String PREF_FILENAME = "SortOrderFile";
    private String PREF_VAL_KEY = "PreferredSortOrder";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        errorTextView = (TextView) findViewById(R.id.tv_error_message_display);
        //Get a reference to Recycler View and set GridlayoutManager
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(gridLayoutManager);

        //Set Adapter
        movieAdapter = new MovieAdapter(this);

        mRecyclerView.setAdapter(movieAdapter);

        //Load the movie data, default sorted by popularity
        sortOrder = getSharedPreferenceOrder();
        loadMovieData(sortOrder);
    }

    //Method to kick off the Background task
    private void loadMovieData(String sortOrder){
        //Check if there is an internet connection
        connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        //Load Movie Data if device is connected to internet, else show error message
        if(activeNetwork != null && activeNetwork.isConnected()){
            showMovieData();
            new FetchMovieTask().execute(sortOrder);
        }else {
            showErrorMessage();
            errorTextView.setText(R.string.no_network);
        }

    }

    @Override
    public void onClick(Movie movie) {
        Context context = this;
        Class destinationClass = DetailsActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra("movie", movie);
        startActivity(intentToStartDetailActivity);
    }

    private void showMovieData() {
        mRecyclerView.setVisibility(View.VISIBLE);
        errorTextView.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        errorTextView.setVisibility(View.VISIBLE);
        //Default set text to error
        errorTextView.setText(R.string.error_message);
    }

    private String getSharedPreferenceOrder() {
        SharedPreferences sharedPrefs = getSharedPreferences(PREF_FILENAME, 0);
        String order = sharedPrefs.getString(PREF_VAL_KEY, getString(R.string.popular));
        return order;
    }

    private void saveSharedPreferenceOrder(String order){
        SharedPreferences sharedPrefs = getSharedPreferences(PREF_FILENAME, 0);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(PREF_VAL_KEY, order);
        editor.commit();
    }

    //Asynch Task to Load Data from API
    public class FetchMovieTask extends AsyncTask<String, Void, ArrayList<Movie>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {
            //Return null if param is empty
            if(params.length == 0){
                return null;
            }

            String preferredOrder = params[0];
            URL moviesRequestUrl = NetworkUtils.builtUrl(preferredOrder);

            try {
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
            progressBar.setVisibility(View.INVISIBLE);
            if(moviesData != null){
                showMovieData();
                movieAdapter.setMoviesArray(moviesData);
            }else {
                showErrorMessage();
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
                    saveSharedPreferenceOrder(getString(R.string.popular));
                    sortOrder = getString(R.string.popular);
                    loadMovieData(sortOrder);
                    return true;
                }
            case R.id.sort_top_rated:
                if(item.isChecked()){
                    return false;
                }else {
                    item.setChecked(true);
                    saveSharedPreferenceOrder(getString(R.string.top_rated));
                    sortOrder = getString(R.string.top_rated);
                    loadMovieData(sortOrder);
                    return true;
                }
            case R.id.refresh_button:
                loadMovieData(sortOrder);
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }


    }
}
