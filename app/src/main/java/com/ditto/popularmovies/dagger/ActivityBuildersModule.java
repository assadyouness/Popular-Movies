package com.ditto.popularmovies.dagger;

import com.ditto.popularmovies.dagger.main.MainFragmentBuildersModule;
import com.ditto.popularmovies.dagger.main.MainModule;
import com.ditto.popularmovies.dagger.main.MainScope;
import com.ditto.popularmovies.dagger.main.MainViewModelsModule;
import com.ditto.popularmovies.ui.activities.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityBuildersModule {


    @MainScope
    @ContributesAndroidInjector(
            modules = {MainFragmentBuildersModule.class, MainViewModelsModule.class, MainModule.class}
    )
    abstract MainActivity contributeMainActivity();

}
