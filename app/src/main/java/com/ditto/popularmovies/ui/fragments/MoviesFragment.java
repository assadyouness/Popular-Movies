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

import com.bumptech.glide.RequestManager;
import com.ditto.popularmovies.R;
import com.ditto.popularmovies.models.Movie;
import com.ditto.popularmovies.ui.adapters.MoviesRecyclerAdapter;
import com.ditto.popularmovies.viewmodels.MoviesViewModel;
import com.ditto.popularmovies.viewmodels.ViewModelProviderFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesFragment extends BaseFragment {

    private static final String TAG = "MoviesFragment";

    private MoviesViewModel viewModel;

    private MoviesRecyclerAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    RequestManager requestManager;

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
        viewModel.getMoreMovies();
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
                            updateRecyclerAdapter(listResource.data.getMovies(),listResource.data.getPage(),listResource.data.getTotalPages());
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

    private void updateRecyclerAdapter(List<Movie> movies, int page, int totalPages) {
        movies = (movies == null ? new ArrayList<>() : movies);

        final int size = movies.size();
        final boolean hasNextPage = totalPages != page;

        if (page == 1) {
            adapter.setMovies(movies);
        } else if (size > 0) {
            // loading more -> with result
            Movie movie;
            for (int i = 0; i < size; i++) {
                movie = movies.get(i);
                if (!adapter.getMovies().contains(movie)) {
                    adapter.insert(movie, adapter.getLastItemIndex());
                }
            }
            adapter.setLoadingNextPage(false);
            adapter.notifyDataSetChanged();
        }

        if(hasNextPage){
            adapter.setOnNextPageLoadListener(new MoviesRecyclerAdapter.OnNextPageLoadListener() {
                @Override
                public void onLoadingNextPage() {
                    viewModel.getMoreMovies();
                    adapter.setLoadingNextPage(true);
                    doIn(() -> adapter.notifyItemChanged(adapter.getLastItemIndex()));
                }
            });
        }
        else {
            adapter.setLoadingEnabled(false);
            adapter.setLoadingNextPage(false);
            adapter.notifyItemChanged(adapter.getLastItemIndex());
        }

    }



    private void showProgress(boolean show){
        progressBar.setVisibility(show?View.VISIBLE:View.GONE);
    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MoviesRecyclerAdapter(getContext(),requestManager);
        recyclerView.setAdapter(adapter);

    }
}
























