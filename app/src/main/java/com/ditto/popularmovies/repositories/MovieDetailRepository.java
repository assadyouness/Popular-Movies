package com.ditto.popularmovies.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ditto.popularmovies.models.Movie;
import com.ditto.popularmovies.network.APIStatus;
import com.ditto.popularmovies.network.ApiCallback;
import com.ditto.popularmovies.network.main.MainApi;
import com.ditto.popularmovies.network.main.responses.MovieTrailerResponse;
import com.ditto.popularmovies.utlis.Constants;

import javax.inject.Inject;

import retrofit2.Response;

public class MovieDetailRepository {

    private final MainApi mainApi;
    private MutableLiveData<MovieTrailerResponse> mMoviesTrailerResponse;
    private MutableLiveData<APIStatus> apiStatus;
    private Movie movie;

    @Inject
    public MovieDetailRepository(MainApi mainApi) {

        this.mainApi = mainApi;

        mMoviesTrailerResponse = new MutableLiveData<>();
        apiStatus = new MutableLiveData<>();

    }

    public LiveData<MovieTrailerResponse> getMovieTrailer() {
        return mMoviesTrailerResponse;
    }

    public LiveData<APIStatus> getAPIStatus() {
        return apiStatus;
    }


    public void getDataFromNetwork(){

        apiStatus.setValue(APIStatus.loading());

        mainApi.getMovieDetail(String.valueOf(movie.getId()),Constants.API_KEY,"videos").enqueue(new ApiCallback<MovieTrailerResponse>() {
            @Override
            public void success(MovieTrailerResponse movieTrailerResponse, Response<MovieTrailerResponse> response) {

                mMoviesTrailerResponse.setValue(movieTrailerResponse);

            }

            @Override
            public void failure(boolean io, MovieTrailerResponse movieTrailerResponse, String message, String error) {
                apiStatus.setValue(APIStatus.error(message, io));
            }
        });
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
