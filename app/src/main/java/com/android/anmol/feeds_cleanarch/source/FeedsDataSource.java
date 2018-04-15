package com.android.anmol.feeds_cleanarch.source;

import android.support.annotation.NonNull;

import com.android.anmol.feeds_cleanarch.feeds.viewholders.FeedStatusType;

/**
 * Created by anmolsehgal on 27-03-2018.
 * <p>
 * Main entry point for accessing Feeds data.
 * <p>
 * For simplicity, only getFeeds() and configureLikeStatus() have callbacks. Consider adding callbacks to other
 * methods to inform the user of network/database errors or successful operations.
 */
public interface FeedsDataSource {

    interface FetchFeedsCallback {

        void onFeedsFetched(ResFeed[] feeds);

        void onDataNotAvailable();
    }

    interface FeedLikeStatus {

        void configureLikeStatus(@FeedStatusType int likeStatus);
    }

    void getFeeds(@NonNull FetchFeedsCallback callback, boolean forceUpdate);

    void configureLikeStatus(int feedId, @FeedStatusType int oldLikeStatus, @NonNull FeedLikeStatus callback);
}