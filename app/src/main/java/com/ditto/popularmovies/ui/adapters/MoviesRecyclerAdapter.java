package com.ditto.popularmovies.ui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ditto.popularmovies.R;
import com.ditto.popularmovies.models.Movie;
import com.ditto.popularmovies.ui.composites.EmptyViewHolder;
import com.ditto.popularmovies.ui.composites.LoadingViewHolder;
import com.ditto.popularmovies.ui.composites.RecyclerArrayAdapter;
import com.ditto.popularmovies.ui.composites.RecyclerViewHolder;
import com.ditto.popularmovies.utlis.CommonUtils;
import com.ditto.popularmovies.utlis.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class MoviesRecyclerAdapter extends RecyclerArrayAdapter<MoviesRecyclerAdapter.Item, RecyclerView.ViewHolder> {

    public static final String TAG = "MoviesRecyclerAdapter";

    private static final int TYPE_MOVIE = 1;

    private List<Movie> movies = new ArrayList<>();

    private OnNextPageLoadListener mOnNextPageLoadListener;
    private OnMovieItemClickedListener onItemClickedListener;

    private boolean mLoadingNextPage = false;
    private boolean mLoadingEnabled = true;

    class Item {
        int itemType;
        int questIndex;

        Item(int questIndex, int questType) {
            this.questIndex = questIndex;
            this.itemType = questType;
        }
    }

    public MoviesRecyclerAdapter(Context context) {
        super(context, new ArrayList<>());
        setEndOffset(1);
    }

    public interface OnMovieItemClickedListener {
        void onMovieItemClicked(Movie movie, ImageView imageView, TextView textView);
    }

    public static abstract class OnNextPageLoadListener {
        public abstract void onLoadingNextPage();
    }

    public void setOnNextPageLoadListener(OnNextPageLoadListener l) {
        this.mOnNextPageLoadListener = l;
    }

    public void setOnItemClickedListener(OnMovieItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (getLastItemIndex() == position) {
            return LoadingViewHolder.TYPE_LOAD_MORE;
        } else {
            return TYPE_MOVIE;
        }
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;

        for (int i = 0; i < movies.size(); i++) {
            mDataSet.add(new Item(i, (TYPE_MOVIE)));
        }

        notifyDataSetChanged();
    }

    public void insert(Movie movie, int index) {
        movies.add(movie);
        insert(new Item(movies.size() - 1, (TYPE_MOVIE)), index);
    }

    public void reset(List<Movie> movies) {

        mDataSet.clear();
        this.movies = movies;

        for (int i = 0; i < this.movies.size(); i++) {
            mDataSet.add(new Item(i, (TYPE_MOVIE)));
        }
    }

    public List<Movie> getMovies() {
        return movies == null ? movies = new ArrayList<>() : movies;
    }

    public void setLoadingEnabled(boolean enabled) {
        mLoadingEnabled = enabled;
    }

    public boolean isLoadingNextPage() {
        return mLoadingNextPage;
    }

    public void setLoadingNextPage(boolean loading) {
        mLoadingNextPage = loading;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerViewHolder holder = new EmptyViewHolder(mContext, parent);

        switch (viewType) {

            case TYPE_MOVIE:
                holder = new MovieViewHolder(mContext, parent, viewType);

                break;

            case LoadingViewHolder.TYPE_LOAD_MORE:
                holder = new LoadingViewHolder(mContext, parent);

                break;
        }

        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final int viewType = getItemViewType(position);
        final int positionInDataSet = getPositionInDataSet(position);

        switch (viewType) {

            case TYPE_MOVIE:
                Movie movie = getMovies().get(position);

                final MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
                movieViewHolder.bind(movie,position);
                break;

            case LoadingViewHolder.TYPE_LOAD_MORE:
                final LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.bindItem(position, positionInDataSet);

                loadingViewHolder.itemView.setVisibility(mLoadingEnabled && mLoadingNextPage ? VISIBLE : INVISIBLE);
                if (mLoadingEnabled && !mLoadingNextPage) {
                    if (mOnNextPageLoadListener != null) {
                        mOnNextPageLoadListener.onLoadingNextPage();
                    }
                }
                break;

        }
    }


    class MovieViewHolder extends RecyclerViewHolder {

        @BindView(R.id.tv_title)
        TextView tv_title;

        @BindView(R.id.tv_description)
        TextView tv_description;

        @BindView(R.id.iv_image)
        ImageView iv_image;

        Movie movie;

        MovieViewHolder(Context context, ViewGroup viewGroup, int viewType) {
            super(context, R.layout.listitem_movie, viewGroup, viewType, false);
            ButterKnife.bind(this, itemView);

        }

        void bind(Movie movie, int position) {

            iv_image.setTransitionName(mContext.getString(R.string.transition_image) + position);
            tv_description.setTransitionName(mContext.getString(R.string.transition_text) + position);

            this.movie = movie;
            tv_title.setText(movie.getTitle());
            tv_description.setText(movie.getOverview());
            Picasso.get().load(CommonUtils.buildImageUrl(movie.getBackdropPath(), Constants.image_size5).toString()).into(iv_image);
        }

        @OnClick(R.id.card_view)
        protected void onItemClicked(View view){
            onItemClickedListener.onMovieItemClicked(movie,iv_image, tv_description);
        }
    }

}






