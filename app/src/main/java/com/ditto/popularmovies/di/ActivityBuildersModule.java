package com.ditto.popularmovies.di;

import com.ditto.popularmovies.di.main.MainFragmentBuildersModule;
import com.ditto.popularmovies.di.main.MainModule;
import com.ditto.popularmovies.di.main.MainScope;
import com.ditto.popularmovies.di.main.MainViewModelsModule;
import com.ditto.popularmovies.ui.activities.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {


    @MainScope
    @ContributesAndroidInjector(
            modules = {MainFragmentBuildersModule.class, MainViewModelsModule.class, MainModule.class}
    )
    abstract MainActivity contributeMainActivity();

}
