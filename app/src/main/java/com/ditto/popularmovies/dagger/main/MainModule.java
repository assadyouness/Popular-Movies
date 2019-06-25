package com.ditto.popularmovies.dagger.main;

import com.ditto.popularmovies.network.main.MainApi;
import com.ditto.popularmovies.repositories.MovieDetailRepository;
import com.ditto.popularmovies.repositories.MoviesRepository;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @MainScope
    @Provides
    static MainApi provideMainApi(Retrofit retrofit){
        return retrofit.create(MainApi.class);
    }

    @MainScope
    @Provides
    static MoviesRepository provideMoviesRepository(MainApi mainApi){
        return new MoviesRepository(mainApi);
    }

    @MainScope
    @Provides
    static MovieDetailRepository provideMovieDetailRepository(MainApi mainApi){
        return new MovieDetailRepository(mainApi);
    }
}
