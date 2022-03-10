package com.arshana.raje.newActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arshana.raje.Adapter.PhotoGalleryAdapter;
import com.arshana.raje.Constant.API;
import com.arshana.raje.Constant.RuntimePermissionsActivity;
import com.arshana.raje.Model.WallpaperModel;
import com.arshana.raje.R;
import com.arshana.raje.data.Constant;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PhotoGalleryActivity extends RuntimePermissionsActivity {
    RecyclerView recyclerPhotoGallery;
    PhotoGalleryAdapter photoGalleryAdapter;
    Context context = this;
    ProgressDialog progressDialog;
    String code, message;
    ArrayList<WallpaperModel> arrayList;
    private static final int REQUEST_PERMISSIONS = 20;


    private final String TAG = HomePageActivity.class.getSimpleName();
    private NativeBannerAd nativeBannerAd;
    private NativeAdLayout nativeAdLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);
        nativeBannerAd = new NativeBannerAd(this, Constant.GALLERY_NATIVE_BANNER);
        nativeBannerAd.setAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
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
        init();
        PhotoGalleryActivity.super.requestAppPermissions(new String[]{
                        Manifest.permission.WAKE_LOCK,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                }, R.string.runtime_permissions_txt
                , REQUEST_PERMISSIONS);
    }

    private void inflateAd(NativeBannerAd nativeBannerAd) {
        // Unregister last ad
        nativeBannerAd.unregisterView();

        // Add the Ad view into the ad container.
        nativeAdLayout = findViewById(R.id.native_banner_ad_container);
        LayoutInflater inflater = LayoutInflater.from(PhotoGalleryActivity.this);
        // Inflate the Ad view.  The layout referenced is the one you created in the last step.
        LinearLayout adView = (LinearLayout) inflater.inflate(R.layout.native_banner_ad_unit, nativeAdLayout, false);
        nativeAdLayout.addView(adView);

        // Add the AdChoices icon
        RelativeLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(PhotoGalleryActivity.this, nativeBannerAd, nativeAdLayout);
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

    @Override
    public void onPermissionsGranted(int requestCode) {
        getWallpaperAPI();
    }

    private void getWallpaperAPI() {
        if (API.isNetworkConnected(PhotoGalleryActivity.this)) {
            arrayList = new ArrayList<>();
            progressDialog.show();
            recyclerPhotoGallery.setVisibility(View.VISIBLE);
            arrayList.clear();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, API.getWallpaperAPI, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        code = jsonObject.getString("code");
                        message = jsonObject.getString("message");
                        if (code.equals("1")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("list");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                WallpaperModel wallpaperModel = new WallpaperModel();
                                wallpaperModel.setId(jsonObject1.getString("id"));
                                wallpaperModel.setWallpaper(jsonObject1.getString("image").replace(" ", "%20"));
                                arrayList.add(wallpaperModel);
                            }
                            photoGalleryAdapter = new PhotoGalleryAdapter(arrayList, context);
                            recyclerPhotoGallery.setAdapter(photoGalleryAdapter);
                            progressDialog.dismiss();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        } else {
            AlertDialog.Builder builderSingle1 = new AlertDialog.Builder(PhotoGalleryActivity.this);
            builderSingle1.setMessage(getResources().getString(R.string.net_connection));
            builderSingle1.setTitle(getResources().getString(R.string.net_connection_title));
            builderSingle1.setPositiveButton("ठीक आहे", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            builderSingle1.setCancelable(false);
            builderSingle1.show();
        }

    }


    private void init() {

        recyclerPhotoGallery = (RecyclerView) findViewById(R.id.recyclerVideoGallery);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerPhotoGallery.setLayoutManager(layoutManager);
        recyclerPhotoGallery.setItemAnimator(new DefaultItemAnimator());
        ImageView back = (ImageView) findViewById(R.id.img_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        TextView toolbarsave = (TextView) findViewById(R.id.txt_tool_title);
        toolbarsave.setText(getApplicationContext().getResources().getString(R.string.photo_gallery));


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");

        progressDialog.setCancelable(false);


    }

    @Override
    public void onBackPressed() {
      finish();
    }
}
