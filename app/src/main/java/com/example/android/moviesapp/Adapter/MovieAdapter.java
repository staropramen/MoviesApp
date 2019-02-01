package com.example.android.moviesapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.moviesapp.R;
import com.example.android.moviesapp.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    //Global Variables
    private List<Movie> moviesArray;
    private String baseImageUrl = "https://image.tmdb.org/t/p/w185";

    private final MovieOnClickHandler movieOnClickHandler;

    public interface MovieOnClickHandler {
        void onClick(Movie movie);
    }

    //Constructor
    public MovieAdapter(MovieOnClickHandler clickHandler){
        movieOnClickHandler = clickHandler;
    }

    //View Holder
    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Initialize Image View
        public final ImageView movieImageView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            //Get a reference to the Image View
            movieImageView = (ImageView) view.findViewById(R.id.iv_movie_poster);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Movie movie = moviesArray.get(adapterPosition);
            movieOnClickHandler.onClick(movie);
        }
    }

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int gridItem = R.layout.grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(gridItem, viewGroup,shouldAttachToParentImmediately);

        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        Movie movieObject = moviesArray.get(position);
        String moviePath = movieObject.getPosterPath();
        Picasso.get().load(baseImageUrl + moviePath).into(movieAdapterViewHolder.movieImageView);
    }

    @Override
    public int getItemCount() {
        if (null == moviesArray) return 0;
        return moviesArray.size();
    }

    //Function to set moviesArray
    public void setMoviesArray(List<Movie> moviesArrayToSet){
        moviesArray = moviesArrayToSet;
        notifyDataSetChanged();
    }
}
