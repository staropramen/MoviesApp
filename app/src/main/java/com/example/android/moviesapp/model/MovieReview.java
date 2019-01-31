package com.example.android.moviesapp.model;

import com.google.gson.annotations.SerializedName;

public class MovieReview {
    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String content;
    @SerializedName("url")
    private String reviewUrl;

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getReviewUrl() {
        return reviewUrl;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setReviewUrl(String reviewUrl) {
        this.reviewUrl = reviewUrl;
    }
}
