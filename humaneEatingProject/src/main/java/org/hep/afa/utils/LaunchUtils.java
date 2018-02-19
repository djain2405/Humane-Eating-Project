package org.hep.afa.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;

import org.hep.afa.R;
import org.hep.afa.takeaction.ShoutOutDialogFragment;
import org.hep.afa.takeaction.SurveyDialogFragment;
import org.hep.afa.transform.BlurBuilder;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hep.afa.transform.BlurBuilder.blur;


/**
 * Created by heatherlaurin on 4/15/17.
 *
 * These utils are responsible for showing DialogFragment and therefore require FragmentActivity
 * as the hosting context.
 */

public class LaunchUtils {

    public static final String PREFS_NAME         = "HEP_PREFS";
    public static final String PREFS_LAUNCH_COUNT = "launch_count";
    public static final String PREFS_HAS_SHARED   = "has_shared";
    public static final String PREFS_HAS_SURVEYED = "has_surveyed";

    private static final int HAS_SHARED_MAX_COUNT   = 50;
    private static final int HAS_SURVEYED_MIN_COUNT = 10;
    private static final int HAS_SURVEYED_FACTOR    = 20;

    private static LaunchUtils launchUtils = new LaunchUtils();
    private ArrayList<Integer> shareCount = new ArrayList<>(Arrays.asList(3, 6, 15, HAS_SHARED_MAX_COUNT));

    public static LaunchUtils getInstance() {
        return launchUtils;
    }

    public void runLaunchChecks(FragmentActivity activity) {

        boolean hasShared = hasShared(activity);
        boolean hasSurveyed = hasSurveyed(activity);

        if (hasShared && hasSurveyed) {
            return;
        }

        int launchCount = getLaunchCount(activity);
        updateLaunchCount(activity, ++launchCount);

        if (!hasShared && launchCount <= HAS_SHARED_MAX_COUNT && shareCount.contains(launchCount)) {
            showShare(activity);
            return;
        }

        if (!hasSurveyed &&
            (launchCount == HAS_SURVEYED_MIN_COUNT || launchCount % HAS_SURVEYED_FACTOR == 0)) {
            showSurvey(activity);
            return;
        }


    }

    private static Bitmap blurredBackgroundBitmap;

    public static Bitmap getBackgroundBitmap(FragmentActivity activity) {
        if (blurredBackgroundBitmap == null) {
            blurredBackgroundBitmap = BlurBuilder.blur(activity.findViewById(android.R.id.content).getRootView());
        }
        return blurredBackgroundBitmap;
    }

    public static void setBlurredBackgroundBitmap(FragmentActivity activity, Bitmap bitmap) {
        if (bitmap == null) {
            blurredBackgroundBitmap = bitmap;
        }
        else {
            blurredBackgroundBitmap = BlurBuilder.blur(activity, bitmap);
        }
    }


    // any click on the share functionality results in marking this as 'shared'
    private void showShare(final FragmentActivity activity) {

        ShoutOutDialogFragment dialogFragment = new ShoutOutDialogFragment();
        dialogFragment.setShoutOutListener(new ShoutOutDialogFragment.ShoutOutListener() {
            @Override
            public void onFacebookSelected() {
                updateHasShared(activity, true);
            }

            @Override
            public void onTwitterSelected() {
                updateHasShared(activity, true);
            }
        });
        dialogFragment.show(activity.getSupportFragmentManager(), activity.getString(R.string.dialog_fragment_tag));
    }

    private void showSurvey(final FragmentActivity activity) {
        SurveyDialogFragment dialogFragment = new SurveyDialogFragment();
        dialogFragment.setSurveyListener(new SurveyDialogFragment.SurveyListener() {
            @Override
            public void onTakeSurveySelected() {
                updateHasSurveyed(activity, true);
            }
        });
        dialogFragment.show(activity.getSupportFragmentManager(), activity.getString(R.string.dialog_fragment_tag));
    }


    private int getLaunchCount(FragmentActivity activity) {
        SharedPreferences preferences =
                activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return preferences.getInt(PREFS_LAUNCH_COUNT, 0);
    }

    private void updateLaunchCount(Context context, int launchCount) {
        SharedPreferences preferences =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(PREFS_LAUNCH_COUNT, launchCount);
        editor.apply();
    }

    private boolean hasShared(Context context) {
        SharedPreferences preferences =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(PREFS_HAS_SHARED, false);
    }

    private void updateHasShared(Context context, boolean hasShared) {
        SharedPreferences preferences =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PREFS_HAS_SHARED, hasShared);
        editor.apply();
    }

    private boolean hasSurveyed(Context context) {
        SharedPreferences preferences =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(PREFS_HAS_SURVEYED, false);
    }

    private void updateHasSurveyed(Context context, boolean hasSurveyed) {
        SharedPreferences preferences =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PREFS_HAS_SURVEYED, hasSurveyed);
        editor.apply();
    }
}
