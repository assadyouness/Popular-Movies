package com.ditto.popularmovies.ui.activities;

import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import com.ditto.popularmovies.R;
import com.ditto.popularmovies.ui.fragments.MoviesFragment;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addMoviesFragment();

    }

    private void addMoviesFragment() {
        MoviesFragment moviesFragment = new MoviesFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameContainer, moviesFragment, MoviesFragment.class.getName());
        fragmentTransaction.commit();
    }

}
