package com.ditto.popularmovies.network.main.responses;

import com.ditto.popularmovies.models.Movie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesResponse {

    @Expose
    @SerializedName(("results"))
    private List<Movie> movies;

    @Expose
    @SerializedName(("page"))
    private int page;

    private boolean isError = false;

    public List<Movie> getMovies() {
        return movies;
    }

    public int getPage() {
        return page;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public boolean isError() {
        return isError;
    }
}
