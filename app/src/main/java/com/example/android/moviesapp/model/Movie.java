package com.example.android.moviesapp.model;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private String originalTitle;
    private String releaseDate;
    private String posterPath;
    private String voteAverage;
    private String plotSynopsis;

    //Constructor
    public Movie(String originalTitle, String releaseDate, String posterPath, String voteAverage, String plotSynopsis) {
        this.originalTitle = originalTitle;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.plotSynopsis = plotSynopsis;
    }

    //Constructor for Parcel
    public Movie(Parcel in) {
        originalTitle = in.readString();
        releaseDate = in.readString();
        posterPath = in.readString();
        voteAverage = in.readString();
        plotSynopsis = in.readString();
    }

    //Get Methods
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
