package com.example.android.moviesapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.annotation.Nullable;
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

import com.example.android.moviesapp.Adapter.MovieAdapter;
import com.example.android.moviesapp.database.AppDatabase;
import com.example.android.moviesapp.model.Movie;
import com.example.android.moviesapp.model.MoviesList;
import com.example.android.moviesapp.utilities.GetDataService;
import com.example.android.moviesapp.utilities.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.MovieOnClickHandler{

    private String TAG = MainActivity.class.getSimpleName();
    private MovieAdapter movieAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private TextView errorTextView;
    private String sortOrder;
    private Parcelable mListState;
    private int selectedMenuItem;
    private MenuItem menuItem;
    private List<Movie> moviesList;

    private GridLayoutManager gridLayoutManager;

    private AppDatabase mDb;

    private ConnectivityManager connectivityManager;

    private String MOVIE_EXTRA = "movie";
    private String LIST_STATE_KEY = "list-state";
    private String SORT_STATE_KEY = "state-sort-order";

    private static final String PREFERRED_SORT_ORDER = "preferred_sort_order";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDb = AppDatabase.getInstance(getApplicationContext());

        progressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        errorTextView = (TextView) findViewById(R.id.tv_error_message_display);
        //Get a reference to Recycler View and set GridlayoutManager
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        gridLayoutManager =
                new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(gridLayoutManager);

        //Set Adapter
        movieAdapter = new MovieAdapter(this);

        mRecyclerView.setAdapter(movieAdapter);

        if(savedInstanceState != null) {
            selectedMenuItem = savedInstanceState.getInt(SORT_STATE_KEY);
        }


        //Load the movie data, default sorted by popularity
        sortOrder = getSortOrder();
        Log.d(TAG, sortOrder);
        loadMovieData(sortOrder);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(SORT_STATE_KEY, selectedMenuItem);

        mListState = gridLayoutManager.onSaveInstanceState();
        outState.putParcelable(LIST_STATE_KEY, mListState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        if(outState != null)
            selectedMenuItem = outState.getInt(SORT_STATE_KEY);
            Log.d(TAG, "RESTORE INSTANCE STATE" + selectedMenuItem);
            mListState = outState.getParcelable(LIST_STATE_KEY);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mListState != null) {
            gridLayoutManager.onRestoreInstanceState(mListState);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    //Get sortOrder
    private String getSortOrder(){
        String sortOrder;
        if(selectedMenuItem == R.id.sort_top_rated){
            sortOrder = getString(R.string.top_rated);
        }else if(selectedMenuItem == R.id.sort_favorites){
            sortOrder = getString(R.string.favorites);
        } else {
            sortOrder = getString(R.string.popular);
        }
        return sortOrder;
    }

    //Method to kick off the Background task
    private void loadMovieData(String sortOrder){
        if(sortOrder.equals(getString(R.string.favorites))){
            setupFavoritesViewModel();
        } else {
            loadDataFromInternet(sortOrder);
        }
    }

    private void loadDataFromInternet(String sortOrder){
        //Check if there is an internet connection
        connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        //Load Movie Data if device is connected to internet, else show error message
        if(activeNetwork != null && activeNetwork.isConnected()){
            if(sortOrder == getString(R.string.top_rated)){
                setupTopRatedViewModel();
            }else {
                setupPopularViewModel();
            }

        }else {
            showErrorMessage();
            errorTextView.setText(R.string.no_network);
        }
    }

    private void setupPopularViewModel(){
        PopularViewModel popularViewModel =ViewModelProviders.of(this).get(PopularViewModel.class);
        popularViewModel.getMovies().observe(this, new Observer<MoviesList>(){

            @Override
            public void onChanged(@Nullable MoviesList moviesList) {
                List<Movie> movies = moviesList.getMovies();
                movieAdapter.setMoviesArray(movies);
            }
        });
    }

    private void setupTopRatedViewModel(){
        TopRatedViewModel topRatedViewModel =ViewModelProviders.of(this).get(TopRatedViewModel.class);
        topRatedViewModel.getMovies().observe(this, new Observer<MoviesList>(){

            @Override
            public void onChanged(@Nullable MoviesList moviesList) {
                List<Movie> movies = moviesList.getMovies();
                movieAdapter.setMoviesArray(movies);
            }
        });
    }

    private void setupFavoritesViewModel(){
        FavoritesViewModel viewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                Log.v(TAG, "Updating List from LiveData in ViewModel");
                progressBar.setVisibility(View.INVISIBLE);
                if(movies.isEmpty()){
                    showErrorMessage();
                    errorTextView.setText(R.string.no_favorite);
                } else {
                    showMovieData();
                    movieAdapter.setMoviesArray(movies);
                }
            }
        });
    }

    @Override
    public void onClick(Movie movie) {
        Context context = this;
        Class destinationClass = DetailsActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(MOVIE_EXTRA, movie);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        switch (selectedMenuItem){
            case R.id.sort_popular:
                menuItem = menu.findItem(R.id.sort_popular);
                menuItem.setChecked(true);
                sortOrder = getString(R.string.popular);
                break;
            case R.id.sort_top_rated:
                menuItem = menu.findItem(R.id.sort_top_rated);
                menuItem.setChecked(true);
                sortOrder = getString(R.string.top_rated);
                break;
            case R.id.sort_favorites:
                menuItem = menu.findItem(R.id.sort_favorites);
                menuItem.setChecked(true);
                sortOrder = getString(R.string.favorites);
                break;
                default:
                    menuItem = menu.findItem(R.id.sort_popular);
                    menuItem.setChecked(true);
                    sortOrder = getString(R.string.popular);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.sort_popular:
                selectedMenuItem = id;
                if(item.isChecked()){
                    return false;
                }else {
                    item.setChecked(true);
                    sortOrder = getString(R.string.popular);
                    loadMovieData(sortOrder);
                    return true;
                }
            case R.id.sort_top_rated:
                selectedMenuItem = id;
                if(item.isChecked()){
                    return false;
                }else {
                    item.setChecked(true);
                    sortOrder = getString(R.string.top_rated);
                    loadMovieData(sortOrder);
                    return true;
                }
            case R.id.sort_favorites:
                selectedMenuItem = id;
                if(item.isChecked()){
                    return false;
                }else {
                    item.setChecked(true);
                    sortOrder = getString(R.string.favorites);
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
