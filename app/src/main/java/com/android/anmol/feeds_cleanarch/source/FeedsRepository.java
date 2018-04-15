/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.anmol.feeds_cleanarch.source;

import android.support.annotation.NonNull;

import com.android.anmol.feeds_cleanarch.feeds.viewholders.FeedStatusType;
import com.android.anmol.feeds_cleanarch.utility.Utils;

/**
 * Created by anmolsehgal on 27-03-2018.
 * <p>
 * Implementation to load Feeds from Data source.
 */
public class FeedsRepository implements FeedsDataSource {

    private static FeedsRepository INSTANCE = null;

    private final FeedsDataSource mFeedsRemoteDataSource;

    private ResFeed[] mCachedFeeds;

    // Prevent direct instantiation.
    private FeedsRepository(@NonNull FeedsDataSource feedsRemoteDataSource) {
        mFeedsRemoteDataSource = Utils.checkNotNull(feedsRemoteDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param feedsRemoteDataSource the backend data source
     * @return the {@link FeedsRepository} instance
     */
    public static FeedsRepository getInstance(FeedsDataSource feedsRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new FeedsRepository(feedsRemoteDataSource);
        }
        return INSTANCE;
    }

    /**
     * Gets Feeds from cache, local data source or remote data source, whichever is
     * available first.
     * <p>
     * Note: {@link FetchFeedsCallback#onDataNotAvailable()} is fired if all data sources fail to
     * get the data.
     */
    @Override
    public void getFeeds(@NonNull FetchFeedsCallback callback, boolean forceUpdate) {
        Utils.checkNotNull(callback);

        if (mCachedFeeds != null && !forceUpdate) {
            callback.onFeedsFetched(mCachedFeeds);
        } else {
            getFeedsFromRemoteDataSource(callback);
        }
    }

    @Override
    public void configureLikeStatus(final int feedId, @FeedStatusType int oldLikeStatus, @NonNull final FeedLikeStatus callback) {

        mFeedsRemoteDataSource.configureLikeStatus(feedId, oldLikeStatus, new FeedLikeStatus() {

            @Override
            public void configureLikeStatus(@FeedStatusType final int newLikeStatus) {
                callback.configureLikeStatus(newLikeStatus);

                mCachedFeeds[feedId].setFeedStatus(newLikeStatus);
            }
        });
    }

    private void getFeedsFromRemoteDataSource(@NonNull final FetchFeedsCallback callback) {
        mFeedsRemoteDataSource.getFeeds(new FetchFeedsCallback() {

            @Override
            public void onFeedsFetched(ResFeed[] feeds) {
                for (int x = 0; x < feeds.length; x++) {
                    feeds[x].setId(x);
                }
                refreshFeeds(feeds);
                callback.onFeedsFetched(feeds);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        }, true);
    }

    private void refreshFeeds(ResFeed[] feeds) {
        mCachedFeeds = feeds;
    }
}