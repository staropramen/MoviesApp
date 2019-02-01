package com.example.android.moviesapp;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
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

import com.example.android.moviesapp.Adapter.ReviewAdapter;
import com.example.android.moviesapp.Adapter.VideoAdapter;
import com.example.android.moviesapp.database.AppDatabase;
import com.example.android.moviesapp.databinding.ActivityDetailsBinding;
import com.example.android.moviesapp.model.Detail;
import com.example.android.moviesapp.model.Movie;
import com.example.android.moviesapp.model.MovieReview;
import com.example.android.moviesapp.model.MovieTrailer;
import com.example.android.moviesapp.utilities.MovieDateUtils;
import com.example.android.moviesapp.utilities.NetworkUtils;
import com.example.android.moviesapp.utilities.OpenMovieJsonUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity
        implements VideoAdapter.VideoOnClickHandler, LoaderManager.LoaderCallbacks<Detail> {

    ActivityDetailsBinding mBinding;

    private Movie movie;
    private VideoAdapter videoAdapter;
    private ReviewAdapter reviewAdapter;

    private String MOVIE_QUERY_ID = "movie-query-id";
    private String PATH_TRAILER = "videos";
    private String PATH_REVIEWS= "reviews";
    private static final int DETAIL_LOADER = 45;
    private String YOUTUBE_BASE_URL = "http://www.youtube.com/watch?v=";
    private String YOUTUBE_APP_BASE = "vnd.youtube:";
    private String MOVIE_EXTRA = "movie";
    private String IS_FAV_EXTRA = "is-fav";

    private String baseImageUrl = "https://image.tmdb.org/t/p/w185";

    private AppDatabase mDb;

    private boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mDb = AppDatabase.getInstance(getApplicationContext());

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        //Set title of activity
        setTitle(getString(R.string.details_name));

        //Get movie object from Intent
        movie = (Movie) getIntent().getParcelableExtra(MOVIE_EXTRA);
        //isFavorite = getIntent().getExtras().getBoolean(IS_FAV_EXTRA);

        //Set Fav Star color
        checkIfIsFav(movie.getMovieId());

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
        mBinding.reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Set Adapter
        videoAdapter = new VideoAdapter(this);
        mBinding.trailerRecyclerView.setAdapter(videoAdapter);
        reviewAdapter = new ReviewAdapter();
        mBinding.reviewRecyclerView.setAdapter(reviewAdapter);

        String movieId = movie.getMovieId();
        loadMovieDetails(movieId);

        //Set OnClick for Favorites Button
        mBinding.ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFavorite){
                    mBinding.ivFavorite.setColorFilter(Color.WHITE);
                    deleteMovieFromDb(movie.getMovieId());
                    isFavorite = false;
                } else {
                    mBinding.ivFavorite.setColorFilter(Color.YELLOW);
                    saveMovieInDb();
                    isFavorite = true;
                }

            }
        });
    }

    //Save Movie to Database
    private void saveMovieInDb() {
        final Movie movieEntry = new Movie(movie.getMovieId(), movie.getOriginalTitle(),
                movie.getReleaseDate(), movie.getPosterPath(), movie.getVoteAverage(), movie.getPlotSynopsis());

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.movieDao().insertMovie(movieEntry);
            }
        });
    }

    // Delete Movie from Db
    private void deleteMovieFromDb(final String movieId){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.movieDao().deleteByMovieId(movieId);
            }
        });
    }

    //Check if Movie is in Database
    private void checkIfIsFav(final String movieId){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final Movie movie = mDb.movieDao().checkForMovie(movieId);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(movie == null){
                            mBinding.ivFavorite.setColorFilter(Color.WHITE);
                            isFavorite = false;
                        }else {
                            mBinding.ivFavorite.setColorFilter(Color.YELLOW);
                            isFavorite = true;
                        }
                    }
                });
            }
        });
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
    public Loader<Detail> onCreateLoader(int i, @Nullable final Bundle bundle) {
        return new AsyncTaskLoader<Detail>(this) {
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
            public Detail loadInBackground() {
                String movieId = bundle.getString(MOVIE_QUERY_ID);
                //Return null if param is empty
                if(movieId == null || TextUtils.isEmpty(movieId)){
                    return null;
                }
                URL trailerRequestUrl = NetworkUtils.builtDetailsUrl(PATH_TRAILER, movieId);
                URL reviewRequestUrl = NetworkUtils.builtDetailsUrl(PATH_REVIEWS, movieId);



                try {
                    String jsonResponse = NetworkUtils.getHttpResponse(trailerRequestUrl);
                    ArrayList<MovieTrailer> movieTrailers = OpenMovieJsonUtils.getTrailerStingsFromJson(DetailsActivity.this, jsonResponse);
                    String jsonReviewResponse = NetworkUtils.getHttpResponse(reviewRequestUrl);
                    ArrayList<MovieReview> movieReviews = OpenMovieJsonUtils.getReviewStingsFromJson(DetailsActivity.this, jsonReviewResponse);
                    Detail detail = new Detail(movieTrailers, movieReviews);
                    return detail;
                }catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Detail> loader, Detail detail) {
        if(detail != null){
            mBinding.trailerRecyclerView.setVisibility(View.VISIBLE);
            ArrayList<MovieTrailer> movieTrailers = detail.getMovieTrailers();
            ArrayList<MovieReview> movieReviews = detail.getMovieReviews();
            videoAdapter.setMovieTrailerArray(movieTrailers);
            reviewAdapter.setMovieReviewArray(movieReviews);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Detail> loader) {

    }
}
