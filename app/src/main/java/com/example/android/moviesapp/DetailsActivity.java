package com.example.android.moviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.android.moviesapp.model.Movie;
import com.example.android.moviesapp.utilities.MovieDateUtils;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private TextView mMovieTitle;
    private TextView mReleaseDate;
    private TextView mAverageRate;
    private TextView mPlotSynopsis;
    private ImageView mImagePoster;
    private ScrollView movieScrollView;
    private TextView detailErrorMessage;

    private Movie movie;

    private String baseImageUrl = "https://image.tmdb.org/t/p/w185";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mMovieTitle = (TextView) findViewById(R.id.tv_movie_title);
        mReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        mAverageRate = (TextView) findViewById(R.id.tv_average_rate);
        mPlotSynopsis = (TextView) findViewById(R.id.tv_plot_synopsis);
        mImagePoster = (ImageView) findViewById(R.id.iv_movie_thumbnail);
        movieScrollView = (ScrollView) findViewById(R.id.sv_movie_detail);
        detailErrorMessage = (TextView) findViewById(R.id.tv_error_message_detail);

        //Set title of activity
        setTitle(getString(R.string.details_name));

        //Get movie object from Intent
        movie = (Movie) getIntent().getParcelableExtra("movie");

        //If movie not null set the views
        if(movie != null){
            mMovieTitle.setText(movie.getOriginalTitle());
            String newDate = MovieDateUtils.makePrettyDate(movie.getReleaseDate());
            mReleaseDate.setText(newDate);
            mAverageRate.setText(movie.getVoteAverage());
            mPlotSynopsis.setText(movie.getPlotSynopsis());

            Picasso.get()
                    .load(baseImageUrl + movie.getPosterPath())
                    .into(mImagePoster);
        } else {
            //Show Error Message
            movieScrollView.setVisibility(View.INVISIBLE);
            detailErrorMessage.setVisibility(View.VISIBLE);
        }
    }
}
