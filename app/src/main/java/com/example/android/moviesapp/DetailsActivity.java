package com.example.android.moviesapp;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.android.moviesapp.databinding.ActivityDetailsBinding;
import com.example.android.moviesapp.model.Movie;
import com.example.android.moviesapp.utilities.MovieDateUtils;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding mBinding;

    private Movie movie;

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
    }
}
