package com.example.android.moviesapp;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.android.moviesapp.Adapter.VideoAdapter;
import com.example.android.moviesapp.databinding.ActivityDetailsBinding;
import com.example.android.moviesapp.model.Movie;
import com.example.android.moviesapp.model.MovieTrailer;
import com.example.android.moviesapp.utilities.MovieDateUtils;
import com.example.android.moviesapp.utilities.NetworkUtils;
import com.example.android.moviesapp.utilities.OpenMovieJsonUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity
        implements VideoAdapter.VideoOnClickHandler, LoaderManager.LoaderCallbacks<ArrayList<MovieTrailer>> {

    ActivityDetailsBinding mBinding;

    private Movie movie;
    private VideoAdapter videoAdapter;

    private String MOVIE_QUERY_ID = "movie-query-id";
    private String PATH_TRAILER = "videos";
    private static final int DETAIL_LOADER = 45;
    private String YOUTUBE_BASE_URL = "http://www.youtube.com/watch?v=";
    private String YOUTUBE_APP_BASE = "vnd.youtube:";

    private String baseImageUrl = "https://image.tmdb.org/t/p/w185";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        //Set title of activity
        setTitle(getString(R.string.details_name));

        //Get movie object from Intent
        movie = (Movie) getIntent().getParcelableExtra("movie");

        //If movie not null set the views
        if(movie != null){
            mBinding.tvMovieTitle.setText(movie.getOriginalTitle());
            String newDate = MovieDateUtils.makePrettyDate(movie.getReleaseDate());
            mBinding.tvReleaseDate.setText(newDate);
            mBinding.tvAverageRate.setText(movie.getVoteAverage());
            mBinding.tvPlotSynopsis.setText(movie.getPlotSynopsis());

            Picasso.get()
                    .load(baseImageUrl + movie.getPosterPath())
                    .into(mBinding.ivMovieThumbnail);
        } else {
            //Show Error Message
            mBinding.svMovieDetail.setVisibility(View.INVISIBLE);
            mBinding.tvErrorMessageDetail.setVisibility(View.VISIBLE);
        }

        //Set Layout Manager
        mBinding.trailerRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Set Adapter
        videoAdapter = new VideoAdapter(this);
        mBinding.trailerRecyclerView.setAdapter(videoAdapter);

        String movieId = movie.getMovieId();
        loadMovieDetails(movieId);
    }

    //Kick off the Background Task
    private void loadMovieDetails(String movieId){
        //Make a Bundle for parameters
        Bundle bundle = new Bundle();
        bundle.putString(MOVIE_QUERY_ID, movieId);
        //Kick off the loader
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<Object> detailsLoader = loaderManager.getLoader(DETAIL_LOADER);
        if(detailsLoader == null){
            loaderManager.initLoader(DETAIL_LOADER, bundle, this).forceLoad();
        }else {
            loaderManager.restartLoader(DETAIL_LOADER, bundle,this).forceLoad();
        }
    }

    @Override
    public void onClick(MovieTrailer movieTrailer) {
        String youtubeKey = movieTrailer.getKey();
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_APP_BASE + youtubeKey));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(YOUTUBE_BASE_URL + youtubeKey));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

    @SuppressLint("StaticFieldLeak")
    @NonNull
    @Override
    public Loader<ArrayList<MovieTrailer>> onCreateLoader(int i, @Nullable final Bundle bundle) {
        return new AsyncTaskLoader<ArrayList<MovieTrailer>>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if(bundle == null){
                    return;
                }
                //Hide recyclerview while loading
                mBinding.trailerRecyclerView.setVisibility(View.INVISIBLE);
            }

            @Nullable
            @Override
            public ArrayList<MovieTrailer> loadInBackground() {
                Log.v("!!!!!!!", "IM HERE");
                String movieId = bundle.getString(MOVIE_QUERY_ID);
                //Return null if param is empty
                if(movieId == null || TextUtils.isEmpty(movieId)){
                    return null;
                }
                URL trailerRequestUrl = NetworkUtils.builtDetailsUrl(PATH_TRAILER, movieId);

                try {
                    String jsonResponse = NetworkUtils.getHttpResponse(trailerRequestUrl);
                    return OpenMovieJsonUtils.getTrailerStingsFromJson(DetailsActivity.this, jsonResponse);
                }catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<MovieTrailer>> loader, ArrayList<MovieTrailer> movieTrailers) {
        if(movieTrailers != null){
            mBinding.trailerRecyclerView.setVisibility(View.VISIBLE);
            videoAdapter.setMovieTrailerArray(movieTrailers);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<MovieTrailer>> loader) {

    }
}
