package com.android.anmol.feeds_cleanarch.base;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.android.anmol.feeds_cleanarch.feeds.model.BaseFeedItemModel;

import java.util.List;

/**
 * Created by anmolsehgal on 27-03-2018.
 * <p>
 * Core Adapter's utilities.
 */
public abstract class BaseRecyclerViewAdapter<T extends BaseFeedItemModel> extends RecyclerView.Adapter {

    @Nullable
    private List<T> mList;
    private boolean mIsSelectionMode;

    public BaseRecyclerViewAdapter(List<T> list) {
        mList = list;
    }

    public List<T> getList() {
        return mList;
    }

    public T getItemAt(int pos) {
        return mList.get(pos);
    }

    public void setItemAt(int pos, T data) {
        mList.set(pos, data);
    }

    public void removeItemAt(int pos) {
        mList.remove(pos);
    }

    public void remove(T item) {
        mList.remove(item);
    }

    public void setSelectionMode(boolean modeStatus) {
        mIsSelectionMode = modeStatus;
    }

    public boolean getSelectionMode() {
        return mIsSelectionMode;
    }

    @Override
    public int getItemViewType(int position) {
        if (mList != null && mList.get(position) != null) {
            return mList.get(position).getFeedType();
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
