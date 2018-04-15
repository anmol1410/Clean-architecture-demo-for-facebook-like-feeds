package com.android.anmol.feeds_cleanarch.feeds.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by anmolsehgal on 27-03-2018.
 * <p>
 * Check for the Feed type i.e. It can be either date header or the Feed.
 */
@Retention(SOURCE)
@IntDef({FeedType.DATE, FeedType.FEED})
public @interface FeedType {
    int DATE = 1;

    int FEED = 2;
}