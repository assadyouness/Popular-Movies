package com.ditto.popularmovies.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ditto.popularmovies.models.Movie;
import com.ditto.popularmovies.network.APIStatus;
import com.ditto.popularmovies.network.main.MainApi;
import com.ditto.popularmovies.repositories.MoviesRepository;

import java.util.List;

import javax.inject.Inject;

public class MoviesViewModel extends ViewModel {

    private static final String TAG = "MoviesViewModel";

    // inject
    private final MoviesRepository moviesRepository;

    @Inject
    MoviesViewModel(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
        getMoreMovies();
    }


    public LiveData<List<Movie>> observeMovies(){
        return moviesRepository.getMovies();
    }
    public LiveData<APIStatus> observeState(){
        return moviesRepository.getAPIStatus();
    }

    public void getMoreMovies() {

        moviesRepository.getDataFromNetwork();

    }

    public int getPageIndex() {
        return moviesRepository.getPageIndex();
    }

    public boolean hasNextPage(){
        return moviesRepository.hasNextPage();
    }
}
