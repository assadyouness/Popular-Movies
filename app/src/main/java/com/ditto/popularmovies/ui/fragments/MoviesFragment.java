package com.ditto.popularmovies.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ditto.popularmovies.R;
import com.ditto.popularmovies.ui.adapters.MoviesRecyclerAdapter;
import com.ditto.popularmovies.viewmodels.MoviesViewModel;
import com.ditto.popularmovies.viewmodels.ViewModelProviderFactory;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesFragment extends BaseFragment {

    private static final String TAG = "MoviesFragment";

    private MoviesViewModel viewModel;

    @Inject
    MoviesRecyclerAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this, providerFactory).get(MoviesViewModel.class);

        initRecyclerView();
        subscribeObervers();
    }

    private void subscribeObervers(){
        viewModel.observeMovies().removeObservers(getViewLifecycleOwner());
        viewModel.observeMovies().observe(getViewLifecycleOwner(), listResource -> {
            if(listResource != null){
                switch (listResource.status){

                    case LOADING:{
                        Log.d(TAG, "onChanged: LOADING...");
                        showProgress(true);
                        break;
                    }

                    case SUCCESS:{
                        Log.d(TAG, "onChanged: got movies...");
                        showProgress(false);
                        if (listResource.data != null) {
                            adapter.setMovies(listResource.data.getMovies());
                        }
                        break;
                    }

                    case ERROR:{
                        Log.e(TAG, "onChanged: ERROR..." + listResource.message );
                        showProgress(false);
                        break;
                    }
                }
            }
        });
    }

    private void showProgress(boolean show){
        progressBar.setVisibility(show?View.VISIBLE:View.GONE);
    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

    }
}
























