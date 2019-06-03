package com.ditto.popularmovies.di.main;

import com.ditto.popularmovies.ui.fragments.MovieDetailFragment;
import com.ditto.popularmovies.ui.fragments.MoviesFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract MoviesFragment contributeMoviesFragment();

    @ContributesAndroidInjector
    abstract MovieDetailFragment contributeMovieDetailFragment();

}
