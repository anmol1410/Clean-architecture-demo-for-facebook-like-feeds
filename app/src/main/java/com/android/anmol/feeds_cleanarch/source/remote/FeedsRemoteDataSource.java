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

package com.android.anmol.feeds_cleanarch.source.remote;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.android.anmol.feeds_cleanarch.feeds.FeedStatusType;
import com.android.anmol.feeds_cleanarch.source.FeedsDataSource;
import com.android.anmol.feeds_cleanarch.source.ResFeed;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by anmolsehgal on 27-03-2018.
 * <p>
 * Implementation of the data source that adds a latency simulating network.
 */
public class FeedsRemoteDataSource implements FeedsDataSource {

    private static FeedsRemoteDataSource INSTANCE;

    public static FeedsRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FeedsRemoteDataSource();
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private FeedsRemoteDataSource() {

    }

    @Override
    public void getFeeds(final @NonNull FetchFeedsCallback callback, boolean forceUpdate) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                try {
                    InputStream raw = this.getClass().getClassLoader().getResourceAsStream("res/raw/json_response");
//                    InputStream raw = mContext.getResources().openRawResource(R.raw.json_response);
                    Reader rd = new BufferedReader(new InputStreamReader(raw));
                    Gson gson = new Gson();
                    callback.onFeedsFetched(gson.fromJson(rd, ResFeed[].class));

                } catch (JsonSyntaxException | JsonIOException e) {
                    callback.onDataNotAvailable();
                }
            }
        });
    }

    @Override
    public void configureLikeStatus(int feedId, @FeedStatusType final int oldLikeStatus, @NonNull final FeedLikeStatus callback) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                @FeedStatusType int newLikeStatus = FeedStatusType.LIKE;

                switch (oldLikeStatus) {
                    case FeedStatusType.LIKE:
                        newLikeStatus = FeedStatusType.UNLIKE;
                        break;
                    case FeedStatusType.UNLIKE:
                        break;
                }

                callback.configureLikeStatus(newLikeStatus);
            }
        });
    }
}
