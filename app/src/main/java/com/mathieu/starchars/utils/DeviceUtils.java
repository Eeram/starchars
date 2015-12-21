package com.mathieu.starchars.utils;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Project :    Star Chars
 * Author :     Mathieu
 * Date :       21/12/2015
 */

public class DeviceUtils {

    public static boolean isTablet(Context context) {
        return isLargeScreen(context) || isXLargeScreen(context);
    }

    public static boolean isLargeScreen(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static boolean isXLargeScreen(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}
