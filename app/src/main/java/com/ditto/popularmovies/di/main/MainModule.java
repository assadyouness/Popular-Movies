package com.ditto.popularmovies.di.main;

import com.bumptech.glide.RequestManager;
import com.ditto.popularmovies.network.main.MainApi;
import com.ditto.popularmovies.ui.adapters.MoviesRecyclerAdapter;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @MainScope
    @Provides
    static MoviesRecyclerAdapter provideAdapter(RequestManager requestManager){
        return new MoviesRecyclerAdapter(requestManager);
    }

    @MainScope
    @Provides
    static MainApi provideMainApi(Retrofit retrofit){
        return retrofit.create(MainApi.class);
    }
}
