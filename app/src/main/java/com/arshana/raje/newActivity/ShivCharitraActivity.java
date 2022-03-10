package com.arshana.raje.newActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.arshana.raje.Adapter.IndexAdapter;
import com.arshana.raje.Constant.Constant;
import com.arshana.raje.R;
import com.arshana.raje.sharedPref.SharedPref;
import com.arshana.raje.Fragment.BookPageIntroFragment;
import com.elconfidencial.bubbleshowcase.BubbleShowCase;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseSequence;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ShivCharitraActivity extends AppCompatActivity {

    RecyclerView rec_index;
    String[] id, content, titles;
    AlertDialog.Builder builderSingle;
    DrawerLayout drawer_layout;
    int current_Speak_position = 0;
    TextToSpeech tts;
    String toSpeak;
    HashMap<String, String> ttsParams;
    InterstitialAd interstitialAd;
    private final String TAG = HomePageActivity.class.getSimpleName();
    private NativeBannerAd nativeBannerAd;
    private NativeAdLayout nativeAdLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);
        interstitialAd = new InterstitialAd(this, com.arshana.raje.data.Constant.SHIVCHARITRA_INT);
        interstitialAd.loadAd();
        nativeBannerAd = new NativeBannerAd(this, com.arshana.raje.data.Constant.SHIVCHARITRA_NATIVE_BANNER);
        nativeBannerAd.setAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
            }

            @Override
            public void onError(Ad ad, AdError adError) {
            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (nativeBannerAd == null || !nativeBannerAd.isAdLoaded()) {
                    return;
                }
                // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad.
                if (nativeBannerAd.isAdInvalidated()) {
                    return;
                }
                inflateAd(nativeBannerAd); //

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });
        // load the ad
        nativeBannerAd.loadAd();
        id = new String[]{
                getResources().getString(R.string.episode0_id),
                getResources().getString(R.string.episode1_id),
                getResources().getString(R.string.episode2_id),
                getResources().getString(R.string.episode3_id),
                getResources().getString(R.string.episode4_id),
                getResources().getString(R.string.episode5_id),
                getResources().getString(R.string.episode6_id),
                getResources().getString(R.string.episode7_id),
                getResources().getString(R.string.episode8_id),
                getResources().getString(R.string.episode9_id),
                getResources().getString(R.string.episode10_id),
                getResources().getString(R.string.episode11_id),
                getResources().getString(R.string.episode12_id),
                getResources().getString(R.string.episode13_id),
                getResources().getString(R.string.episode14_id),
                getResources().getString(R.string.episode15_id),
                getResources().getString(R.string.episode16_id),
                getResources().getString(R.string.episode17_id),
                getResources().getString(R.string.episode18_id),
                getResources().getString(R.string.episode19_id),
                getResources().getString(R.string.episode20_id),
                getResources().getString(R.string.episode21_id),
                getResources().getString(R.string.episode22_id),
                getResources().getString(R.string.episode23_id),
                getResources().getString(R.string.episode24_id),
                getResources().getString(R.string.episode25_id),
                getResources().getString(R.string.episode26_id),
                getResources().getString(R.string.episode27_id),
                getResources().getString(R.string.episode28_id),
                getResources().getString(R.string.episode29_id),
                getResources().getString(R.string.episode30_id),
                getResources().getString(R.string.episode31_id),
                getResources().getString(R.string.episode32_id),
                getResources().getString(R.string.episode33_id),
                getResources().getString(R.string.episode34_id),
                getResources().getString(R.string.episode35_id),
                getResources().getString(R.string.episode36_id),
                getResources().getString(R.string.episode37_id),
                getResources().getString(R.string.episode38_id),
                getResources().getString(R.string.episode39_id),
                getResources().getString(R.string.episode40_id),
                getResources().getString(R.string.episode41_id),
                getResources().getString(R.string.episode42_id),
                getResources().getString(R.string.episode43_id),
                getResources().getString(R.string.episode44_id),
                getResources().getString(R.string.episode45_id),
                getResources().getString(R.string.episode46_id),
                getResources().getString(R.string.episode47_id),
                getResources().getString(R.string.episode48_id),
                getResources().getString(R.string.episode49_id),
                getResources().getString(R.string.episode49_id),
                getResources().getString(R.string.episode50_id),
                getResources().getString(R.string.episode51_id),
                getResources().getString(R.string.episode52_id),
                getResources().getString(R.string.episode53_id),
                getResources().getString(R.string.episode54_id),
                getResources().getString(R.string.episode55_id),
                getResources().getString(R.string.episode56_id),
                getResources().getString(R.string.episode57_id),
                getResources().getString(R.string.episode58_id),
                getResources().getString(R.string.episode59_id),
                getResources().getString(R.string.episode60_id),
                getResources().getString(R.string.episode61_id),
                getResources().getString(R.string.episode62_id),
                getResources().getString(R.string.episode63_id),
                getResources().getString(R.string.episode64_id),
                getResources().getString(R.string.episode65_id),
                getResources().getString(R.string.episode66_id),
                getResources().getString(R.string.episode67_id),
                getResources().getString(R.string.episode68_id),
                getResources().getString(R.string.episode69_id),
                getResources().getString(R.string.episode70_id),
                getResources().getString(R.string.episode71_id),
                getResources().getString(R.string.episode72_id),
                getResources().getString(R.string.episode73_id),
                getResources().getString(R.string.episode74_id),
                getResources().getString(R.string.episode75_id),
                getResources().getString(R.string.episode76_id),
                getResources().getString(R.string.episode77_id),
                getResources().getString(R.string.episode78_id),
                getResources().getString(R.string.episode79_id),
                getResources().getString(R.string.episode80_id),
                getResources().getString(R.string.episode81_id),
                getResources().getString(R.string.episode82_id),
                getResources().getString(R.string.episode83_id),
                getResources().getString(R.string.episode84_id),
                getResources().getString(R.string.episode85_id),
                getResources().getString(R.string.episode86_id),
                getResources().getString(R.string.episode87_id),
                getResources().getString(R.string.episode88_id),
                getResources().getString(R.string.episode89_id),
                getResources().getString(R.string.episode90_id),
                getResources().getString(R.string.episode91_id),
                getResources().getString(R.string.episode92_id),
                getResources().getString(R.string.episode93_id),
                getResources().getString(R.string.episode94_id),
                getResources().getString(R.string.episode95_id),
                getResources().getString(R.string.episode96_id),
                getResources().getString(R.string.episode97_id),
                getResources().getString(R.string.episode98_id),
                getResources().getString(R.string.episode99_id),
                getResources().getString(R.string.episode100_id),
                getResources().getString(R.string.episode101_id),
                getResources().getString(R.string.episode102_id),
                getResources().getString(R.string.episode103_id),
                getResources().getString(R.string.episode104_id),
                getResources().getString(R.string.episode105_id),
                getResources().getString(R.string.episode106_id),
                getResources().getString(R.string.episode107_id),
                getResources().getString(R.string.episode108_id),
                getResources().getString(R.string.episode109_id),
                getResources().getString(R.string.episode110_id),
                getResources().getString(R.string.episode111_id),
                getResources().getString(R.string.episode112_id),
                getResources().getString(R.string.episode113_id),
                getResources().getString(R.string.episode114_id),
                getResources().getString(R.string.episode115_id),
                getResources().getString(R.string.episode116_id),
                getResources().getString(R.string.episode117_id),
                getResources().getString(R.string.episode118_id),
                getResources().getString(R.string.episode119_id),
                getResources().getString(R.string.episode120_id),
                getResources().getString(R.string.episode121_id),
                getResources().getString(R.string.episode122_id),
                getResources().getString(R.string.episode123_id),
                getResources().getString(R.string.episode124_id),
                getResources().getString(R.string.episode125_id),
                getResources().getString(R.string.episode126_id),
                getResources().getString(R.string.episode127_id),
                getResources().getString(R.string.episode128_id),
                getResources().getString(R.string.episode129_id),
                getResources().getString(R.string.episode130_id),
                getResources().getString(R.string.episode131_id),
                getResources().getString(R.string.episode132_id),
                getResources().getString(R.string.episode133_id),
                getResources().getString(R.string.episode134_id),
                getResources().getString(R.string.episode135_id),
                getResources().getString(R.string.episode136_id),
                getResources().getString(R.string.episode137_id),
                getResources().getString(R.string.episode138_id),
                getResources().getString(R.string.episode139_id),
                getResources().getString(R.string.episode140_id),
                getResources().getString(R.string.episode141_id),
                getResources().getString(R.string.episode142_id),
                getResources().getString(R.string.episode143_id),
                getResources().getString(R.string.episode144_id),
                getResources().getString(R.string.episode145_id),
                getResources().getString(R.string.episode146_id),
                getResources().getString(R.string.episode147_id),
                getResources().getString(R.string.episode148_id),
                getResources().getString(R.string.episode149_id),
                getResources().getString(R.string.episode150_id)


        };

        titles = new String[]{
                getResources().getString(R.string.episode0_title),
                getResources().getString(R.string.episode1_title),
                getResources().getString(R.string.episode2_title),
                getResources().getString(R.string.episode3_title),
                getResources().getString(R.string.episode4_title),
                getResources().getString(R.string.episode5_title),
                getResources().getString(R.string.episode6_title),
                getResources().getString(R.string.episode7_title),
                getResources().getString(R.string.episode8_title),
                getResources().getString(R.string.episode9_title),
                getResources().getString(R.string.episode10_title),
                getResources().getString(R.string.episode11_title),
                getResources().getString(R.string.episode12_title),
                getResources().getString(R.string.episode13_title),
                getResources().getString(R.string.episode14_title),
                getResources().getString(R.string.episode15_title),
                getResources().getString(R.string.episode16_title),
                getResources().getString(R.string.episode17_title),
                getResources().getString(R.string.episode18_title),
                getResources().getString(R.string.episode19_title),
                getResources().getString(R.string.episode20_title),
                getResources().getString(R.string.episode21_title),
                getResources().getString(R.string.episode22_title),
                getResources().getString(R.string.episode23_title),
                getResources().getString(R.string.episode24_title),
                getResources().getString(R.string.episode25_title),
                getResources().getString(R.string.episode26_title),
                getResources().getString(R.string.episode27_title),
                getResources().getString(R.string.episode28_title),
                getResources().getString(R.string.episode29_title),
                getResources().getString(R.string.episode30_title),
                getResources().getString(R.string.episode31_title),
                getResources().getString(R.string.episode32_title),
                getResources().getString(R.string.episode33_title),
                getResources().getString(R.string.episode34_title),
                getResources().getString(R.string.episode35_title),
                getResources().getString(R.string.episode36_title),
                getResources().getString(R.string.episode37_title),
                getResources().getString(R.string.episode38_title),
                getResources().getString(R.string.episode39_title),
                getResources().getString(R.string.episode40_title),
                getResources().getString(R.string.episode41_title),
                getResources().getString(R.string.episode42_title),
                getResources().getString(R.string.episode43_title),
                getResources().getString(R.string.episode44_title),
                getResources().getString(R.string.episode45_title),
                getResources().getString(R.string.episode46_title),
                getResources().getString(R.string.episode47_title),
                getResources().getString(R.string.episode48_title),
                getResources().getString(R.string.episode49_title),
                getResources().getString(R.string.episode49_title),
                getResources().getString(R.string.episode50_title),
                getResources().getString(R.string.episode51_title),
                getResources().getString(R.string.episode52_title),
                getResources().getString(R.string.episode53_title),
                getResources().getString(R.string.episode54_title),
                getResources().getString(R.string.episode55_title),
                getResources().getString(R.string.episode56_title),
                getResources().getString(R.string.episode57_title),
                getResources().getString(R.string.episode58_title),
                getResources().getString(R.string.episode59_title),
                getResources().getString(R.string.episode60_title),
                getResources().getString(R.string.episode61_title),
                getResources().getString(R.string.episode62_title),
                getResources().getString(R.string.episode63_title),
                getResources().getString(R.string.episode64_title),
                getResources().getString(R.string.episode65_title),
                getResources().getString(R.string.episode66_title),
                getResources().getString(R.string.episode67_title),
                getResources().getString(R.string.episode68_title),
                getResources().getString(R.string.episode69_title),
                getResources().getString(R.string.episode70_title),
                getResources().getString(R.string.episode71_title),
                getResources().getString(R.string.episode72_title),
                getResources().getString(R.string.episode73_title),
                getResources().getString(R.string.episode74_title),
                getResources().getString(R.string.episode75_title),
                getResources().getString(R.string.episode76_title),
                getResources().getString(R.string.episode77_title),
                getResources().getString(R.string.episode78_title),
                getResources().getString(R.string.episode79_title),
                getResources().getString(R.string.episode80_title),
                getResources().getString(R.string.episode81_title),
                getResources().getString(R.string.episode82_title),
                getResources().getString(R.string.episode83_title),
                getResources().getString(R.string.episode84_title),
                getResources().getString(R.string.episode85_title),
                getResources().getString(R.string.episode86_title),
                getResources().getString(R.string.episode87_title),
                getResources().getString(R.string.episode88_title),
                getResources().getString(R.string.episode89_title),
                getResources().getString(R.string.episode90_title),
                getResources().getString(R.string.episode91_title),
                getResources().getString(R.string.episode92_title),
                getResources().getString(R.string.episode93_title),
                getResources().getString(R.string.episode94_title),
                getResources().getString(R.string.episode95_title),
                getResources().getString(R.string.episode96_title),
                getResources().getString(R.string.episode97_title),
                getResources().getString(R.string.episode98_title),
                getResources().getString(R.string.episode99_title),
                getResources().getString(R.string.episode100_title),
                getResources().getString(R.string.episode101_title),
                getResources().getString(R.string.episode102_title),
                getResources().getString(R.string.episode103_title),
                getResources().getString(R.string.episode104_title),
                getResources().getString(R.string.episode105_title),
                getResources().getString(R.string.episode106_title),
                getResources().getString(R.string.episode107_title),
                getResources().getString(R.string.episode108_title),
                getResources().getString(R.string.episode109_title),
                getResources().getString(R.string.episode110_title),
                getResources().getString(R.string.episode111_title),
                getResources().getString(R.string.episode112_title),
                getResources().getString(R.string.episode113_title),
                getResources().getString(R.string.episode114_title),
                getResources().getString(R.string.episode115_title),
                getResources().getString(R.string.episode116_title),
                getResources().getString(R.string.episode117_title),
                getResources().getString(R.string.episode118_title),
                getResources().getString(R.string.episode119_title),
                getResources().getString(R.string.episode120_title),
                getResources().getString(R.string.episode121_title),
                getResources().getString(R.string.episode122_title),
                getResources().getString(R.string.episode123_title),
                getResources().getString(R.string.episode124_title),
                getResources().getString(R.string.episode125_title),
                getResources().getString(R.string.episode126_title),
                getResources().getString(R.string.episode127_title),
                getResources().getString(R.string.episode128_title),
                getResources().getString(R.string.episode129_title),
                getResources().getString(R.string.episode130_title),
                getResources().getString(R.string.episode131_title),
                getResources().getString(R.string.episode132_title),
                getResources().getString(R.string.episode133_title),
                getResources().getString(R.string.episode134_title),
                getResources().getString(R.string.episode135_title),
                getResources().getString(R.string.episode136_title),
                getResources().getString(R.string.episode137_title),
                getResources().getString(R.string.episode138_title),
                getResources().getString(R.string.episode139_title),
                getResources().getString(R.string.episode140_title),
                getResources().getString(R.string.episode141_title),
                getResources().getString(R.string.episode142_title),
                getResources().getString(R.string.episode143_title),
                getResources().getString(R.string.episode144_title),
                getResources().getString(R.string.episode145_title),
                getResources().getString(R.string.episode146_title),
                getResources().getString(R.string.episode147_title),
                getResources().getString(R.string.episode148_title),
                getResources().getString(R.string.episode149_title),
                getResources().getString(R.string.episode150_title)

        };

        content = new String[]{
                getResources().getString(R.string.episode0_content),
                getResources().getString(R.string.episode1_content),
                getResources().getString(R.string.episode2_content),
                getResources().getString(R.string.episode3_content),
                getResources().getString(R.string.episode4_content),
                getResources().getString(R.string.episode5_content),
                getResources().getString(R.string.episode6_content),
                getResources().getString(R.string.episode7_content),
                getResources().getString(R.string.episode8_content),
                getResources().getString(R.string.episode9_content),
                getResources().getString(R.string.episode10_content),
                getResources().getString(R.string.episode11_content),
                getResources().getString(R.string.episode12_content),
                getResources().getString(R.string.episode13_content),
                getResources().getString(R.string.episode14_content),
                getResources().getString(R.string.episode15_content),
                getResources().getString(R.string.episode16_content),
                getResources().getString(R.string.episode17_content),
                getResources().getString(R.string.episode18_content),
                getResources().getString(R.string.episode19_content),
                getResources().getString(R.string.episode20_content),
                getResources().getString(R.string.episode21_content),
                getResources().getString(R.string.episode22_content),
                getResources().getString(R.string.episode23_content),
                getResources().getString(R.string.episode24_content),
                getResources().getString(R.string.episode25_content),
                getResources().getString(R.string.episode26_content),
                getResources().getString(R.string.episode27_content),
                getResources().getString(R.string.episode28_content),
                getResources().getString(R.string.episode29_content),
                getResources().getString(R.string.episode30_content),
                getResources().getString(R.string.episode31_content),
                getResources().getString(R.string.episode32_content),
                getResources().getString(R.string.episode33_content),
                getResources().getString(R.string.episode34_content),
                getResources().getString(R.string.episode35_content),
                getResources().getString(R.string.episode36_content),
                getResources().getString(R.string.episode37_content),
                getResources().getString(R.string.episode38_content),
                getResources().getString(R.string.episode39_content),
                getResources().getString(R.string.episode40_content),
                getResources().getString(R.string.episode41_content),
                getResources().getString(R.string.episode42_content),
                getResources().getString(R.string.episode43_content),
                getResources().getString(R.string.episode44_content),
                getResources().getString(R.string.episode45_content),
                getResources().getString(R.string.episode46_content),
                getResources().getString(R.string.episode47_content),
                getResources().getString(R.string.episode48_content),
                getResources().getString(R.string.episode49_content),
                getResources().getString(R.string.episode49_content),
                getResources().getString(R.string.episode50_content),
                getResources().getString(R.string.episode51_content),
                getResources().getString(R.string.episode52_content),
                getResources().getString(R.string.episode53_content),
                getResources().getString(R.string.episode54_content),
                getResources().getString(R.string.episode55_content),
                getResources().getString(R.string.episode56_content),
                getResources().getString(R.string.episode57_content),
                getResources().getString(R.string.episode58_content),
                getResources().getString(R.string.episode59_content),
                getResources().getString(R.string.episode60_content),
                getResources().getString(R.string.episode61_content),
                getResources().getString(R.string.episode62_content),
                getResources().getString(R.string.episode63_content),
                getResources().getString(R.string.episode64_content),
                getResources().getString(R.string.episode65_content),
                getResources().getString(R.string.episode66_content),
                getResources().getString(R.string.episode67_content),
                getResources().getString(R.string.episode68_content),
                getResources().getString(R.string.episode69_content),
                getResources().getString(R.string.episode70_content),
                getResources().getString(R.string.episode71_content),
                getResources().getString(R.string.episode72_content),
                getResources().getString(R.string.episode73_content),
                getResources().getString(R.string.episode74_content),
                getResources().getString(R.string.episode75_content),
                getResources().getString(R.string.episode76_content),
                getResources().getString(R.string.episode77_content),
                getResources().getString(R.string.episode78_content),
                getResources().getString(R.string.episode79_content),
                getResources().getString(R.string.episode80_content),
                getResources().getString(R.string.episode81_content),
                getResources().getString(R.string.episode82_content),
                getResources().getString(R.string.episode83_content),
                getResources().getString(R.string.episode84_content),
                getResources().getString(R.string.episode85_content),
                getResources().getString(R.string.episode86_content),
                getResources().getString(R.string.episode87_content),
                getResources().getString(R.string.episode88_content),
                getResources().getString(R.string.episode89_content),
                getResources().getString(R.string.episode90_content),
                getResources().getString(R.string.episode91_content),
                getResources().getString(R.string.episode92_content),
                getResources().getString(R.string.episode93_content),
                getResources().getString(R.string.episode94_content),
                getResources().getString(R.string.episode95_content),
                getResources().getString(R.string.episode96_content),
                getResources().getString(R.string.episode97_content),
                getResources().getString(R.string.episode98_content),
                getResources().getString(R.string.episode99_content),
                getResources().getString(R.string.episode100_content),
                getResources().getString(R.string.episode101_content),
                getResources().getString(R.string.episode102_content),
                getResources().getString(R.string.episode103_content),
                getResources().getString(R.string.episode104_content),
                getResources().getString(R.string.episode105_content),
                getResources().getString(R.string.episode106_content),
                getResources().getString(R.string.episode107_content),
                getResources().getString(R.string.episode108_content),
                getResources().getString(R.string.episode109_content),
                getResources().getString(R.string.episode110_content),
                getResources().getString(R.string.episode111_content),
                getResources().getString(R.string.episode112_content),
                getResources().getString(R.string.episode113_content),
                getResources().getString(R.string.episode114_content),
                getResources().getString(R.string.episode115_content),
                getResources().getString(R.string.episode116_content),
                getResources().getString(R.string.episode117_content),
                getResources().getString(R.string.episode118_content),
                getResources().getString(R.string.episode119_content),
                getResources().getString(R.string.episode120_content),
                getResources().getString(R.string.episode121_content),
                getResources().getString(R.string.episode122_content),
                getResources().getString(R.string.episode123_content),
                getResources().getString(R.string.episode124_content),
                getResources().getString(R.string.episode125_content),
                getResources().getString(R.string.episode126_content),
                getResources().getString(R.string.episode127_content),
                getResources().getString(R.string.episode128_content),
                getResources().getString(R.string.episode129_content),
                getResources().getString(R.string.episode130_content),
                getResources().getString(R.string.episode131_content),
                getResources().getString(R.string.episode132_content),
                getResources().getString(R.string.episode133_content),
                getResources().getString(R.string.episode134_content),
                getResources().getString(R.string.episode135_content),
                getResources().getString(R.string.episode136_content),
                getResources().getString(R.string.episode137_content),
                getResources().getString(R.string.episode138_content),
                getResources().getString(R.string.episode139_content),
                getResources().getString(R.string.episode140_content),
                getResources().getString(R.string.episode141_content),
                getResources().getString(R.string.episode142_content),
                getResources().getString(R.string.episode143_content),
                getResources().getString(R.string.episode144_content),
                getResources().getString(R.string.episode145_content),
                getResources().getString(R.string.episode146_content),
                getResources().getString(R.string.episode147_content),
                getResources().getString(R.string.episode148_content),
                getResources().getString(R.string.episode149_content),
                getResources().getString(R.string.episode150_content)


        };
        String s =
                "";
        final ViewPager myViewPager = findViewById(R.id.myViewPager);
        drawer_layout = findViewById(R.id.drawer_layout);
        builderSingle = new AlertDialog.Builder(ShivCharitraActivity.this);
        builderSingle.setTitle("फॉन्ट आकार निवडा");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ShivCharitraActivity.this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("१४");
        arrayAdapter.add("१६");
        arrayAdapter.add("१८");
        arrayAdapter.add("२०");
        arrayAdapter.add("२२");

        builderSingle.setNegativeButton("रद्द करा\n", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        rec_index = findViewById(R.id.rec_index);
        RecyclerView.LayoutManager layoutManager = new
                LinearLayoutManager(getApplicationContext());
        rec_index.setLayoutManager(layoutManager);
        rec_index.setItemAnimator(new DefaultItemAnimator());
        IndexAdapter photoGalleryAdapter = new IndexAdapter(id, ShivCharitraActivity.this, titles, myViewPager, drawer_layout);
        rec_index.setAdapter(photoGalleryAdapter);
        final BookOnboardingPagerAdapter pagerAdapter = new BookOnboardingPagerAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(pagerAdapter);
//        myViewPager.setCurrentItem(100);
        myViewPager.setCurrentItem(SharedPref.getLastPage(this));
        myViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (tts != null) {
                    tts.shutdown();
                }
            }

            @Override
            public void onPageSelected(int position) {

                SharedPref.saveLastPage(ShivCharitraActivity.this, position);
                SharedPref.saveShivCharitraNotification(ShivCharitraActivity.this, titles[position]);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int size = 12;

                switch (which) {
                    case 0:
                        size = 14;
                        break;
                    case 1:
                        size = 16;
                        break;
                    case 2:
                        size = 18;
                        break;
                    case 3:
                        size = 20;
                        break;
                    case 4:
                        size = 22;
                        break;
                }
                SharedPref.saveFontSize(ShivCharitraActivity.this, size);
                myViewPager.setAdapter(pagerAdapter);
                myViewPager.setCurrentItem(SharedPref.getLastPage(ShivCharitraActivity.this));
                pagerAdapter.notifyDataSetChanged();
                dialog.dismiss();

            }
        });

        ImageView img_font_size = findViewById(R.id.img_font_size);
        ImageView img_audio = findViewById(R.id.img_audio);
        ImageView img_share = findViewById(R.id.img_share);
        ImageView img_back = findViewById(R.id.img_back);
        ImageView img_index = findViewById(R.id.img_index);
        img_index.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.openDrawer(GravityCompat.END);
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        img_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = myViewPager.getCurrentItem();
                final ArrayList<String> mylist = new ArrayList<String>();
                toSpeak = content[currentPosition];
                int characterNumber = 4000;
                if (toSpeak.length() > characterNumber) {
                    int maxlengthDivide = toSpeak.length() / characterNumber;

                    int maxlengthMod = toSpeak.length() % characterNumber;

                    if (maxlengthMod != 0) {
                        maxlengthDivide = maxlengthDivide + 1;
                    }
                    int startIndex = 0;
                    int endIndex = characterNumber;

                    for (int j = 0; j < maxlengthDivide; ) {
                        String trimString = toSpeak.substring(startIndex, endIndex);
                        mylist.add(trimString);
                        j = j + 1;
                        startIndex = endIndex + 1;
                        if (j == maxlengthDivide - 1) {
                            endIndex = maxlengthMod + endIndex;

                        } else {
                            int multiple = j + 1;
                            endIndex = characterNumber * multiple;
                        }
                    }
                    current_Speak_position = 0;
                    toSpeak = mylist.get(0);

                }


                tts = new TextToSpeech(ShivCharitraActivity.this, new TextToSpeech.OnInitListener() {

                    @Override
                    public void onInit(int status) {
                        Locale loc = new Locale("mr", "IN");

                        tts.setLanguage(loc);
                        ttsParams = new HashMap<String, String>();
                        ttsParams.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,
                                ShivCharitraActivity.this.getPackageName());
                        tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, ttsParams);
                        tts.setOnUtteranceCompletedListener(new TextToSpeech.OnUtteranceCompletedListener() {

                            @Override
                            public void onUtteranceCompleted(final String utteranceId) {
                                current_Speak_position++;
                                if (current_Speak_position < mylist.size()) {
                                    toSpeak = mylist.get(current_Speak_position);
                                    tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, ttsParams);
                                }

                            }
                        });

                    }
                });


            }
        });


        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = myViewPager.getCurrentItem();
                String shareContent = "*" + getResources().getString(R.string.shivcharitra) + "*" + "\n\n" + "*" + titles[currentPosition] + "*" + "\n\n" + content[currentPosition];
                Uri uriUrl = Uri.parse("whatsapp://send?text=" + Constant.PLAYSTORE_URL + shareContent + "");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                try {
                    startActivity(launchBrowser);

                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "Whatsapp is not installed on this device", Toast.LENGTH_SHORT).show();
                }
            }
        });

        img_font_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tts != null && tts.isSpeaking()) {
                    AlertDialog.Builder builderSingle1 = new AlertDialog.Builder(ShivCharitraActivity.this);
                    builderSingle1.setTitle("ऑडिओ चालू आपण आता फॉन्ट बदलू शकत नाही");
                    builderSingle1.setPositiveButton("ठीक आहे", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builderSingle1.show();
                } else {
                    builderSingle.show();

                }


            }
        });

        Drawable closeDrawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            closeDrawable = getResources().getDrawable(R.drawable.ic_close_black_24dp, getApplicationContext().getTheme());
        } else {
            closeDrawable = getResources().getDrawable(R.drawable.ic_close_black_24dp);
        }

        int color;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            color = getResources().getColor(R.color.colorPrimary, getApplicationContext().getTheme());

        } else {
            color = getResources().getColor(R.color.colorPrimary);
        }

        ImageView img_swipe_left = findViewById(R.id.img_swipe_left);
        ImageView img_swipe_right = findViewById(R.id.img_swipe_right);

        BubbleShowCaseBuilder builder_audio = new BubbleShowCaseBuilder(this) //Activity instance
                .title("ऑडिओ ऐका\n") //Any title for the bubble view
                .description("ऑडिओ ऐकण्यासाठी येथे क्लिक करा\n") //More detailed description
                .arrowPosition(BubbleShowCase.ArrowPosition.TOP) //You can force the position of the arrow to change the location of the bubble.
                .backgroundColor(color) //Bubble background color
                .textColor(Color.WHITE) //Bubble Text color
                .titleTextSize(17) //Title text size in SP (default value 16sp)
                .descriptionTextSize(15) //Subtitle text size in SP (default value 14sp)
                //                .image(imageDrawable) //Bubble main image
                .closeActionImage(closeDrawable) //Custom close action image
                .showOnce("7") //Id to show only once the BubbleShowCase
                .targetView(img_audio);

        BubbleShowCaseBuilder builder_font = new BubbleShowCaseBuilder(this) //Activity instance
                .title("अक्षराचा आकार\n") //Any title for the bubble view
                .description("फॉन्ट आकार बदलण्यासाठी येथे क्लिक करा\n") //More detailed description
                .arrowPosition(BubbleShowCase.ArrowPosition.TOP) //You can force the position of the arrow to change the location of the bubble.
                .backgroundColor(color) //Bubble background color
                .textColor(Color.WHITE) //Bubble Text color
                .titleTextSize(17) //Title text size in SP (default value 16sp)
                .descriptionTextSize(15) //Subtitle text size in SP (default value 14sp)
                //                .image(imageDrawable) //Bubble main image
                .closeActionImage(closeDrawable) //Custom close action image
                .showOnce("8") //Id to show only once the BubbleShowCase
                .targetView(img_font_size);

        BubbleShowCaseBuilder builder_index = new BubbleShowCaseBuilder(this) //Activity instance
                .title("अनुक्रमणिका\n") //Any title for the bubble view
                .description("पूर्ण अनुक्रमणिका मिळविण्यासाठी येथे क्लिक करा\n") //More detailed description
                .arrowPosition(BubbleShowCase.ArrowPosition.TOP) //You can force the position of the arrow to change the location of the bubble.
                .backgroundColor(color) //Bubble background color
                .textColor(Color.WHITE) //Bubble Text color
                .titleTextSize(17) //Title text size in SP (default value 16sp)
                .descriptionTextSize(15) //Subtitle text size in SP (default value 14sp)
                //                .image(imageDrawable) //Bubble main image
                .closeActionImage(closeDrawable) //Custom close action image
                .showOnce("9") //Id to show only once the BubbleShowCase
                .targetView(img_index);
        BubbleShowCaseBuilder builder_share_whatsapp = new BubbleShowCaseBuilder(this) //Activity instance
                .title("सोशल मीडियावर पाठवा\n") //Any title for the bubble view
                .description("शेअर करण्यासाठी येथे क्लिक करा\n") //More detailed description
                .arrowPosition(BubbleShowCase.ArrowPosition.TOP) //You can force the position of the arrow to change the location of the bubble.
                .backgroundColor(color) //Bubble background color
                .textColor(Color.WHITE) //Bubble Text color
                .titleTextSize(17) //Title text size in SP (default value 16sp)
                .descriptionTextSize(15) //Subtitle text size in SP (default value 14sp)
                //                .image(imageDrawable) //Bubble main image
                .closeActionImage(closeDrawable) //Custom close action image
                .showOnce("10") //Id to show only once the BubbleShowCase
                .targetView(img_share);

        BubbleShowCaseBuilder builder_swipeLeft = new BubbleShowCaseBuilder(this) //Activity instance
                .title("डावीकडे स्वाइप करा\n") //Any title for the bubble view
                .description("मागील भाग पाहण्यासाठी डावीकडे स्वाइप करा\n") //More detailed description
                .arrowPosition(BubbleShowCase.ArrowPosition.TOP) //You can force the position of the arrow to change the location of the bubble.
                .backgroundColor(color) //Bubble background color
                .textColor(Color.WHITE) //Bubble Text color
                .titleTextSize(17) //Title text size in SP (default value 16sp)
                .descriptionTextSize(15) //Subtitle text size in SP (default value 14sp)
                //                .image(imageDrawable) //Bubble main image
                .closeActionImage(closeDrawable) //Custom close action image
                .showOnce("11") //Id to show only once the BubbleShowCase
                .targetView(img_swipe_left);


        BubbleShowCaseBuilder builder_swipeRight = new BubbleShowCaseBuilder(this) //Activity instance
                .title("उजवीकडे स्वाइप करा\n") //Any title for the bubble view
                .description("पुढील भाग पाहण्यासाठी उजवीकडे स्वाइप करा\n") //More detailed description
                .arrowPosition(BubbleShowCase.ArrowPosition.TOP) //You can force the position of the arrow to change the location of the bubble.
                .backgroundColor(color) //Bubble background color
                .textColor(Color.WHITE) //Bubble Text color
                .titleTextSize(17) //Title text size in SP (default value 16sp)
                .descriptionTextSize(15) //Subtitle text size in SP (default value 14sp)
                //                .image(imageDrawable) //Bubble main image
                .closeActionImage(closeDrawable) //Custom close action image
                .showOnce("12") //Id to show only once the BubbleShowCase
                .targetView(img_swipe_right);


        new BubbleShowCaseSequence()
                .addShowCase(builder_audio) //First BubbleShowCase to show
                .addShowCase(builder_font) // This one will be showed when firstShowCase is dismissed
                .addShowCase(builder_index) // This one will be showed when secondShowCase is dismissed
                .addShowCase(builder_share_whatsapp) // This one will be showed when secondShowCase is dismissed
                .addShowCase(builder_swipeLeft) // This one will be showed when secondShowCase is dismissed
                .addShowCase(builder_swipeRight) // This one will be showed when secondShowCase is dismissed
                .show(); //Display the ShowCaseSequence


    }

    private void inflateAd(NativeBannerAd nativeBannerAd) {
        // Unregister last ad
        nativeBannerAd.unregisterView();

        // Add the Ad view into the ad container.
        nativeAdLayout = findViewById(R.id.native_banner_ad_container);
        LayoutInflater inflater = LayoutInflater.from(ShivCharitraActivity.this);
        // Inflate the Ad view.  The layout referenced is the one you created in the last step.
        LinearLayout adView = (LinearLayout) inflater.inflate(R.layout.native_banner_ad_unit, nativeAdLayout, false);
        nativeAdLayout.addView(adView);

        // Add the AdChoices icon
        RelativeLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(ShivCharitraActivity.this, nativeBannerAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        AdIconView nativeAdIconView = adView.findViewById(R.id.native_icon_view);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdCallToAction.setText(nativeBannerAd.getAdCallToAction());
        nativeAdCallToAction.setVisibility(
                nativeBannerAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdTitle.setText(nativeBannerAd.getAdvertiserName());
        nativeAdSocialContext.setText(nativeBannerAd.getAdSocialContext());
        sponsoredLabel.setText(nativeBannerAd.getSponsoredTranslation());

        // Register the Title and CTA button to listen for clicks.
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);
        nativeBannerAd.registerViewForInteraction(adView, nativeAdIconView, clickableViews);
    }

    public class BookOnboardingPagerAdapter extends FragmentPagerAdapter {
        ArrayList<BookPageIntroFragment> fragments = new ArrayList<BookPageIntroFragment>();


        public BookOnboardingPagerAdapter(FragmentManager fm) {
            super(fm);

            for (int i = 0; i < titles.length; i++) {
                BookPageIntroFragment frag = BookPageIntroFragment.newInstance(titles[i], content[i]);
                fragments.add(frag);
            }
        }


        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (tts != null) {
            tts.shutdown();
        }
        if (interstitialAd != null && !interstitialAd.isAdInvalidated()) {
            interstitialAd.show();

        } else {
            finish();

        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (tts != null) {
            tts.shutdown();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tts != null) {
            tts.shutdown();
        }
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
    }
}

