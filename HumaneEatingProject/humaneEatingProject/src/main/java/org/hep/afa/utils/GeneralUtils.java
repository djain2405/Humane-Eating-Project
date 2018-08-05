package org.hep.afa.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.hep.afa.main.HEPApplication;
import org.hep.afa.R;

import java.util.List;

/**
 * Created by heather on 10/4/16.
 */

public class GeneralUtils {

    /**
     * Determines status of network connectivity
     * @return  true if online, false otherwise
     */
    public static boolean isConnected() {
        ConnectivityManager connMgr =
            (ConnectivityManager) HEPApplication.getMyAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }

    public static boolean isIntentSafe(Intent intent) {
        PackageManager packageManager = HEPApplication.getMyAppContext().getPackageManager();
        List activities = packageManager.queryIntentActivities(intent,
            PackageManager.MATCH_DEFAULT_ONLY);
        return activities.size() > 0;
    }

    public static boolean isWatchList(Context context, String humaneStatus) {
        if (context == null || humaneStatus == null) {
            return false;
        }

        if (humaneStatus.equals(context.getString(R.string.status_watch_list))) {
            return true;
        }

        return false;
    }
}
