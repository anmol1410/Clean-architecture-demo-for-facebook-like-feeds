package com.android.anmol.feeds_cleanarch.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by anmolsehgal on 27-03-2018.
 * <p>
 * Utility methods for the Date.
 */
public class DateUtils {
    private static final String HEADER_DATE_FORMAT = "dd MMMM yyyy";

    /**
     * Get formatted Date to show on the Date header.
     *
     * @param timeInMillis Time in Millis.
     * @return Formatted Date.
     */
    static public String getHeaderDate(long timeInMillis) {
        return new SimpleDateFormat(HEADER_DATE_FORMAT, Locale.US).format(new Date(timeInMillis));
    }
}