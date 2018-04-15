package com.android.anmol.feeds_cleanarch.feeds;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.anmol.feeds_cleanarch.R;

/**
 * Created by anmolsehgal on 27-03-2018.
 * <p>
 * Handles the clicks on the feed view.
 */
public class FeedClickCallbackListener implements View.OnClickListener {

    /**
     * View holder hosting the views.
     */
    private RecyclerView.ViewHolder mViewHolder;

    /**
     * Callback for the clicks.
     */
    private FeedClickListener mFeedClickCallback;

    public FeedClickCallbackListener(RecyclerView.ViewHolder viewHolder, FeedClickListener feedClickCallback) {
        mViewHolder = viewHolder;
        mFeedClickCallback = feedClickCallback;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // Trigger the callback on various clicks.

            case R.id.rv_feed_container:
                mFeedClickCallback.onFeedClicked(mViewHolder.getAdapterPosition());
                break;

            case R.id.btn_like:
                mFeedClickCallback.onLikeButtonClicked(mViewHolder.getAdapterPosition());
                break;
        }
    }
}