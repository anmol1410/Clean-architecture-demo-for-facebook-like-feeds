package com.android.anmol.feeds_cleanarch.source;

import com.android.anmol.feeds_cleanarch.feeds.viewholders.FeedStatusType;
import com.google.gson.annotations.SerializedName;

/**
 * Created by anmolsehgal on 27-03-2018.
 * <p>
 * Response Model as returned from the Server(Or JSON file in this case).
 */
public class ResFeed {

    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("text")
    private String mText;

    @SerializedName("imageUrl")
    private String mImageUrl;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("time")
    private long mTime;

    @SerializedName("description")
    private String mDescription;

    @FeedStatusType
    private int mFeedStatus = FeedStatusType.LIKE;

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getText() {
        return mText;
    }

    public void setText(String mName) {
        this.mText = mName;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long mTime) {
        this.mTime = mTime;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    @FeedStatusType
    public int getFeedStatus() {
        return mFeedStatus;
    }

    public void setFeedStatus(int mFeedStatus) {
        this.mFeedStatus = mFeedStatus;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }
}