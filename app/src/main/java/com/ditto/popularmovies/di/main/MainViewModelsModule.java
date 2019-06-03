package com.ditto.popularmovies.di.main;

import androidx.lifecycle.ViewModel;
import com.ditto.popularmovies.di.ViewModelKey;
import com.ditto.popularmovies.viewmodels.MovieDetailViewModel;
import com.ditto.popularmovies.viewmodels.MoviesViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel.class)
    public abstract ViewModel bindMovieViewModel(MoviesViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel.class)
    public abstract ViewModel bindMovieDetailViewModel(MovieDetailViewModel viewModel);

}




