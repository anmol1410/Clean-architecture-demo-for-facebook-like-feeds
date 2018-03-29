package com.android.anmol.feeds_cleanarch.utility;

import android.support.annotation.Nullable;

/**
 * Created by anmolsehgal on 28-03-2018.
 * <p>
 * Converter to convert One Model into Another.
 *
 * @param <S> Source Model.
 * @param <D> Destination Model.
 */
public interface ModelConverter<S, D> {

    @Nullable
    D convert(@Nullable S source);
}
