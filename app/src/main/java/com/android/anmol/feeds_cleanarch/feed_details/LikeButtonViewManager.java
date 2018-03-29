package com.android.anmol.feeds_cleanarch.feed_details;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

import com.android.anmol.feeds_cleanarch.R;
import com.android.anmol.feeds_cleanarch.feeds.FeedStatusType;
import com.android.anmol.feeds_cleanarch.feeds.model.FeedModel;

/**
 * Created by anmolsehgal on 28-03-2018.
 * <p>
 * Handles View update for Like button.
 */
public class LikeButtonViewManager {

    /**
     * Get the Feed updated with Like status.
     *
     * @param feedWithoutBtn Feed with old like status or no like status set.
     * @param likeStatus     Like status to set.
     * @return Updated Feed with updated like status.
     */
    public static FeedModel getUpdatedFeed(FeedModel feedWithoutBtn, @FeedStatusType final int likeStatus) {

        @DrawableRes
        int btnLikeBackgroundResId = R.drawable.like_btn_selector;

        @ColorRes
        int mBtnLikeTextColorResId = android.R.color.white;

        switch (likeStatus) {
            case FeedStatusType.LIKE:
                break;

            case FeedStatusType.UNLIKE:
                btnLikeBackgroundResId = R.drawable.unlike_btn_selector;
                mBtnLikeTextColorResId = R.color.colorPrimary;
                break;
        }

        return FeedModel
                .newBuilder(feedWithoutBtn)
                .withFeedStatus(likeStatus)
                .withBtnLikeBackgroundResId(btnLikeBackgroundResId)
                .withBtnLikeTextColorResId(mBtnLikeTextColorResId)
                .build();
    }
}
