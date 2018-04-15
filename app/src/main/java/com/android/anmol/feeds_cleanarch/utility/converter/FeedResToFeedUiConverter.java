package com.android.anmol.feeds_cleanarch.utility.converter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.android.anmol.feeds_cleanarch.R;
import com.android.anmol.feeds_cleanarch.feed_details.LikeButtonViewManager;
import com.android.anmol.feeds_cleanarch.feeds.model.FeedModel;
import com.android.anmol.feeds_cleanarch.source.ResFeed;
import com.android.anmol.feeds_cleanarch.utility.ResUtils;

/**
 * Created by anmolsehgal on 27-03-2018.
 * <p>
 * Convert the List of Feeds as returned from the server to the list of Feeds to render on UI.
 */
class FeedResToFeedUiConverter implements ModelConverter<ResFeed, FeedModel> {

    private Context mContext;

    FeedResToFeedUiConverter(Context context) {
        mContext = context;
    }

    @Nullable
    @Override
    public FeedModel convert(@Nullable ResFeed source) {

        if (source == null) {
            return null;
        }

        boolean isFeedTextVisible = true;
        int ivHeight = ResUtils.getDimen(mContext, R.dimen.image_dimen);
        int ivWidth = ResUtils.getDimen(mContext, R.dimen.image_dimen);
        int ivMargin = ResUtils.getDimen(mContext, R.dimen.spacing);

        if (source.getText() == null) {
            // In case of no Text in the Feed, make Image width match parent and hide the TextView.
            isFeedTextVisible = false;
            ivWidth = RecyclerView.LayoutParams.MATCH_PARENT;
            ivHeight = ResUtils.getDimen(mContext, R.dimen.image_dimen);
            ivMargin = 0;
        }

        // Construct the Feed Model for the UI render.
        FeedModel feedModelWithoutButtonStatus = FeedModel.newBuilder()
                .withId(source.getId())
                .withName(ResUtils.getString(mContext, R.string.from_name, source.getName()))
                .withText(source.getText())
                .withImageUrl(source.getImageUrl())
                .withTitle(source.getTitle())
                .withTime(source.getTime())
                .withDescription(source.getDescription())
                .withIsFeedTextVisible(isFeedTextVisible)
                .withIvWidth(ivWidth)
                .withIvHeight(ivHeight)
                .withIvMargin(ivMargin)
                .withIsImageVisible(source.getImageUrl() != null)
                .build();

        // Add the Like button configuration in the feeds as well.
        return LikeButtonViewManager.getUpdatedFeed(feedModelWithoutButtonStatus, source.getFeedStatus());
    }
}