package com.ditto.popularmovies.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.ditto.popularmovies.models.Movie;
import com.ditto.popularmovies.network.APIStatus;
import com.ditto.popularmovies.network.main.responses.MovieTrailerResponse;
import com.ditto.popularmovies.repositories.MovieDetailRepository;

import javax.inject.Inject;

public class MovieDetailViewModel extends ViewModel {

    private static final String TAG = "MovieDetailViewModel";

    private final MovieDetailRepository movieDetailRepository;

    @Inject
    public MovieDetailViewModel(MovieDetailRepository movieDetailRepository) {
        this.movieDetailRepository = movieDetailRepository;
    }


    public LiveData<MovieTrailerResponse> observeMovieTrailer(){
        return movieDetailRepository.getMovieTrailer();
    }

    public void getMovieTrailer() {
        movieDetailRepository.getDataFromNetwork();
    }

    public LiveData<APIStatus> observeState(){
        return movieDetailRepository.getAPIStatus();
    }

    public void setMovie(Movie movie) {
         movieDetailRepository.setMovie(movie);
    }
}
