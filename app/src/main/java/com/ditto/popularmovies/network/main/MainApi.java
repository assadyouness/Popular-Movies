package com.ditto.popularmovies.network.main;

import com.ditto.popularmovies.network.main.responses.MovieTrailerResponse;
import com.ditto.popularmovies.network.main.responses.MoviesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MainApi {

    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/{movie_id}")
    Call<MovieTrailerResponse> getMovieDetail(@Path("movie_id") String movieId,@Query("api_key") String apiKey, @Query("append_to_response") String appendToResp);

}