package com.ditto.popularmovies.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("key")
    @Expose
    private String key;

    @SerializedName("name")
    @Expose
    private String name;

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
