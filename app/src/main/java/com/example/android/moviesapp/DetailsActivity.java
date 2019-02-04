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
import com.example.android.moviesapp.model.ReviewsList;
import com.example.android.moviesapp.model.TrailersList;
import com.example.android.moviesapp.utilities.GetDataService;
import com.example.android.moviesapp.utilities.MovieDateUtils;
import com.example.android.moviesapp.utilities.NetworkUtils;
import com.example.android.moviesapp.utilities.OpenMovieJsonUtils;
import com.example.android.moviesapp.utilities.RetrofitClientInstance;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity
        implements VideoAdapter.VideoOnClickHandler{

    ActivityDetailsBinding mBinding;

    private Movie movie;
    private VideoAdapter videoAdapter;
    private ReviewAdapter reviewAdapter;
    private List<MovieTrailer> movieTrailers;
    private List<MovieReview> movieReviews;

    private String MOVIE_QUERY_ID = "movie-query-id";
    private String PATH_TRAILER = "videos";
    private String PATH_REVIEWS= "reviews";
    private String YOUTUBE_BASE_URL = "http://www.youtube.com/watch?v=";
    private String YOUTUBE_APP_BASE = "vnd.youtube:";
    private String MOVIE_EXTRA = "movie";

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
        loadMovieReviews(movieId);
        loadMovieTrailers(movieId);

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

    //Kick off the Background Task for Trailers
    private void loadMovieTrailers(String movieId){
        GetDataService api = RetrofitClientInstance.getApiService();
        Call<TrailersList> call = api.getAllTrailers(movieId);

        call.enqueue(new Callback<TrailersList>() {
            @Override
            public void onResponse(Call<TrailersList> call, Response<TrailersList> response) {
                if(response.isSuccessful()){
                    movieTrailers = response.body().getTrailers();
                    videoAdapter.setMovieTrailerArray(movieTrailers);
                }
            }

            @Override
            public void onFailure(Call<TrailersList> call, Throwable t) {
            }
        });
    }

    //Kick off the Background Task for Reviews
    private void loadMovieReviews(String movieId){
        GetDataService api = RetrofitClientInstance.getApiService();
        Call<ReviewsList> call = api.getAllReviews(movieId);

        call.enqueue(new Callback<ReviewsList>() {
            @Override
            public void onResponse(Call<ReviewsList> call, Response<ReviewsList> response) {
                if(response.isSuccessful()){
                    movieReviews = response.body().getReviews();
                    Log.d("ARRAY", movieReviews.toString());
                    reviewAdapter.setMovieReviewArray(movieReviews);
                }
            }

            @Override
            public void onFailure(Call<ReviewsList> call, Throwable t) {
            }
        });
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
}
