
package com.example.android.moviesapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.moviesapp.R;
import com.example.android.moviesapp.model.Movie;
import com.example.android.moviesapp.model.MovieReview;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {

    private ArrayList<MovieReview> movieReviews;



    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {

        public final TextView reviewAuthor;
        public final TextView reviewContent;

        public ReviewAdapterViewHolder(View view){
            super(view);
            reviewAuthor = (TextView) view.findViewById(R.id.review_author);
            reviewContent = (TextView) view.findViewById(R.id.review_content);

        }
    }

    @NonNull
    @Override
    public ReviewAdapter.ReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int gridItem = R.layout.movie_review;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(gridItem, viewGroup,shouldAttachToParentImmediately);

        return new ReviewAdapter.ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewAdapterViewHolder reviewAdapterViewHolder, int i) {
        MovieReview movieReview = movieReviews.get(i);
        reviewAdapterViewHolder.reviewAuthor.setText(movieReview.getAuthor());
        reviewAdapterViewHolder.reviewContent.setText(movieReview.getContent());
    }

    @Override
    public int getItemCount() {
        if (null == movieReviews) return 0;
        return movieReviews.size();
    }

    //Function to set movieTrailers
    public void setMovieReviewArray(ArrayList<MovieReview> reviewArrayToSet){
        movieReviews = reviewArrayToSet;
        notifyDataSetChanged();
    }
}
