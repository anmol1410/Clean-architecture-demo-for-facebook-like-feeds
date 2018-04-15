package com.android.anmol.feeds_cleanarch.feeds.like_feed;

import com.android.anmol.feeds_cleanarch.base.usecase.UseCase;
import com.android.anmol.feeds_cleanarch.feeds.FeedStatusType;
import com.android.anmol.feeds_cleanarch.source.FeedsDataSource;

/**
 * Created by anmolsehgal on 27-03-2018.
 */
public class LikeFeed extends UseCase<LikeFeed.RequestValues, LikeFeed.ResponseValue> {

    private FeedsDataSource mFeedsRemoteDataSource;

    public LikeFeed(FeedsDataSource mFeedsRemoteDataSource) {
        this.mFeedsRemoteDataSource = mFeedsRemoteDataSource;
    }

    @Override
    protected void executeUseCase(LikeFeed.RequestValues requestValues) {

        mFeedsRemoteDataSource.configureLikeStatus(
                requestValues.getFeedId(),
                requestValues.getOldFeedStatus(),
                new FeedsDataSource.FeedLikeStatus() {

                    @Override
                    public void configureLikeStatus(@FeedStatusType final int newLikeStatus) {
                        LikeFeed.ResponseValue responseValue = new LikeFeed.ResponseValue(newLikeStatus);
                        getUseCaseCallback().onSuccess(responseValue);
                    }
                });
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private int mFeedId;

        @FeedStatusType
        private int mOldFeedStatus;

        public RequestValues(int feedId,
                             @FeedStatusType int oldFeedStatus) {
            mFeedId = feedId;
            mOldFeedStatus = oldFeedStatus;
        }

        public int getFeedId() {
            return mFeedId;
        }

        @FeedStatusType
        public int getOldFeedStatus() {
            return mOldFeedStatus;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private int mNewLikeStatus;

        public ResponseValue(@FeedStatusType final int newLikeStatus) {
            mNewLikeStatus = newLikeStatus;
        }

        public int getNewLikeStatus() {
            return mNewLikeStatus;
        }
    }
}