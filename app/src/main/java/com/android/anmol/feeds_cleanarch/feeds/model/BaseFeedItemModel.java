package com.android.anmol.feeds_cleanarch.feeds.model;

/**
 * Created by anmolsehgal on 28-03-2018.
 * <p>
 * Base Feed model to be rendered on the UI.
 */
public interface BaseFeedItemModel {

    /**
     * Get Feed type, either Date header or Feed type.
     *
     * @return FeedType
     */
    @FeedType
    int getFeedType();
}
