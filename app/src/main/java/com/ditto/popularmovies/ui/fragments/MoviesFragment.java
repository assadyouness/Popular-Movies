package com.ditto.popularmovies.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ditto.popularmovies.R;
import com.ditto.popularmovies.models.Movie;
import com.ditto.popularmovies.ui.adapters.MoviesRecyclerAdapter;
import com.ditto.popularmovies.ui.adapters.MoviesRecyclerAdapter.OnMovieItemClickedListener;
import com.ditto.popularmovies.utlis.CommonUtils;
import com.ditto.popularmovies.viewmodels.MoviesViewModel;
import com.ditto.popularmovies.viewmodels.ViewModelProviderFactory;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoviesFragment extends BaseFragment implements OnMovieItemClickedListener {

    private static final String TAG = "MoviesFragment";

    private MoviesViewModel viewModel;

    private MoviesRecyclerAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.group_no_internet_alert)
    Group group_no_internet;

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

        if(CommonUtils.isNetworkAvailable(getContext())){
            viewModel.getMoreMovies();
        }
        else {
            showNoInternetAlertView(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getCurrentActivity().showBackButton(false);
        getCurrentActivity().setToolbarTitle(getString(R.string.app_name));
        getCurrentActivity().setToolbarSubTitle("");
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

                        if(listResource.data != null && listResource.data.getThrowable() instanceof IOException){
                            if(viewModel.getPageIndex() == 1) {
                                showNoInternetAlertView(true);
                            }
                            else {
                                adapter.setLoadingNextPage(false);
                                adapter.notifyItemChanged(adapter.getLastItemIndex());
                            }
                        }
                        else {
                            Toast.makeText(getContext(),getString(R.string.alert_invalid_api_key),Toast.LENGTH_LONG).show();
                        }

                        break;
                    }
                }
            }
        });
    }

    private void showNoInternetAlertView(boolean show){
        group_no_internet.setVisibility(show?View.VISIBLE:View.GONE);
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
            adapter.notifyItemChanged(adapter.getLastItemIndex());
        }

        if(hasNextPage){
            adapter.setOnNextPageLoadListener(new MoviesRecyclerAdapter.OnNextPageLoadListener() {
                @Override
                public void onLoadingNextPage() {
                    if(CommonUtils.isNetworkAvailable(getContext())) {
                        viewModel.getMoreMovies();
                        adapter.setLoadingNextPage(true);
                        doIn(() -> adapter.notifyItemChanged(adapter.getLastItemIndex()));
                    }
                    else {
                        Snackbar.make(group_no_internet,getString(R.string.alert_no_internet),Snackbar.LENGTH_LONG).show();
                    }
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
        if(adapter == null){
            adapter = new MoviesRecyclerAdapter(getContext());
        }
        adapter.setOnItemClickedListener(this);
        recyclerView.setAdapter(adapter);

    }

    @OnClick(R.id.bt_retry)
    protected void onRetryClicked(View view){
        showNoInternetAlertView(false);
        viewModel.getMoreMovies();
    }

    @Override
    public void onMovieItemClicked(Movie movie, ImageView imageView, TextView textView) {

        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        movieDetailFragment.setMovie(movie);
        movieDetailFragment.setTransitionImage(ViewCompat.getTransitionName(imageView));
        movieDetailFragment.setTransitionText(ViewCompat.getTransitionName(textView));

        pushFragment(movieDetailFragment,true,MovieDetailFragment.class.getName(),imageView,textView);
    }

}
























