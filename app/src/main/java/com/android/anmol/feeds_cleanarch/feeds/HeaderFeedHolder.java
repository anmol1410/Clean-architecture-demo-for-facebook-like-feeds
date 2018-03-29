package com.android.anmol.feeds_cleanarch.feeds;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.anmol.feeds_cleanarch.R;
import com.android.anmol.feeds_cleanarch.feeds.model.DateHeaderModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by anmolsehgal on 27-03-2018.
 * <p>
 * To render the Date header for the feeds.
 */
class HeaderFeedHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_feed_header)
    TextView mTvFeedDate;

    HeaderFeedHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void bind(DateHeaderModel feed) {
        mTvFeedDate.setText(feed.getDate());
    }
}
