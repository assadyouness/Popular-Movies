package com.ditto.popularmovies.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.ditto.popularmovies.models.Movie;
import com.ditto.popularmovies.network.ApiCallback;
import com.ditto.popularmovies.network.APIStatus;
import com.ditto.popularmovies.network.main.MainApi;
import com.ditto.popularmovies.network.main.responses.MoviesResponse;
import com.ditto.popularmovies.utlis.Constants;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;

public class MoviesRepository {

    private final MainApi mainApi;

    private MutableLiveData<List<Movie>> mMovies;
    private MutableLiveData<APIStatus> apiStatus;

    private int pageIndex = 1;
    private boolean hasNextPage = true;

    @Inject
    public MoviesRepository(MainApi mainApi) {

        this.mainApi = mainApi;

        mMovies = new MutableLiveData<>();
        apiStatus = new MutableLiveData<>();

    }

    public LiveData<List<Movie>> getMovies() {
        return mMovies;
    }

    public LiveData<APIStatus> getAPIStatus() {
        return apiStatus;
    }


    public void getDataFromNetwork(){

        apiStatus.setValue(APIStatus.loading());

        mainApi.getPopularMovies(Constants.API_KEY, pageIndex).enqueue(new ApiCallback<MoviesResponse>() {
            @Override
            public void success(MoviesResponse moviesResponse, Response<MoviesResponse> response) {

                pageIndex++;
                apiStatus.setValue(APIStatus.success());

                hasNextPage = moviesResponse.getPage() != moviesResponse.getTotalPages();
                if(mMovies.getValue() != null){
                    List<Movie> currentMovies = mMovies.getValue();
                    currentMovies.addAll(moviesResponse.getMovies());
                    mMovies.setValue(currentMovies);
                }
                else {
                    mMovies.setValue(moviesResponse.getMovies());
                }
            }

            @Override
            public void failure(boolean io, MoviesResponse moviesResponse, String message, String error) {
                apiStatus.setValue(APIStatus.error(message,io));
            }
        });
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public boolean hasNextPage() {
        return hasNextPage;
    }
}
