package com.ditto.popularmovies.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import com.ditto.popularmovies.R;
import com.ditto.popularmovies.models.Movie;
import com.ditto.popularmovies.models.Video;
import com.ditto.popularmovies.utlis.CommonUtils;
import com.ditto.popularmovies.utlis.Constants;
import com.ditto.popularmovies.viewmodels.MovieDetailViewModel;
import com.ditto.popularmovies.viewmodels.ViewModelProviderFactory;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.squareup.picasso.Picasso;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieDetailFragment extends BaseFragment {

    private static final String TAG = "MovieDetailFragment";

    private MovieDetailViewModel viewModel;

    @BindView(R.id.iv_image)
    ImageView iv_image;

    @BindView(R.id.iv_youtube)
    ImageView iv_youtube;

    @BindView(R.id.tv_description)
    TextView tv_description;

    Movie movie;

    private String transitionImage;
    private String transitionText;

    String trailerId = "";

    @Inject
    ViewModelProviderFactory providerFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        ButterKnife.bind(this, view);

        iv_image.setTransitionName(transitionImage);
        tv_description.setTransitionName(transitionText);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Picasso.get().load(CommonUtils.buildImageUrl(movie.getBackdropPath(), Constants.image_size5).toString()).into(iv_image);

        viewModel = ViewModelProviders.of(this, providerFactory).get(MovieDetailViewModel.class);
        viewModel.setMovie(movie);

        subscribeObervers();

        if (CommonUtils.isNetworkAvailable(getContext())) {
            viewModel.getMovieTrailer();
        }

        tv_description.setText(movie.getOverview());
    }

    @Override
    public void onResume() {
        super.onResume();
        getCurrentActivity().showBackButton(true);
        getCurrentActivity().setToolbarTitle(movie.getTitle());
        getCurrentActivity().setToolbarSubTitle(movie.getReleaseDate());
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    private void subscribeObervers() {
        viewModel.observeMovies().removeObservers(getViewLifecycleOwner());
        viewModel.observeMovies().observe(getViewLifecycleOwner(), listResource -> {
            if (listResource != null) {
                switch (listResource.status) {

                    case LOADING: {
                        Log.d(TAG, "onChanged: LOADING...");
                        break;
                    }

                    case SUCCESS: {
                        Log.d(TAG, "onChanged: got videos...");
                        if (listResource.data != null) {
                            showTrailer(listResource.data.getVideos());
                        }
                        break;
                    }

                    case ERROR: {
                        Log.e(TAG, "onChanged: ERROR..." + listResource.message);

                        break;
                    }
                }
            }
        });
    }

    private void showTrailer(List<Video> videos) {
        if (videos != null) {
            iv_youtube.setVisibility(View.VISIBLE);
            trailerId = videos.get(0).getKey();
        }
    }

    @OnClick(R.id.iv_image)
    public void onPlayImageClicked(View view) {
        if (trailerId != null && !trailerId.equals("")) {
            Intent intent = YouTubeStandalonePlayer.createVideoIntent(getActivity(), Constants.Youtube_API_KEY, trailerId, 0, true, false);
            startActivity(intent);
        }
    }

    public void setTransitionImage(String transitionImage) {
        this.transitionImage = transitionImage;
    }

    public void setTransitionText(String transitionText) {
        this.transitionText = transitionText;
    }
}
























