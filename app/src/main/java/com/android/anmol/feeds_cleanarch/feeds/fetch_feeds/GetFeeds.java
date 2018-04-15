package com.android.anmol.feeds_cleanarch.feeds.fetch_feeds;

import android.support.annotation.NonNull;

import com.android.anmol.feeds_cleanarch.base.usecase.UseCase;
import com.android.anmol.feeds_cleanarch.source.FeedsDataSource;
import com.android.anmol.feeds_cleanarch.source.ResFeed;
import com.android.anmol.feeds_cleanarch.utility.Utils;

/**
 * Created by anmolsehgal on 29-03-2018.
 */
public class GetFeeds extends UseCase<GetFeeds.RequestValues, GetFeeds.ResponseValue> {

    private FeedsDataSource mFeedsRemoteDataSource;

    public GetFeeds(FeedsDataSource mFeedsRemoteDataSource) {
        this.mFeedsRemoteDataSource = mFeedsRemoteDataSource;
    }

    @Override
    protected void executeUseCase(GetFeeds.RequestValues requestValues) {

        mFeedsRemoteDataSource.getFeeds(new FeedsDataSource.FetchFeedsCallback() {

            @Override
            public void onFeedsFetched(ResFeed[] feeds) {
                for (int x = 0; x < feeds.length; x++) {
                    feeds[x].setId(x);
                }

                GetFeeds.ResponseValue responseValue = new GetFeeds.ResponseValue(feeds);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        }, requestValues.isForceUpdate());

    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final boolean mForceUpdate;

        public RequestValues(boolean forceUpdate) {
            mForceUpdate = forceUpdate;
        }

        boolean isForceUpdate() {
            return mForceUpdate;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final ResFeed[] mFeeds;

        ResponseValue(@NonNull ResFeed[] tasks) {
            mFeeds = Utils.checkNotNull(tasks, "Feeds cannot be null!");
        }

        public ResFeed[] getFeeds() {
            return mFeeds;
        }
    }

}
