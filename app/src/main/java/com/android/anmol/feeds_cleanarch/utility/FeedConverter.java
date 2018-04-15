package com.android.anmol.feeds_cleanarch.utility;

import android.content.Context;
import android.support.annotation.Nullable;

import com.android.anmol.feeds_cleanarch.feeds.model.FeedModel;
import com.android.anmol.feeds_cleanarch.source.ResFeed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anmolsehgal on 27-03-2018.
 * <p>
 * Converts List of Server Response to Feeds ready to be shown to UI.
 */
public class FeedConverter implements ModelConverter<ResFeed[], List<FeedModel>> {

    private Context mContext;

    public FeedConverter(Context context) {
        mContext = context;
    }

    @Nullable
    @Override
    public List<FeedModel> convert(@Nullable ResFeed[] source) {
        if (source == null) {
            return null;
        }
        List<FeedModel> list = new ArrayList<>();
        final FeedResToFeedUiConverter modelConverter
                = new FeedResToFeedUiConverter(mContext);
        for (ResFeed resFeed : source) {
            list.add(modelConverter.convert(resFeed));
        }
        return list;
    }
}