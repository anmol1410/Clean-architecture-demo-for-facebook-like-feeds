package com.android.anmol.feeds_cleanarch.feeds.model;

/**
 * Created by anmolsehgal on 28-03-2018.
 * <p>
 * Header Model for Date header.
 */
public class DateHeaderModel implements BaseFeedItemModel {

    private String mDate;

    public DateHeaderModel(String date) {
        this.mDate = date;
    }

    public String getDate() {
        return mDate;
    }

    @Override
    public int getFeedType() {
        return FeedType.DATE;
    }
}


