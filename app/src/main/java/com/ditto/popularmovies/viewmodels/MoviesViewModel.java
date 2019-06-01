package com.ditto.popularmovies.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import com.ditto.popularmovies.network.Resource;
import com.ditto.popularmovies.network.main.MainApi;
import com.ditto.popularmovies.network.main.responses.MoviesResponse;
import com.ditto.popularmovies.utlis.Constants;
import javax.inject.Inject;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MoviesViewModel extends ViewModel {

    private static final String TAG = "MoviesViewModel";

    private int pageIndex = 1;
    // inject
    private final MainApi mainApi;

    private MediatorLiveData<Resource<MoviesResponse>> movies;

    @Inject
    public MoviesViewModel(MainApi mainApi) {
        this.mainApi = mainApi;
        movies = new MediatorLiveData<>();
    }


    public LiveData<Resource<MoviesResponse>> observeMovies(){
        return movies;
    }

    public void getMoreMovies() {

        if(pageIndex == 1) {
            movies.setValue(Resource.loading(null));
        }

        final LiveData<Resource<MoviesResponse>> source = LiveDataReactiveStreams.fromPublisher(

                mainApi.getPopularMovies(Constants.API_KEY, pageIndex)
                        .onErrorReturn(throwable -> {
                            Log.e(TAG, "apply: ", throwable);
                            MoviesResponse moviesResponse = new MoviesResponse();
                            moviesResponse.setError(true);
                            return moviesResponse;
                        })

                        .map((Function<MoviesResponse, Resource<MoviesResponse>>) moviesResponse -> {

                            if (moviesResponse.isError()) {
                                return Resource.error("Something went wrong", null);
                            }
                            else {
                                pageIndex++;
                            }

                            return Resource.success(moviesResponse);
                        })

                        .subscribeOn(Schedulers.io())
        );

        movies.addSource(source, moviesResponseResource -> {
            movies.setValue(moviesResponseResource);
            movies.removeSource(source);
        });

    }

    public int getPageIndex() {
        return pageIndex;
    }
}
