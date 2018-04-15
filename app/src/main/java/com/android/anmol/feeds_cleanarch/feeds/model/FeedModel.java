package com.android.anmol.feeds_cleanarch.feeds.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;

import com.android.anmol.feeds_cleanarch.feeds.viewholders.FeedStatusType;

/**
 * Created by anmolsehgal on 27-03-2018.
 * <p>
 * Feed Model to be rendered on the UI.
 */
public class FeedModel implements Parcelable, BaseFeedItemModel {

    private int mId;
    private String mName;
    private String mText;
    private String mImageUrl;
    private String mTitle;
    private long mTime;
    private String mDescription;

    @FeedStatusType
    private int mFeedStatus;

    @DrawableRes
    private int mBtnLikeBackgroundResId;

    @ColorRes
    private int mBtnLikeTextColorResId;

    private boolean mIsFeedTextVisible;

    private boolean mIsImageVisible;

    @DimenRes
    private int mIvHeight;

    @DimenRes
    private int mIvWidth;

    @DimenRes
    private int mIvMargin;

    private FeedModel(Builder builder) {
        mId = builder.mId;
        mName = builder.mName;
        mText = builder.mText;
        mImageUrl = builder.mImageUrl;
        mTitle = builder.mTitle;
        mTime = builder.mTime;
        mDescription = builder.mDescription;
        mFeedStatus = builder.mFeedStatus;
        mBtnLikeBackgroundResId = builder.mBtnLikeBackgroundResId;
        mBtnLikeTextColorResId = builder.mBtnLikeTextColorResId;
        mIsFeedTextVisible = builder.mIsFeedTextVisible;
        mIsImageVisible = builder.mIsImageVisible;
        mIvHeight = builder.mIvHeight;
        mIvWidth = builder.mIvWidth;
        mIvMargin = builder.mIvMargin;
    }

    protected FeedModel(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mText = in.readString();
        mImageUrl = in.readString();
        mTitle = in.readString();
        mTime = in.readLong();
        mDescription = in.readString();
        mFeedStatus = in.readInt();
        mBtnLikeBackgroundResId = in.readInt();
        mBtnLikeTextColorResId = in.readInt();
        mIsFeedTextVisible = in.readByte() != 0;
        mIsImageVisible = in.readByte() != 0;
        mIvHeight = in.readInt();
        mIvWidth = in.readInt();
        mIvMargin = in.readInt();
    }

    public static final Creator<FeedModel> CREATOR = new Creator<FeedModel>() {
        @Override
        public FeedModel createFromParcel(Parcel in) {
            return new FeedModel(in);
        }

        @Override
        public FeedModel[] newArray(int size) {
            return new FeedModel[size];
        }
    };

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(final FeedModel copy) {
        Builder builder = new Builder();
        builder.mId = copy.mId;
        builder.mName = copy.mName;
        builder.mText = copy.mText;
        builder.mImageUrl = copy.mImageUrl;
        builder.mTitle = copy.mTitle;
        builder.mTime = copy.mTime;
        builder.mDescription = copy.mDescription;
        builder.mFeedStatus = copy.mFeedStatus;
        builder.mBtnLikeBackgroundResId = copy.mBtnLikeBackgroundResId;
        builder.mBtnLikeTextColorResId = copy.mBtnLikeTextColorResId;
        builder.mIsFeedTextVisible = copy.mIsFeedTextVisible;
        builder.mIsImageVisible = copy.mIsImageVisible;
        builder.mIvHeight = copy.mIvHeight;
        builder.mIvWidth = copy.mIvWidth;
        builder.mIvMargin = copy.mIvMargin;
        return builder;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeString(mText);
        dest.writeString(mImageUrl);
        dest.writeString(mTitle);
        dest.writeLong(mTime);
        dest.writeString(mDescription);
        dest.writeInt(mFeedStatus);
        dest.writeInt(mBtnLikeBackgroundResId);
        dest.writeInt(mBtnLikeTextColorResId);
        dest.writeByte((byte) (mIsFeedTextVisible ? 1 : 0));
        dest.writeByte((byte) (mIsImageVisible ? 1 : 0));
        dest.writeInt(mIvHeight);
        dest.writeInt(mIvWidth);
        dest.writeInt(mIvMargin);
    }


    public static final class Builder {
        private int mId;
        private String mName;
        private String mText;
        private String mImageUrl;
        private String mTitle;
        private long mTime;
        private String mDescription;
        private int mFeedStatus;
        private int mBtnLikeBackgroundResId;
        private int mBtnLikeTextColorResId;
        private boolean mIsFeedTextVisible;
        private boolean mIsImageVisible;
        private int mIvHeight;
        private int mIvWidth;
        private int mIvMargin;

        private Builder() {
        }

        public Builder withId(int val) {
            mId = val;
            return this;
        }

        public Builder withName(String val) {
            mName = val;
            return this;
        }

        public Builder withText(String val) {
            mText = val;
            return this;
        }

        public Builder withImageUrl(String val) {
            mImageUrl = val;
            return this;
        }

        public Builder withTitle(String val) {
            mTitle = val;
            return this;
        }

        public Builder withTime(long val) {
            mTime = val;
            return this;
        }

        public Builder withDescription(String val) {
            mDescription = val;
            return this;
        }

        public Builder withFeedStatus(int val) {
            mFeedStatus = val;
            return this;
        }

        public Builder withBtnLikeBackgroundResId(int val) {
            mBtnLikeBackgroundResId = val;
            return this;
        }

        public Builder withBtnLikeTextColorResId(int val) {
            mBtnLikeTextColorResId = val;
            return this;
        }

        public Builder withIsFeedTextVisible(boolean val) {
            mIsFeedTextVisible = val;
            return this;
        }

        public Builder withIsImageVisible(boolean val) {
            mIsImageVisible = val;
            return this;
        }

        public Builder withIvHeight(int val) {
            mIvHeight = val;
            return this;
        }

        public Builder withIvWidth(int val) {
            mIvWidth = val;
            return this;
        }

        public Builder withIvMargin(int val) {
            mIvMargin = val;
            return this;
        }

        public FeedModel build() {
            return new FeedModel(this);
        }
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getText() {
        return mText;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public long getTime() {
        return mTime;
    }

    public String getDescription() {
        return mDescription;
    }

    @Override
    public int getFeedType() {
        return FeedType.FEED;
    }

    public int getFeedStatus() {
        return mFeedStatus;
    }

    public int getBtnLikeBackgroundResId() {
        return mBtnLikeBackgroundResId;
    }

    public int getBtnLikeTextColorResId() {
        return mBtnLikeTextColorResId;
    }

    public boolean isFeedTextVisible() {
        return mIsFeedTextVisible;
    }

    public boolean isIsImageVisible() {
        return mIsImageVisible;
    }

    public int getIvHeight() {
        return mIvHeight;
    }

    public int getIvWidth() {
        return mIvWidth;
    }

    public int getIvMarginEnd() {
        return mIvMargin;
    }
}

