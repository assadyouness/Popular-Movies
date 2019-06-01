package com.ditto.popularmovies.ui.composites;

import android.content.Context;
import android.view.ViewGroup;

import com.ditto.popularmovies.R;

public class EmptyViewHolder extends RecyclerViewHolder {

    public static final int TYPE_EMPTY = -999;

    public EmptyViewHolder(Context context, ViewGroup viewGroup) {
        super(context, R.layout.listitem_empty, viewGroup, TYPE_EMPTY, false);
    }

}
