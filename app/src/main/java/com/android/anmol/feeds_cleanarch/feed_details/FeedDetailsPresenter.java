package com.android.anmol.feeds_cleanarch.feed_details;

import android.support.annotation.NonNull;

import com.android.anmol.feeds_cleanarch.feeds.FeedStatusType;
import com.android.anmol.feeds_cleanarch.feeds.model.FeedModel;
import com.android.anmol.feeds_cleanarch.source.FeedsDataSource;
import com.android.anmol.feeds_cleanarch.source.FeedsRepository;
import com.android.anmol.feeds_cleanarch.utility.Utils;

/**
 * Created by anmolsehgal on 27-03-2018.
 * <p>
 * Listens to user actions from the UI ({@link FeedDetailsActivity}), retrieves the data and updates the
 * UI as required.
 */
public class FeedDetailsPresenter implements FeedDetailsContract.FeedDetailsPresenter {

    private FeedsRepository mFeedsRepository;

    private FeedDetailsContract.View mView;

    FeedDetailsPresenter(FeedsRepository feedsRepository, FeedDetailsContract.View view) {
        mFeedsRepository = Utils.checkNotNull(feedsRepository, "feedsRepository can not be null");
        mView = Utils.checkNotNull(view, "view can not be null");

        mView.setPresenter(this);
    }

    @Override
    public void configureLikeStatus(@NonNull final FeedModel feedModel) {
        Utils.checkNotNull(feedModel, "Feed position clicked can not be null");

        mFeedsRepository.configureLikeStatus(feedModel.getId(), feedModel.getFeedStatus(), new FeedsDataSource.FeedLikeStatus() {
            @Override
            public void configureLikeStatus(@FeedStatusType final int likeStatus) {
                mView.updateView(LikeButtonViewManager.getUpdatedFeed(feedModel, likeStatus));
            }
        });
    }
}
