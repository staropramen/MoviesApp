package com.example.android.moviesapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@Entity(tableName = "movie")
public class Movie implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int primaryKey;
    @ColumnInfo(name = "movie_id")
    @SerializedName("id")
    private String movieId;
    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    private String originalTitle;
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    private String releaseDate;
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String posterPath;
    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private String voteAverage;
    @ColumnInfo(name = "plot_synopsis")
    @SerializedName("overview")
    private String plotSynopsis;

    //Constructor
    @Ignore
    public Movie(String movieId, String originalTitle, String releaseDate, String posterPath, String voteAverage,
                 String plotSynopsis) {
        this.movieId = movieId;
        this.originalTitle = originalTitle;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.plotSynopsis = plotSynopsis;
    }

    public Movie(int primaryKey, String movieId, String originalTitle, String releaseDate, String posterPath, String voteAverage,
                 String plotSynopsis) {
        this.primaryKey = primaryKey;
        this.movieId = movieId;
        this.originalTitle = originalTitle;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.plotSynopsis = plotSynopsis;
    }

    //Constructor for Parcel
    @Ignore
    public Movie(Parcel in) {
        movieId = in.readString();
        originalTitle = in.readString();
        releaseDate = in.readString();
        posterPath = in.readString();
        voteAverage = in.readString();
        plotSynopsis = in.readString();
    }

    //Get Methods
    public int getPrimaryKey() {return primaryKey; }
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
    public void setPrimaryKey(int primaryKey) {this.primaryKey = primaryKey; }
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

    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }

    @Ignore
    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(movieId);
        dest.writeString(originalTitle);
        dest.writeString(releaseDate);
        dest.writeString(posterPath);
        dest.writeString(voteAverage);
        dest.writeString(plotSynopsis);
    }

    @Ignore
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
