package com.arshana.raje.Constant;

import android.app.Application;

import com.facebook.ads.AudienceNetworkAds;

/**
 * Created by Darshana Chorat on 2020-06-01.
 */
public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();
        // Initialize the Audience Network SDK
        AudienceNetworkAds.initialize(this);
    }
}
