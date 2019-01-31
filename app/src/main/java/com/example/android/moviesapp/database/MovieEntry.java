package com.example.android.moviesapp.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "movie")
public class MovieEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "movie_id")
    private String movieId;
    @ColumnInfo(name = "original_title")
    private String originalTitle;
    @ColumnInfo(name = "release_date")
    private String releaseDate;
    @ColumnInfo(name = "poster_path")
    private String posterPath;
    @ColumnInfo(name = "vote_average")
    private String voteAverage;
    @ColumnInfo(name = "plot_synopsis")
    private String plotSynopsis;

    @Ignore
    public MovieEntry(String movieId, String originalTitle, String releaseDate,
                      String posterPath, String voteAverage, String plotSynopsis) {
        this.movieId = movieId;
        this.originalTitle = originalTitle;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.plotSynopsis = plotSynopsis;
    }

    public MovieEntry(int id, String movieId, String originalTitle, String releaseDate,
                      String posterPath, String voteAverage, String plotSynopsis) {
        this.id = id;
        this.movieId = movieId;
        this.originalTitle = originalTitle;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.plotSynopsis = plotSynopsis;
    }

    public int getId() {
        return id;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }
}
