package com.example.android.moviesapp.model;

import java.util.ArrayList;

public class Detail {

    private ArrayList<MovieTrailer> movieTrailers;
    private ArrayList<MovieReview> movieReviews;

    //Constructor
    public Detail(ArrayList<MovieTrailer> movieTrailers, ArrayList<MovieReview> movieReviews) {
        this.movieTrailers = movieTrailers;
        this.movieReviews = movieReviews;
    }

    public ArrayList<MovieTrailer> getMovieTrailers(){return movieTrailers;}
    public ArrayList<MovieReview> getMovieReviews(){return movieReviews;}

    public void setMovieTrailers(ArrayList<MovieTrailer> movieTrailers){this.movieTrailers = movieTrailers;}
    public void setMovieReviews(ArrayList<MovieReview> movieReviews){this.movieReviews = movieReviews;}
}
