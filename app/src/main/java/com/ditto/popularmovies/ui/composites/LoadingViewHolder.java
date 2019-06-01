package com.ditto.popularmovies.ui.composites;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ditto.popularmovies.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadingViewHolder extends RecyclerViewHolder {

    public static final int TYPE_LOAD_MORE = -999;

    @BindView(R.id.pb_pagination)
    protected ProgressBar mProgressbar;

    public LoadingViewHolder(Context context, ViewGroup viewGroup) {
        super(context, R.layout.loader_loader, viewGroup, TYPE_LOAD_MORE, false);
        ButterKnife.bind(this, itemView);

    }
}
