package com.android.anmol.feeds_cleanarch.feed_details;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.anmol.feeds_cleanarch.Injection;
import com.android.anmol.feeds_cleanarch.R;
import com.android.anmol.feeds_cleanarch.base.BaseActivity;
import com.android.anmol.feeds_cleanarch.feeds.model.FeedModel;
import com.android.anmol.feeds_cleanarch.utility.DateUtils;
import com.android.anmol.feeds_cleanarch.utility.ImageLoadUtils;
import com.android.anmol.feeds_cleanarch.utility.ResUtils;
import com.android.anmol.feeds_cleanarch.utility.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by anmolsehgal on 27-03-2018.
 * <p>
 * To show Feeds on the UI.
 */
public class FeedDetailsActivity extends BaseActivity {

    private static final String FEED_EXTRAS = "FEED_EXTRAS";
    private static final String FEED_POS = "FEED_POS";
    private static final String FEED_MODEL_SENT = "FEED_MODEL_SENT";
    private static final String FEED_POS_SENT = "FEED_POS_SENT";

    @BindView(R.id.tv_feed_header)
    TextView mTvTimeHeader;

    @BindView(R.id.iv_feed_image)
    ImageView mIvFeedImage;

    @BindView(R.id.tv_feed_text)
    TextView mTvFeedText;

    @BindView(R.id.btn_like)
    Button mBtnLike;

    @BindView(R.id.tv_from_person)
    TextView mTvFromPerson;

    @BindView(R.id.tv_feed_desc)
    TextView mTvFeedDesc;

    @BindView(R.id.progress_bar_loading)
    ProgressBar mProgressBar;

    @BindView(R.id.tv_image_error)
    TextView mTvImageLoadError;

    private FeedModel mFeedToOpen;

    private FeedDetailsContract.FeedDetailsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_detail_layout);
        ButterKnife.bind(this);

        if (getIntent() != null && getIntent().hasExtra(FEED_EXTRAS)) {
            mFeedToOpen = getIntent().getParcelableExtra(FEED_EXTRAS);
        } else {
            finish();
        }

        setupUi();

        new FeedDetailsPresenter(
                Injection.provideFeedsRepository(),
                new ViewCallbackListener());
    }

    private void setupUi() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(mFeedToOpen.getTitle());
        }
        mTvTimeHeader.setText(DateUtils.getHeaderDate(mFeedToOpen.getTime()));

        mTvFeedText.setText(mFeedToOpen.getText());

        mTvFromPerson.setText(mFeedToOpen.getName());

        mTvFeedText.setVisibility(mFeedToOpen.isFeedTextVisible() ? View.VISIBLE : View.GONE);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                mFeedToOpen.getIvWidth(),
                mFeedToOpen.getIvHeight());
        params.setMarginEnd(mFeedToOpen.getIvMarginEnd());

        mIvFeedImage.setLayoutParams(params);

        ImageLoadUtils.loadRoundedImage(mFeedToOpen.getImageUrl(),
                mIvFeedImage,
                mProgressBar,
                mTvImageLoadError,
                mFeedToOpen.isFeedTextVisible());

        mIvFeedImage.setVisibility(mFeedToOpen.isIsImageVisible() ? View.VISIBLE : View.GONE);

        configureLikeButton();

        mTvFeedDesc.setText(mFeedToOpen.getDescription());

        mTvFeedDesc.setMovementMethod(new ScrollingMovementMethod());
    }

    private void configureLikeButton() {
        mBtnLike.setBackground(ResUtils.getDrawable(this, mFeedToOpen.getBtnLikeBackgroundResId()));
        mBtnLike.setTextColor(ResUtils.getColor(this, mFeedToOpen.getBtnLikeTextColorResId()));
        mBtnLike.setText(ResUtils.getString(this, mFeedToOpen.getFeedStatus()));
    }

    public static Intent createIntent(Context context, FeedModel feed, int feedPos) {
        Utils.checkNotNull(context, "Context cannot be null");

        Intent intent = new Intent(context, FeedDetailsActivity.class);
        intent.putExtra(FEED_EXTRAS, feed);
        intent.putExtra(FEED_POS, feedPos);
        return intent;
    }

    public static Bundle getAnimationBundle(@NonNull Context context,
                                            @Nullable ImageView feedImage,
                                            @Nullable TextView feedText,
                                            @Nullable Button btnLike,
                                            @Nullable TextView feedFrom) {
        if (feedImage == null ||
                feedText == null ||
                btnLike == null ||
                feedFrom == null) {
            return null;
        }

        Utils.checkNotNull(context, "Context cannot be null");

        Pair<View, String> p1 = Pair.create((View) feedImage, feedImage.getTransitionName());
        Pair<View, String> p2 = Pair.create((View) feedText, feedText.getTransitionName());
        Pair<View, String> p3 = Pair.create((View) btnLike, btnLike.getTransitionName());
        Pair<View, String> p4 = Pair.create((View) feedFrom, feedFrom.getTransitionName());
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation((Activity) context, p1, p2, p3, p4);
        return options.toBundle();
    }

    @OnClick(R.id.btn_like)
    public void onViewClicked(View v) {
        mPresenter.configureLikeStatus(mFeedToOpen);
    }

    public static FeedModel getFeedModel(Intent data) {
        return data.getParcelableExtra(FEED_MODEL_SENT);
    }

    public static int getFeedPos(Intent data) {
        return data.getIntExtra(FEED_POS_SENT, -1);
    }

    private class ViewCallbackListener implements FeedDetailsContract.View {

        @Override
        public void setPresenter(FeedDetailsContract.FeedDetailsPresenter presenter) {
            mPresenter = presenter;
        }

        @Override
        public void updateView(FeedModel newFeed) {
            mFeedToOpen = newFeed;
            configureLikeButton();
        }
    }

    @Override
    public void onBackPressed() {
        if (getIntent().hasExtra(FEED_POS) &&
                ((FeedModel) getIntent().getParcelableExtra(FEED_EXTRAS)).getFeedStatus() != mFeedToOpen.getFeedStatus()) {

            // Send Data only if like status git changed.
            Intent intent = new Intent();
            intent.putExtra(FEED_MODEL_SENT, mFeedToOpen);
            intent.putExtra(FEED_POS_SENT, getIntent().getIntExtra(FEED_POS, -1));
            setResult(RESULT_OK, intent);
        }
        super.onBackPressed();
    }
}
