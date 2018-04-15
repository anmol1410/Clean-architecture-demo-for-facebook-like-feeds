package com.android.anmol.feeds_cleanarch;

import com.android.anmol.feeds_cleanarch.base.usecase.UseCaseHandler;
import com.android.anmol.feeds_cleanarch.feeds.fetch_feeds.GetFeeds;
import com.android.anmol.feeds_cleanarch.feeds.like_feed.LikeFeed;
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

    public static UseCaseHandler provideUseCaseHandler() {
        return UseCaseHandler.getInstance();
    }

    public static GetFeeds provideGetFeeds() {
        return new GetFeeds(provideFeedsRepository());
    }

    public static LikeFeed provideLikeFeed() {
        return new LikeFeed(provideFeedsRepository());
    }
}