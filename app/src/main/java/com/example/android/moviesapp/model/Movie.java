package com.example.android.moviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Movie implements Parcelable {
    @SerializedName("id")
    private String movieId;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("vote_average")
    private String voteAverage;
    @SerializedName("overview")
    private String plotSynopsis;


    //Constructor
    public Movie(String movieId, String originalTitle, String releaseDate, String posterPath, String voteAverage,
                 String plotSynopsis) {
        this.movieId = movieId;
        this.originalTitle = originalTitle;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.plotSynopsis = plotSynopsis;
    }

    //Constructor for Parcel
    public Movie(Parcel in) {
        movieId = in.readString();
        originalTitle = in.readString();
        releaseDate = in.readString();
        posterPath = in.readString();
        voteAverage = in.readString();
        plotSynopsis = in.readString();
    }

    //Get Methods
    public String getMovieId(){return movieId;}
    public String getOriginalTitle(){ return originalTitle; }
    public String getReleaseDate(){
        return releaseDate;
    }
    public String getPosterPath(){
        return posterPath;
    }
    public String getVoteAverage(){
        return voteAverage;
    }
    public String getPlotSynopsis(){
        return plotSynopsis;
    }

    //Set Methods
    public void setMovieId(String movieId){
        this.movieId = movieId;
    }
    public void setOriginalTitle(String originalTitle){
        this.originalTitle = originalTitle;
    }
    public void setReleaseDate(String releaseDate){
        this.releaseDate = releaseDate;
    }
    public void setPosterPath(String posterPath){
        this.posterPath = posterPath;
    }
    public void setVoteAverage(String voteAverage){
        this.voteAverage = voteAverage;
    }
    public void setPlotSynopsis(String plotSynopsis){
        this.plotSynopsis = plotSynopsis;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(movieId);
        dest.writeString(originalTitle);
        dest.writeString(releaseDate);
        dest.writeString(posterPath);
        dest.writeString(voteAverage);
        dest.writeString(plotSynopsis);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
