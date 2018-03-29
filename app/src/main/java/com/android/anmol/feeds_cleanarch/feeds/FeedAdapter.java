package com.android.anmol.feeds_cleanarch.feeds;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.anmol.feeds_cleanarch.R;
import com.android.anmol.feeds_cleanarch.base.BaseRecyclerViewAdapter;
import com.android.anmol.feeds_cleanarch.feeds.model.BaseFeedItemModel;
import com.android.anmol.feeds_cleanarch.feeds.model.DateHeaderModel;
import com.android.anmol.feeds_cleanarch.feeds.model.FeedModel;

import java.util.List;

/**
 * Created by anmolsehgal on 27-03-2018.
 * <p>
 * Adapter for rendering the feeds on the UI.
 */
class FeedAdapter extends BaseRecyclerViewAdapter<BaseFeedItemModel> {

    /**
     * Feeds to render.
     */
    private final List<BaseFeedItemModel> mFeeds;

    /**
     * To lister to the view clicks so that the UI can change accordingly.
     */
    private FeedClickListener mFeedClickCallback;

    // Different view types.
    private static final int VIEW_TYPE_HEADER = 1;
    private static final int VIEW_TYPE_FEED = 2;

    FeedAdapter(List<BaseFeedItemModel> feeds, FeedClickListener feedClickCallback) {
        super(feeds);
        mFeeds = feeds;
        mFeedClickCallback = feedClickCallback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder = null;

        // Return the view based on if the view is for header, or the feed.
        if (viewType == VIEW_TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed_header, parent, false);
            viewHolder = new HeaderFeedHolder(view);
        } else if (viewType == VIEW_TYPE_FEED) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
            viewHolder = new FeedHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseFeedItemModel feed = mFeeds.get(position);

        // Bind the view based on if the view is for header, or the feed.
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_HEADER:
                ((HeaderFeedHolder) holder).bind((DateHeaderModel) feed);
                break;
            case VIEW_TYPE_FEED:
                ((FeedHolder) holder).bind((FeedModel) feed, mFeedClickCallback);
        }
    }
}