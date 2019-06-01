package com.ditto.popularmovies.ui.composites;

import android.content.Context;
import android.content.res.Resources;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public abstract class RecyclerArrayAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {


    /**
     * Lock used to modify the content ofInt {@link #mDataSet}. Any write operation
     * performed on the array should be synchronized on this lock.
     */
    private final Object mLock = new Object();
    protected ArrayList<T> mDataSet;
    protected Context mContext;
    protected Resources mResources;
    /**
     * Indicates whether or not {@link #notifyDataSetChanged()} must be called
     * whenever {@link #mDataSet} is modified.
     */
    protected boolean mNotifyOnChange = true;
    /**
     * Indicates the headers' count
     */
    protected int mStartOffset = 0;
    /**
     * Indicates the footers' count
     */
    protected int mEndOffset = 0;


    public RecyclerArrayAdapter(Context context, List<T> dataSet) {
        mContext = context;
        if (mContext != null) {
            mResources = mContext.getResources();
        }
        mDataSet = new ArrayList<>(dataSet);
    }

    /**
     * Must be called in the constructor to indicates the headers' count
     */
    public void setStartOffset(int startOffset) {
        mStartOffset = startOffset;
    }

    /**
     * Must be called in the constructor to indicates the footers' count
     */
    public void setEndOffset(int endOffset) {
        mEndOffset = endOffset;
    }

    public T getItemInDataSet(int position) {
        if (mDataSet.size() > position) {
            return mDataSet.get(position);
        } else return null;
    }

    public int getPositionInDataSet(int absolutePosition) {
        return absolutePosition - mStartOffset;
    }

    public int getPositionAbsolute(int positionInDataSet) {
        return positionInDataSet + mStartOffset;
    }

    /**
     * Adds the specified object at the end ofInt the array.
     *
     * @param object The object to add at the end ofInt the array.
     */
    public void add(T object) {
        synchronized (mLock) {
            if (mDataSet != null) {
                mDataSet.add(object);
                if (mNotifyOnChange) {
                    notifyItemInserted(getItemCount() - mEndOffset);
                }
            }
        }
    }

    /**
     * Adds the specified Collection at the end ofInt the array.
     *
     * @param collection The Collection to add at the end ofInt the array.
     */
    public void addAll(Collection<? extends T> collection) {
        int size;
        synchronized (mLock) {
            size = getItemCount() - mEndOffset;
            if (mDataSet != null) {
                mDataSet.addAll(collection);
                if (mNotifyOnChange) {
                    notifyItemRangeInserted(size, collection.size());
                }
            }
        }
    }

    /**
     * Adds the specified items at the end ofInt the array.
     *
     * @param items The items to add at the end ofInt the array.
     */
    public void addAll(T... items) {
        int size;
        synchronized (mLock) {
            size = getItemCount() - mEndOffset;
            if (mDataSet != null) {
                Collections.addAll(mDataSet, items);
                if (mNotifyOnChange) {
                    notifyItemRangeInserted(size, items.length);
                }
            }
        }
    }

    /**
     * Inserts the specified object at the specified index in the array.
     *
     * @param object The object to insert into the array.
     * @param index  The index at which the object must be inserted.
     */
    public void insert(T object, int index) {
        synchronized (mLock) {
            if (mDataSet != null) {
                mDataSet.add(index - mStartOffset, object);
                if (mNotifyOnChange)
                    notifyItemInserted(index);
            }
        }
    }

    /**
     * Removes the object at the specified index in the array.
     *
     * @param index The index at which the object must be removed.
     */
    public void remove(int index) {
        synchronized (mLock) {
            if (mDataSet != null) {
                mDataSet.remove(index - mStartOffset);
                if (mNotifyOnChange)
                    notifyItemRemoved(index);
            }
        }
    }

    /**
     * Removes the object at the specified index in the array.
     *
     * @param object The object which must be removed.
     */
    public void remove(T object) {
        synchronized (mLock) {
            if (mDataSet != null) {
                int index = mDataSet.indexOf(object);
                mDataSet.remove(object);
                if (mNotifyOnChange)
                    notifyItemRemoved(index + mStartOffset);
            }
        }
    }

    /**
     * Remove all elements from the list.
     */
    public void clear() {
        final int size = getItemCount() - mStartOffset - mEndOffset;
        mDataSet.clear();
        notifyItemRangeRemoved(mStartOffset, size);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size() + mStartOffset + mEndOffset;
    }

    public int getLastItemIndex() {
        return mDataSet.size() + mStartOffset + mEndOffset - 1;
    }

    public int getLastDataSetItemIndex() {
        return mDataSet.size() + mStartOffset - 1;
    }
}
