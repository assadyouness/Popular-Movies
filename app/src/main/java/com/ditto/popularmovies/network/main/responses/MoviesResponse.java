package com.ditto.popularmovies.network.main.responses;

import com.ditto.popularmovies.models.Movie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesResponse extends CommonResp
{

    @Expose
    @SerializedName(("results"))
    private List<Movie> movies;

    @Expose
    @SerializedName(("page"))
    private int page;

    @Expose
    @SerializedName(("total_pages"))
    private int totalPages;

    public List<Movie> getMovies() {
        return movies;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
