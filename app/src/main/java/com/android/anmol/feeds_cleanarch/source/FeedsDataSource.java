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

import com.android.anmol.feeds_cleanarch.feeds.FeedStatusType;

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
