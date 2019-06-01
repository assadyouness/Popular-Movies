package com.ditto.popularmovies.ui.composites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public abstract class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context mContext;
    private int mLayoutId;

    private boolean isClickable;
    private int mViewType;

    private int mPositionInDataSet;
    private int mPositionAbsolute;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
    }

    private RecyclerViewHolder(Context context, int layoutId, ViewGroup viewGroup) {
        super(LayoutInflater.from(context).inflate(layoutId, viewGroup, false));

        mContext = context;
        mLayoutId = layoutId;
    }

    public RecyclerViewHolder(Context context, int layoutId, ViewGroup viewGroup, int viewType) {
        this(context, layoutId, viewGroup, viewType, false);
    }

    public RecyclerViewHolder(Context context, int layoutId, ViewGroup viewGroup, int viewType, boolean clickable) {
        this(context, layoutId, viewGroup);

        mViewType = viewType;
        isClickable = clickable;

        itemView.setEnabled(clickable);

        if (isClickable) {
            itemView.setOnClickListener(this);
        }
    }

    public void bindItem(int absolutePosition, int positionInDataSet) {
        mPositionInDataSet = positionInDataSet;
        mPositionAbsolute = absolutePosition;
    }

    public int getViewType() {
        return mViewType;
    }

    @Override
    public void onClick(View v) {
        onItemClick(v, mViewType, mPositionAbsolute, mPositionInDataSet);
    }

    public void onItemClick(View v, int viewType, int positionAbsolute, int positionInDataSet) {
    }
}