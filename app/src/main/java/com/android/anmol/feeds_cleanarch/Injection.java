/*
 * Copyright (C) 2015 The Android Open Source Project
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

package com.android.anmol.feeds_cleanarch;


import com.android.anmol.feeds_cleanarch.feeds.fetch_feeds.GetFeeds;
import com.android.anmol.feeds_cleanarch.feeds.like_feed.LikeFeed;
import com.android.anmol.feeds_cleanarch.base.usecase.UseCaseHandler;
import com.android.anmol.feeds_cleanarch.source.FeedsRepository;
import com.android.anmol.feeds_cleanarch.source.remote.FeedsRemoteDataSource;

/**
 * Created by anmolsehgal on 27-03-2018.
 * <p>
 * Injects various components throughout the application.
 */
public class Injection {

    /**
     * Provides the Feeds repository which handles the Network Calls, Databases etc anything.
     *
     * @return The Feeds Repository.
     */
    public static FeedsRepository provideFeedsRepository() {
        return FeedsRepository.getInstance(FeedsRemoteDataSource.getInstance());
    }

    public static GetFeeds provideGetFeeds() {
        return new GetFeeds(provideFeedsRepository());
    }

    public static UseCaseHandler provideUseCaseHandler() {
        return UseCaseHandler.getInstance();
    }

    public static LikeFeed provideLikeFeed() {
        return new LikeFeed(provideFeedsRepository());
    }
}