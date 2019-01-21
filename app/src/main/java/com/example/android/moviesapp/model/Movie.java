package com.example.android.moviesapp.model;

public class Movie {

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

    //Get Methods
    public String getOriginalTitle(){
        return originalTitle;
    }
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
}
