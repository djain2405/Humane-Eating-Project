package org.hep.afa.main;

import android.app.Application;
import android.content.Context;

import com.android.volley.VolleyLog;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.github.johnkil.print.PrintConfig;

import org.hep.afa.model.QueryBuilder;
import org.hep.afa.utils.google.places.PlaceSearchUtils;

public class HEPApplication extends Application {

    private static HEPApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the singletons so their instances are bound to the application process.
        app = this;
        PrintConfig.initDefault(getAssets(), "fonts/material-icon-font.ttf");
        //FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        VolleyLog.DEBUG = false;
        QueryBuilder.initialize();
        PlaceSearchUtils.initialize(getApplicationContext());
    }

    public static Context getMyAppContext() {
        return app.getApplicationContext();
    }

}