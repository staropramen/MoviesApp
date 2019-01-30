package com.example.android.moviesapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.moviesapp.R;
import com.example.android.moviesapp.model.MovieTrailer;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoAdapterViewHolder> {

    private ArrayList<MovieTrailer> movieTrailers;

    private final  VideoOnClickHandler videoOnClickHandler;

    public interface VideoOnClickHandler{
        void onClick(MovieTrailer movieTrailer);
    }

    public VideoAdapter(VideoOnClickHandler onClickHandler) {videoOnClickHandler = onClickHandler;}

    public class VideoAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView videoTypeText;
        public final TextView videoNameText;

        public VideoAdapterViewHolder(View view){
            super(view);
            videoTypeText = (TextView) view.findViewById(R.id.tv_video_type);
            videoNameText = (TextView) view.findViewById(R.id.tv_video_name);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            MovieTrailer movieTrailer = movieTrailers.get(adapterPosition);
            videoOnClickHandler.onClick(movieTrailer);
        }
    }

    @NonNull
    @Override
    public VideoAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int gridItem = R.layout.movie_trailer;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(gridItem, viewGroup,shouldAttachToParentImmediately);

        return new VideoAdapter.VideoAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapterViewHolder videoAdapterViewHolder, int i) {
        MovieTrailer movieTrailer = movieTrailers.get(i);
        videoAdapterViewHolder.videoTypeText.setText(movieTrailer.getType());
        videoAdapterViewHolder.videoNameText.setText(movieTrailer.getName());
    }

    @Override
    public int getItemCount() {
        if (null == movieTrailers) return 0;
        return movieTrailers.size();
    }

    //Function to set movieTrailers
    public void setMovieTrailerArray(ArrayList<MovieTrailer> trailerArrayToSet){
        movieTrailers = trailerArrayToSet;
        notifyDataSetChanged();
    }


}
