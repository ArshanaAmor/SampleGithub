package com.arshana.raje.data;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.RewardedVideoAd;

/**
 * Created by Darshana Chorat on 2020-05-31.
 */
public class InitViews {

    public static String TAG="fbAds";
    public static AdView initBanner(Context applicationContext, LinearLayout containter, String Placement) {
        AdView adView = new AdView(applicationContext, Placement, AdSize.BANNER_HEIGHT_50);
        containter.addView(adView);
        adView.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Ad loaded callback
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        });
        // Request an ad
        adView.loadAd();
        return adView;

    }

    public static void initNativeBanner(Context applicationContext, LinearLayout containter, String Placement) {

    }

    public static RewardedVideoAd initRewardedAd(final Context applicationContext, String Placement)
    {
        RewardedVideoAd rewardedVideoAd = new RewardedVideoAd(applicationContext, "YOUR_PLACEMENT_ID");

        rewardedVideoAd.loadAd();
        return rewardedVideoAd;
    }

}
