package com.android.anmol.feeds_cleanarch.feeds;

import android.support.annotation.NonNull;

import com.android.anmol.feeds_cleanarch.base.BasePresenter;
import com.android.anmol.feeds_cleanarch.base.BaseView;
import com.android.anmol.feeds_cleanarch.feeds.model.BaseFeedItemModel;
import com.android.anmol.feeds_cleanarch.feeds.model.FeedModel;

import java.util.List;

/**
 * Created by anmolsehgal on 27-03-2018.
 * <p>
 * This specifies the contract between the view and the presenter.
 */
public interface FeedsContract {

    interface View extends BaseView<FeedsPresenter> {

        void setLoadingIndicator(boolean active);

        void showFeeds(List<BaseFeedItemModel> feeds);

        void showFeedDetailsUi(int feedPos);

        boolean isActive();

        void showLoadingFeedsError(boolean showError);

        void updateView(int feedPositionToUpdate, FeedModel newFeed);
    }

    interface FeedsPresenter extends BasePresenter {

        void fetchFeeds(boolean forceUpdate);

        void openFeedDetails(int feedPos);

        void configureLikeStatus(@NonNull FeedModel feedModel, int pos);

        void refreshLikeStatus(FeedModel feedModel, int feedPos);
    }
}
