package com.ditto.popularmovies.dagger;

import androidx.lifecycle.ViewModelProvider;

import com.ditto.popularmovies.viewmodels.ViewModelProviderFactory;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelFactory);

}
