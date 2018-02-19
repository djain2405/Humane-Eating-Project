package org.hep.afa.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.hep.afa.main.HEPApplication;

/**
 * Created by heather on 10/4/16.
 */

public class NetworkUtils {

    public static boolean isConnected() {
        ConnectivityManager connMgr =
            (ConnectivityManager) HEPApplication.getMyAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }
}
