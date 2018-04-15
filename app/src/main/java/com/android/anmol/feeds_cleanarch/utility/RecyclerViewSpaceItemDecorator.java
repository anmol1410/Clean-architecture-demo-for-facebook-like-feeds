package com.android.anmol.feeds_cleanarch.utility;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by anmolsehgal on 28-03-2018.
 * <p>
 * Recycler View Decorator to show Spacing between 2 items.
 */
public class RecyclerViewSpaceItemDecorator extends RecyclerView.ItemDecoration {

    private final int mSpaceSize;

    public RecyclerViewSpaceItemDecorator(@NonNull final Context context,
                                          @DimenRes final int dimenRes) {
        mSpaceSize = ResUtils.getDimen(context, dimenRes);
    }

    @Override
    public void getItemOffsets(final Rect outRect
            , final View view
            , final RecyclerView parent
            , final RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top = mSpaceSize;
    }
}