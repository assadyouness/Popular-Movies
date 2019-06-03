package com.ditto.popularmovies.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.ditto.popularmovies.models.Movie;
import com.ditto.popularmovies.network.Resource;
import com.ditto.popularmovies.network.main.MainApi;
import com.ditto.popularmovies.network.main.responses.MovieTrailerResponse;
import com.ditto.popularmovies.network.main.responses.MoviesResponse;
import com.ditto.popularmovies.utlis.Constants;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailViewModel extends ViewModel {

    private static final String TAG = "MovieDetailViewModel";

    // inject
    private final MainApi mainApi;
    private Movie movie;

    private MediatorLiveData<Resource<MovieTrailerResponse>> movieTrailerResp;

    @Inject
    public MovieDetailViewModel(MainApi mainApi) {
        this.mainApi = mainApi;
        movieTrailerResp = new MediatorLiveData<>();
    }


    public LiveData<Resource<MovieTrailerResponse>> observeMovies(){
        return movieTrailerResp;
    }

    public void getMovieTrailer() {

        final LiveData<Resource<MovieTrailerResponse>> source = LiveDataReactiveStreams.fromPublisher(

                mainApi.getMovieDetail(String.valueOf(movie.getId()),Constants.API_KEY,"videos")
                        .onErrorReturn(throwable -> {
                            Log.e(TAG, "apply: ", throwable);
                            MovieTrailerResponse movieTrailerResponse = new MovieTrailerResponse();
                            movieTrailerResponse.setError(true);
                            return movieTrailerResponse;
                        })

                        .map((Function<MovieTrailerResponse, Resource<MovieTrailerResponse>>) moviesResponse -> {

                            if (moviesResponse.isError()) {
                                return Resource.error("Something went wrong", null);
                            }
                            else {
                                return Resource.success(moviesResponse);
                            }

                        })

                        .subscribeOn(Schedulers.io())
        );

        movieTrailerResp.addSource(source, moviesResponseResource -> {
            movieTrailerResp.setValue(moviesResponseResource);
            movieTrailerResp.removeSource(source);
        });

    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
