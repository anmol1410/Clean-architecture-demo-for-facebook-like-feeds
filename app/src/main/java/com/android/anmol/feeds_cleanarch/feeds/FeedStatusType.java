package com.android.anmol.feeds_cleanarch.feeds;

import android.support.annotation.IntDef;
import android.support.annotation.StringRes;

import com.android.anmol.feeds_cleanarch.R;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by anmolsehgal on 27-03-2018.
 * <p>
 * To check if the Feed Status can be only among "Like" or Ünlike".
 */
@Retention(SOURCE)
@IntDef({FeedStatusType.LIKE, FeedStatusType.UNLIKE})
public @interface FeedStatusType {
    @StringRes
    int LIKE = R.string.like;

    @StringRes
    int UNLIKE = R.string.unlike;
}
