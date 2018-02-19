package org.hep.afa.welcome;

import android.content.Context;
import android.content.SharedPreferences;


public class WelcomePage {
    private static final int NUMBER_OF_TIMES_TO_DISPLAY = 3;

    private static final String PREFS_NAME = "HEP_PREFERENCE";
    private static final String WELCOME_PAGE_DISPLAYED_COUNT = "WELCOME_PAGE_DISPLAYED_COUNT";

    public static boolean shouldDisplay(Context context) {
        SharedPreferences settings = getPreferences(context);

        return settings.getInt(WELCOME_PAGE_DISPLAYED_COUNT, 0) < NUMBER_OF_TIMES_TO_DISPLAY;
    };

    public static void incrementDisplayedCount(Context context) {
        SharedPreferences settings = getPreferences(context);

        int currentDisplayedCount = settings.getInt(WELCOME_PAGE_DISPLAYED_COUNT, 0);

        settings.edit().putInt(WELCOME_PAGE_DISPLAYED_COUNT, ++currentDisplayedCount).commit();
    }

    public static void disableFutureDisplays(Context context) {
        SharedPreferences settings = getPreferences(context);

        settings.edit().putInt(WELCOME_PAGE_DISPLAYED_COUNT, NUMBER_OF_TIMES_TO_DISPLAY).commit();
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
}
