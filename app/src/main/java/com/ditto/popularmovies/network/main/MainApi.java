package com.ditto.popularmovies.network.main;

import com.ditto.popularmovies.network.main.responses.MoviesResponse;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainApi {

    @GET("movie/popular")
    Flowable<MoviesResponse> getPopularMovies(@Query("api_key") String apiKey,@Query("page") int page);

}