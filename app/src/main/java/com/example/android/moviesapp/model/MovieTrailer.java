package com.example.android.moviesapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieTrailer {

    @SerializedName("key")
    private String key;
    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private String type;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
