package com.android.anmol.feeds_cleanarch.feeds;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.anmol.feeds_cleanarch.Injection;
import com.android.anmol.feeds_cleanarch.R;
import com.android.anmol.feeds_cleanarch.base.BaseActivity;
import com.android.anmol.feeds_cleanarch.feed_details.FeedDetailsActivity;
import com.android.anmol.feeds_cleanarch.feeds.model.BaseFeedItemModel;
import com.android.anmol.feeds_cleanarch.feeds.model.FeedModel;
import com.android.anmol.feeds_cleanarch.feeds.viewholders.FeedHolder;
import com.android.anmol.feeds_cleanarch.utility.RecyclerViewSpaceItemDecorator;

import java.util.List;

import butterknife.BindView;

public class FeedsActivity extends BaseActivity {

    private static final int FEED_DETAILS_RES = 101;

    @BindView(R.id.rvFeedsList)
    RecyclerView mRvFeedsList;

    @BindView(R.id.swipe_to_refresh_view)
    SwipeRefreshLayout mSwipeToRefreshView;

    @BindView(R.id.pb_loader)
    ProgressBar mPbLoader;

    @BindView(R.id.tv_error)
    TextView mTvError;

    /**
     * Presenter to do various tasks.
     */
    private FeedsContract.FeedsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feeds_activity);

        setupUi();

        new FeedPresenter(this,
                Injection.provideUseCaseHandler(),
                Injection.provideFeedsRepository(),
                Injection.provideGetFeeds(),
                Injection.provideLikeFeed(),
                new ViewCallbackListener());

        mPresenter.fetchFeeds(true);
    }

    /**
     * Initialize the Ui components.
     */
    private void setupUi() {
        setupRecyclerView();
        mSwipeToRefreshView.setOnRefreshListener(new RefreshFeedsManger());
    }

    private void setupRecyclerView() {
        mRvFeedsList.setHasFixedSize(true);
        final LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRvFeedsList.setLayoutManager(layoutManager);
        mRvFeedsList.addItemDecoration(new RecyclerViewSpaceItemDecorator(this, R.dimen.rv_divider));
    }

    private class FeedClickCallback implements FeedClickListener {
        @Override
        public void onFeedClicked(int feedPos) {
            mPresenter.openFeedDetails(feedPos);
        }

        @Override
        public void onLikeButtonClicked(int feedPos) {
            mPresenter.configureLikeStatus(
                    (FeedModel) ((FeedAdapter) mRvFeedsList.getAdapter()).getItemAt(feedPos),
                    feedPos);
        }
    }

    /**
     * To listen to the View Callbacks and update the UI accordingly.
     */
    private class ViewCallbackListener implements FeedsContract.View {
        @Override
        public void setPresenter(FeedsContract.FeedsPresenter presenter) {
            mPresenter = presenter;
        }

        @Override
        public void setLoadingIndicator(boolean active) {
            mPbLoader.setVisibility(active ? View.VISIBLE : View.GONE);
        }

        @Override
        public void showFeeds(List<BaseFeedItemModel> feeds) {
            if (mSwipeToRefreshView.isRefreshing()) {
                mSwipeToRefreshView.setRefreshing(false);
            }
            mRvFeedsList.setAdapter(new FeedAdapter(feeds, new FeedClickCallback()));
        }

        @Override
        public void showFeedDetailsUi(int feedPos) {
            // To show Animated Transition from FeedsActivity to FeedDetailsActivity.

            RecyclerView.ViewHolder viewHolder = mRvFeedsList.findViewHolderForAdapterPosition(feedPos);
            ImageView image = null;
            TextView text = null;
            Button button = null;
            TextView fromPerson = null;

            if (viewHolder instanceof FeedHolder) {
                image = (ImageView) viewHolder.itemView.findViewById(R.id.iv_feed_image);
                text = (TextView) viewHolder.itemView.findViewById(R.id.tv_feed_text);
                fromPerson = (TextView) viewHolder.itemView.findViewById(R.id.tv_from_person);
                button = (Button) viewHolder.itemView.findViewById(R.id.btn_like);
            }
            startActivityForResult(FeedDetailsActivity.createIntent(FeedsActivity.this,
                    (FeedModel) ((FeedAdapter) mRvFeedsList.getAdapter()).getItemAt(feedPos), feedPos),
                    FEED_DETAILS_RES,
                    FeedDetailsActivity.getAnimationBundle(FeedsActivity.this,
                            image, text, button, fromPerson));
        }

        @Override
        public boolean isActive() {
            return FeedsActivity.isActive(FeedsActivity.this);
        }

        @Override
        public void showLoadingFeedsError(boolean showError) {
            mTvError.setVisibility(showError ? View.VISIBLE : View.GONE);
        }

        @Override
        public void updateView(int feedPositionToUpdate, FeedModel newFeed) {
            ((FeedAdapter) mRvFeedsList.getAdapter()).setItemAt(
                    feedPositionToUpdate,
                    newFeed);

            mRvFeedsList.getAdapter().notifyItemChanged(feedPositionToUpdate);
        }
    }

    /**
     * Refresh the feeds.
     * <p>
     * Swiping will get new Feeds from the server everytime.
     */
    private class RefreshFeedsManger implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            mPresenter.fetchFeeds(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FEED_DETAILS_RES:
                if (data == null) {
                    return;
                }
                mPresenter.refreshLikeStatus(
                        FeedDetailsActivity.getFeedModel(data),
                        FeedDetailsActivity.getFeedPos(data)
                );
                break;
        }
    }
}