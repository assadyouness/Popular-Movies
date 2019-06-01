package com.ditto.popularmovies.ui.fragments;

import android.os.Handler;

import dagger.android.support.DaggerFragment;

class BaseFragment extends DaggerFragment {

    private static final String TAG = "BaseFragment";
    private Handler mBaseHandler = new Handler();

    void doIn(Runnable r) {
        mBaseHandler.postDelayed(r, (long) 100);
    }
}




















