package com.example.android.moviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.moviesapp.model.Movie;

public class DetailsActivity extends AppCompatActivity {

    private TextView mMovieTitle;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mMovieTitle = (TextView) findViewById(R.id.tv_movie_title);

        //Get movie object from Intent
        movie = (Movie) getIntent().getParcelableExtra("movie");

        //If movie not null set the views
        if(movie != null){
            mMovieTitle.setText(movie.getOriginalTitle());
        }
    }
}
