package com.android.anmol.feeds_cleanarch.feeds;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.anmol.feeds_cleanarch.R;
import com.android.anmol.feeds_cleanarch.feeds.model.FeedModel;
import com.android.anmol.feeds_cleanarch.utility.ImageLoadUtils;
import com.android.anmol.feeds_cleanarch.utility.ResUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by anmolsehgal on 27-03-2018.
 * <p>
 * View Manager to render the Feed UI.
 */
class FeedHolder extends RecyclerView.ViewHolder {

    private final View mView;

    @BindView(R.id.tv_feed_text)
    TextView mTvFeedText;

    @BindView(R.id.iv_feed_image)
    ImageView mIvFeedImage;

    @BindView(R.id.tv_feed_title)
    TextView mTvFeedTitle;

    @BindView(R.id.btn_like)
    Button mBtnLike;

    @BindView(R.id.tv_from_person)
    TextView mTvFeedFromPerson;

    @BindView(R.id.tv_image_error)
    TextView mTvImageLoadError;

    @BindView(R.id.progress_bar_loading)
    ProgressBar mProgressBar;

    FeedHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, itemView);
    }

    /**
     * Bind the view with the Feed to show on the UI.
     *
     * @param feed              Feed to render on the UI.
     * @param feedClickCallback Callback to listen to the view clicks.
     */
    void bind(FeedModel feed, FeedClickListener feedClickCallback) {
        // Get data from Feed and update the view.

        mTvFeedText.setText(feed.getText());
        mTvFeedTitle.setText(feed.getTitle());

        mBtnLike.setBackground(ResUtils.getDrawable(mBtnLike.getContext(), feed.getBtnLikeBackgroundResId()));
        mBtnLike.setTextColor(ResUtils.getColor(mBtnLike.getContext(), feed.getBtnLikeTextColorResId()));
        mBtnLike.setText(ResUtils.getString(mBtnLike.getContext(), feed.getFeedStatus()));

        mTvFeedFromPerson.setText(feed.getName());

        mTvFeedText.setVisibility(feed.isFeedTextVisible() ? View.VISIBLE : View.GONE);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                feed.getIvWidth(),
                feed.getIvHeight());
        params.setMarginEnd(feed.getIvMarginEnd());

        mIvFeedImage.setLayoutParams(params);

        ImageLoadUtils.loadRoundedImage(feed.getImageUrl(),
                mIvFeedImage,
                mProgressBar,
                mTvImageLoadError,
                feed.isFeedTextVisible());

        mIvFeedImage.setVisibility(feed.isIsImageVisible() ? View.VISIBLE : View.GONE);

        FeedClickCallbackListener onClickListener = new FeedClickCallbackListener(this, feedClickCallback);

        mView.setOnClickListener(onClickListener);
        mBtnLike.setOnClickListener(onClickListener);
    }
}

