package com.android.anmol.feeds_cleanarch.feeds;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.anmol.feeds_cleanarch.base.usecase.UseCase;
import com.android.anmol.feeds_cleanarch.base.usecase.UseCaseHandler;
import com.android.anmol.feeds_cleanarch.feed_details.LikeButtonViewManager;
import com.android.anmol.feeds_cleanarch.feeds.fetch_feeds.GetFeeds;
import com.android.anmol.feeds_cleanarch.feeds.like_feed.LikeFeed;
import com.android.anmol.feeds_cleanarch.feeds.model.BaseFeedItemModel;
import com.android.anmol.feeds_cleanarch.feeds.model.DateHeaderModel;
import com.android.anmol.feeds_cleanarch.feeds.model.FeedModel;
import com.android.anmol.feeds_cleanarch.source.FeedsRepository;
import com.android.anmol.feeds_cleanarch.source.ResFeed;
import com.android.anmol.feeds_cleanarch.utility.DateUtils;
import com.android.anmol.feeds_cleanarch.utility.converter.FeedConverter;
import com.android.anmol.feeds_cleanarch.utility.Utils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by anmolsehgal on 27-03-2018.
 * <p>
 * Listens to user actions from the UI ({@link FeedsActivity}), retrieves the data and updates the
 * UI as required.
 */
public class FeedPresenter implements FeedsContract.FeedsPresenter {

    private UseCaseHandler mUseCaseHandler;
    private FeedsRepository mFeedsRepository;

    private GetFeeds mGetFeeds;
    private LikeFeed mLikeFeed;
    private FeedsContract.View mView;

    private final WeakReference<Context> mContext;

    FeedPresenter(@NonNull final Context context,
                  UseCaseHandler useCaseHandler,
                  FeedsRepository feedsRepository,
                  GetFeeds getFeeds,
                  LikeFeed likeFeed,
                  FeedsContract.View view) {

        mUseCaseHandler = useCaseHandler;
        mFeedsRepository = Utils.checkNotNull(feedsRepository, "feedsRepository can not be null");
        mGetFeeds = getFeeds;
        mLikeFeed = likeFeed;
        mView = Utils.checkNotNull(view, "view can not be null");

        mContext = new WeakReference<>(context);
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        fetchFeeds(false);
    }

    @Override
    public void fetchFeeds(boolean forceUpdate) {

        // Show loading dialog before the Network call is made.
        mView.setLoadingIndicator(true);

        GetFeeds.RequestValues requestValue = new GetFeeds.RequestValues(forceUpdate);

        mUseCaseHandler.execute(mGetFeeds, requestValue,
                new UseCase.UseCaseCallback<GetFeeds.ResponseValue>() {
                    @Override
                    public void onSuccess(GetFeeds.ResponseValue response) {
                        ResFeed[] feeds = response.getFeeds();
                        // The view may not be able to handle UI updates anymore
                        if (!mView.isActive()) {
                            return;
                        }
                        processFeeds(feeds);
                    }

                    @Override
                    public void onError() {
                        // The view may not be able to handle UI updates anymore
                        if (!mView.isActive()) {
                            return;
                        }
                        mView.showLoadingFeedsError(true);
                    }
                });
    }

    /**
     * Process feeds i.e. convert the Server response to the response which UI can render.
     *
     * @param feeds server response.
     */
    private void processFeeds(ResFeed[] feeds) {
        if (feeds == null || feeds.length == 0) {
            // Show error.
            mView.showLoadingFeedsError(true);
        } else {
            if (mContext.get() == null) {
                return;
            }

            // Convert Server Response to UI Model.
            List<FeedModel> feedsForUi = new FeedConverter(mContext.get()).convert(feeds);

            if (feedsForUi == null || feedsForUi.isEmpty()) {
                mView.showLoadingFeedsError(true);
                return;
            }

            // Sort the feeds by time.
            sortFeedsByTime(feedsForUi);

            mView.showFeeds(addHeaders(feedsForUi));
            mView.showLoadingFeedsError(false);
        }
    }

    /**
     * Add the Date headers such the all feeds with same Date are under the same Date header.
     *
     * @param feedsForUi Feeds without the Headers set.
     * @return Feeds with Headers set.
     */
    private List<BaseFeedItemModel> addHeaders(@NonNull List<FeedModel> feedsForUi) {
        List<BaseFeedItemModel> feedsWithHeaders = new ArrayList<>();
        long prevTime = -1;
        for (FeedModel feed : feedsForUi) {
            if (prevTime == -1 || prevTime != feed.getTime()) {
                feedsWithHeaders.add(new DateHeaderModel(DateUtils.getHeaderDate(feed.getTime())));
            }
            feedsWithHeaders.add(feed);
            prevTime = feed.getTime();
        }
        return feedsWithHeaders;
    }

    /**
     * Sort the feeds by time, such that latest Feed is at the bottom.
     *
     * @param feeds Feeds to sort.
     */
    private void sortFeedsByTime(List<FeedModel> feeds) {
        Collections.sort(feeds, new Comparator<FeedModel>() {
            @Override
            public int compare(FeedModel o1, FeedModel o2) {
                return (int) (o1.getTime() - o2.getTime());
            }
        });
    }

    @Override
    public void openFeedDetails(int requestedFeedPos) {
        if (requestedFeedPos < 0) {
            throw new IllegalArgumentException("Clicked Feed position can not be empty");
        }
        mView.showFeedDetailsUi(requestedFeedPos);
    }

    @Override
    public void configureLikeStatus(@NonNull final FeedModel feedModel, final int pos) {

        LikeFeed.RequestValues requestValue = new LikeFeed.RequestValues(feedModel.getId(), feedModel.getFeedStatus());

        mUseCaseHandler.execute(mLikeFeed, requestValue,
                new UseCase.UseCaseCallback<LikeFeed.ResponseValue>() {

                    @Override
                    public void onSuccess(LikeFeed.ResponseValue response) {
                        if (!mView.isActive()) {
                            return;
                        }
                        mView.updateView(pos, LikeButtonViewManager.getUpdatedFeed(feedModel, response.getNewLikeStatus()));
                    }

                    @Override
                    public void onError() {
                        // Do nothing...
                    }
                });
    }

    @Override
    public void refreshLikeStatus(FeedModel feedModel, int feedPos) {
        mView.updateView(feedPos, feedModel);
    }
}