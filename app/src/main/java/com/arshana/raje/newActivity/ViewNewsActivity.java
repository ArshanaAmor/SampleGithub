package com.arshana.raje.newActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arshana.raje.Constant.API;
import com.arshana.raje.Constant.Constant;
import com.arshana.raje.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewNewsActivity extends AppCompatActivity {
    ImageView img_back;
    TextView txt_title, txt_description, txt_news_title;
    ImageView img_news, img_share;
    String image="", description="", title="";
    private final String TAG = ViewNewsActivity.class.getSimpleName();
    private NativeAd nativeAd;
    private NativeAdLayout nativeAdLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_news);
        loadNativeAd();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(true);
        img_back = findViewById(R.id.img_back);
        txt_title = findViewById(R.id.txt_tool_title);
        txt_title = findViewById(R.id.txt_tool_title);
        txt_news_title = findViewById(R.id.txt_news_title);
        txt_description = findViewById(R.id.txt_description);
        img_news = findViewById(R.id.img_news);
        img_share = findViewById(R.id.img_share);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content =  Constant.PLAYSTORE_URL+"\n\n"+txt_news_title.getText().toString() + "\n\n" + txt_description.getText().toString() ;
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, content  );
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

            }
        });

    }
    ProgressDialog progressDialog = null;

    @Override
    protected void onResume() {
        super.onResume();
        getLatestNews();
    }
    String code,titile="";
    private void loadNativeAd() {
        // Instantiate a NativeAd object.
        // NOTE: the placement ID will eventually identify this as your App, you can ignore it for
        // now, while you are testing and replace it later when you have signed up.
        // While you are using this temporary code you will only get test ads and if you release
        // your code like this to the Google Play your users will not receive ads (you will get a no fill error).
        nativeAd = new NativeAd(this, com.arshana.raje.data.Constant.LEKH_NATIVE);

        nativeAd.setAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // Native ad finished downloading all assets
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }

                inflateAd(nativeAd);
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                }

            @Override
            public void onAdLoaded(Ad ad) {
               if (nativeAd == null || nativeAd != ad) {
                    return;
                }

                nativeAd.downloadMedia();
            }

            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });

        // Request an ad
        nativeAd.loadAd();
    }
    private void inflateAd(NativeAd nativeAd) {

        nativeAd.unregisterView();

        // Add the Ad view into the ad container.
        nativeAdLayout = findViewById(R.id.native_ad_container);
        LayoutInflater inflater = LayoutInflater.from(ViewNewsActivity.this);
        // Inflate the Ad view.  The layout referenced ViewN be the one you created in the last step.
        LinearLayout adView = (LinearLayout) inflater.inflate(R.layout.native_ad_layout_1, nativeAdLayout, false);
        nativeAdLayout.addView(adView);

        // Add the AdOptionsView
        LinearLayout adChoicesContainer = findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(ViewNewsActivity.this, nativeAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        AdIconView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(
                adView,
                nativeAdMedia,
                nativeAdIcon,
                clickableViews);
    }

    private void getLatestNews() {


        if (API.isNetworkConnected(ViewNewsActivity.this)) {

            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, API.getLatestNewsAPI, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        progressDialog.dismiss();
                        JSONObject jsonObject = new JSONObject(response);
                        code = jsonObject.getString("code");
                        if (code.equals("1")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("list");
                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                            titile = jsonObject1.getString("title");
                            description = jsonObject1.getString("description");
                            image = jsonObject1.getString("image");
                            if (titile.equals("")) {
                                txt_title.setText("गोधडीवर साकारला शिवराज्याभिषेक सोहळा...");
                                txt_news_title.setText("गोधडीवर साकारला शिवराज्याभिषेक सोहळा...");
                            } else {
                                txt_news_title.setText(Html.fromHtml(titile));
                                txt_title.setText(Html.fromHtml(titile));
                                //            txt_title.setText("गोधडीवर साकारला शिवराज्याभिषेक सोहळा...");

                            }
                            if (description.equals("")) {
                                txt_description.setText("छत्रपती शिवाजी महाराज यांच्या राज्याभिषेक सोहळ्याची सुंदर कलाकृती एका 'क्विल्ट' वर म्हणजेच 'गोधडी'वर साकारण्यात आली आहे. तब्बल 20 हजार 888 कापडी तुकडे, 288 रंगछटा आणि 19 बाय 8 फुटांचा थक्क करणारा राज्याभिषेक सोहळा सांगलीच्या महिला आर्किटेक्ट श्रुती दांडेकर साकारला आहे. सांगलीत मोठ्य दिमाखात या शिवराज्यभिषेक गोधडीचे अनावरण करण्यात आलं..\n" +
                                        "\n" +
                                        "डिझायनर असणाऱ्या सांगलीच्या श्रुती दांडेकर गेल्या काही वर्षांपासून पारंपारिक गोधडीला नव्या रुपात जगासमोर आणण्याचा काम करत आहेत. आजीबाईची समजली जाणारी गोधडी दांडेकर यांनी क्विल्टच्या रुपाने सातासमुद्रापार नेली आहे. आज त्यांच्या या गोधडीला म्हणजेच क्विल्टला छत्रपती शिवाजी महाराज यांच्या राज्याभिषेक सोहळ्याच्या रुपाने नवा आयाम मिळाला आहे. छत्रपतीचा इतिहास जगभर पोहोचावा या उद्देशाने श्रुती दांडेकर यांनी या कलाकृतीची निर्मिती केली आहे.\n" +
                                        "\n" +
                                        "क्विल्टमध्ये अगदी 3 मिमी इतक्या लहान कपड्यांच्या तुकड्याचा समावेश आहे. राज्याभिषेकाच्या पेंटिंगप्रमाणे हुबेहूब कलाकृती या गोधडीमध्ये रेखीव स्वरुपात साकारली आहे. प्रत्येक गोष्ट याठिकाणी अत्यंत बारकाईने जोडण्यात आली आहे.\n" +
                                        "\n" +
                                        "\n" +
                                        "ही गोधडी साकारण्यासाठी श्रुती दांडेकर यांना तब्बल दहा महिने आणि 807 तासाचा कालावधी लागला आहे. तर गोधडीवर शिवराज्याभिषिक साकारण्यासाठी त्यांनी ज्येष्ठ इतिहासकार बाबासाहेब पुरंदरे यांचे मार्गदर्शन घेऊन सुरुवात केली. पुण्याच्या मनीषा अय्यर यांच्या स्टुडियो बानीमध्ये याचं संपूर्ण शिवणकाम केलं आहे.\n" +
                                        "\n" +
                                        "\n" +
                                        "आता ही क्विल्ट म्हणजेच गोधडी 25 ते 27 जानेवारी चेन्नई इथे होणाऱ्या इंडिया क्विल्ट फेस्टिवलमध्ये सहभागी होणार आहे. शिवाय फेस्टिवलमध्येही छत्रपतींच्या राज्याभिषेकाची गोधडी अर्थात क्विल्ट प्रमुख आकर्षया असणार आहे.\n" +
                                        "\n" +
                                        "\n" +
                                        "तत्पूर्वी सांगलीच्या आभाळमाया फाऊंडेशनेने श्रुती दांडेकर यांची ही कलाकृती आणि छत्रपतींच्या इतिहासाला सांगलीकरांच्या प्रथम समोर आणण्याच्या उद्देशाने आज मोठ्या दिमाखात शानदार सोहळ्यात अनावरण केले आहे.\n" +
                                        "\n" +
                                        "\n" +
                                        "दांडेकर यांच्या कला शिक्षिकीच्या हस्ते यावेळी या भव्य दिव्य शिवराज्याभिषकाच्या सुंदर कलाकृतीचे अनावरण संपन्न झाले . ही कलाकृती पाहण्यासाठी सांगलीकरानी मोठी गर्दी केली होती.\n");
                            } else {


                                txt_description.setText(Html.fromHtml(description));
                            }

                            if (!image.equals("")) {
                                Picasso.with(ViewNewsActivity.this).load(API.IMAGE_PATH_NEWS + image.replace(" ", "%20")).into(img_news);

                            }
                        } else {
                            progressDialog.dismiss();
                            if (titile.equals("")) {
                                txt_title.setText("गोधडीवर साकारला शिवराज्याभिषेक सोहळा...");

                                txt_news_title.setText("गोधडीवर साकारला शिवराज्याभिषेक सोहळा...");
                            }
                            if (description.equals("")) {
                                txt_description.setText("छत्रपती शिवाजी महाराज यांच्या राज्याभिषेक सोहळ्याची सुंदर कलाकृती एका 'क्विल्ट' वर म्हणजेच 'गोधडी'वर साकारण्यात आली आहे. तब्बल 20 हजार 888 कापडी तुकडे, 288 रंगछटा आणि 19 बाय 8 फुटांचा थक्क करणारा राज्याभिषेक सोहळा सांगलीच्या महिला आर्किटेक्ट श्रुती दांडेकर साकारला आहे. सांगलीत मोठ्य दिमाखात या शिवराज्यभिषेक गोधडीचे अनावरण करण्यात आलं..\n" +
                                        "\n" +
                                        "डिझायनर असणाऱ्या सांगलीच्या श्रुती दांडेकर गेल्या काही वर्षांपासून पारंपारिक गोधडीला नव्या रुपात जगासमोर आणण्याचा काम करत आहेत. आजीबाईची समजली जाणारी गोधडी दांडेकर यांनी क्विल्टच्या रुपाने सातासमुद्रापार नेली आहे. आज त्यांच्या या गोधडीला म्हणजेच क्विल्टला छत्रपती शिवाजी महाराज यांच्या राज्याभिषेक सोहळ्याच्या रुपाने नवा आयाम मिळाला आहे. छत्रपतीचा इतिहास जगभर पोहोचावा या उद्देशाने श्रुती दांडेकर यांनी या कलाकृतीची निर्मिती केली आहे.\n" +
                                        "\n" +
                                        "क्विल्टमध्ये अगदी 3 मिमी इतक्या लहान कपड्यांच्या तुकड्याचा समावेश आहे. राज्याभिषेकाच्या पेंटिंगप्रमाणे हुबेहूब कलाकृती या गोधडीमध्ये रेखीव स्वरुपात साकारली आहे. प्रत्येक गोष्ट याठिकाणी अत्यंत बारकाईने जोडण्यात आली आहे.\n" +
                                        "\n" +
                                        "\n" +
                                        "ही गोधडी साकारण्यासाठी श्रुती दांडेकर यांना तब्बल दहा महिने आणि 807 तासाचा कालावधी लागला आहे. तर गोधडीवर शिवराज्याभिषिक साकारण्यासाठी त्यांनी ज्येष्ठ इतिहासकार बाबासाहेब पुरंदरे यांचे मार्गदर्शन घेऊन सुरुवात केली. पुण्याच्या मनीषा अय्यर यांच्या स्टुडियो बानीमध्ये याचं संपूर्ण शिवणकाम केलं आहे.\n" +
                                        "\n" +
                                        "\n" +
                                        "आता ही क्विल्ट म्हणजेच गोधडी 25 ते 27 जानेवारी चेन्नई इथे होणाऱ्या इंडिया क्विल्ट फेस्टिवलमध्ये सहभागी होणार आहे. शिवाय फेस्टिवलमध्येही छत्रपतींच्या राज्याभिषेकाची गोधडी अर्थात क्विल्ट प्रमुख आकर्षया असणार आहे.\n" +
                                        "\n" +
                                        "\n" +
                                        "तत्पूर्वी सांगलीच्या आभाळमाया फाऊंडेशनेने श्रुती दांडेकर यांची ही कलाकृती आणि छत्रपतींच्या इतिहासाला सांगलीकरांच्या प्रथम समोर आणण्याच्या उद्देशाने आज मोठ्या दिमाखात शानदार सोहळ्यात अनावरण केले आहे.\n" +
                                        "\n" +
                                        "\n" +
                                        "दांडेकर यांच्या कला शिक्षिकीच्या हस्ते यावेळी या भव्य दिव्य शिवराज्याभिषकाच्या सुंदर कलाकृतीचे अनावरण संपन्न झाले . ही कलाकृती पाहण्यासाठी सांगलीकरानी मोठी गर्दी केली होती.\n");
                            }
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        if (titile.equals("")) {
                            txt_news_title.setText("गोधडीवर साकारला शिवराज्याभिषेक सोहळा...");
                            txt_title.setText("गोधडीवर साकारला शिवराज्याभिषेक सोहळा...");

                        }
                        if (description.equals("")) {
                            txt_description.setText("छत्रपती शिवाजी महाराज यांच्या राज्याभिषेक सोहळ्याची सुंदर कलाकृती एका 'क्विल्ट' वर म्हणजेच 'गोधडी'वर साकारण्यात आली आहे. तब्बल 20 हजार 888 कापडी तुकडे, 288 रंगछटा आणि 19 बाय 8 फुटांचा थक्क करणारा राज्याभिषेक सोहळा सांगलीच्या महिला आर्किटेक्ट श्रुती दांडेकर साकारला आहे. सांगलीत मोठ्य दिमाखात या शिवराज्यभिषेक गोधडीचे अनावरण करण्यात आलं..\n" +
                                    "\n" +
                                    "डिझायनर असणाऱ्या सांगलीच्या श्रुती दांडेकर गेल्या काही वर्षांपासून पारंपारिक गोधडीला नव्या रुपात जगासमोर आणण्याचा काम करत आहेत. आजीबाईची समजली जाणारी गोधडी दांडेकर यांनी क्विल्टच्या रुपाने सातासमुद्रापार नेली आहे. आज त्यांच्या या गोधडीला म्हणजेच क्विल्टला छत्रपती शिवाजी महाराज यांच्या राज्याभिषेक सोहळ्याच्या रुपाने नवा आयाम मिळाला आहे. छत्रपतीचा इतिहास जगभर पोहोचावा या उद्देशाने श्रुती दांडेकर यांनी या कलाकृतीची निर्मिती केली आहे.\n" +
                                    "\n" +
                                    "क्विल्टमध्ये अगदी 3 मिमी इतक्या लहान कपड्यांच्या तुकड्याचा समावेश आहे. राज्याभिषेकाच्या पेंटिंगप्रमाणे हुबेहूब कलाकृती या गोधडीमध्ये रेखीव स्वरुपात साकारली आहे. प्रत्येक गोष्ट याठिकाणी अत्यंत बारकाईने जोडण्यात आली आहे.\n" +
                                    "\n" +
                                    "\n" +
                                    "ही गोधडी साकारण्यासाठी श्रुती दांडेकर यांना तब्बल दहा महिने आणि 807 तासाचा कालावधी लागला आहे. तर गोधडीवर शिवराज्याभिषिक साकारण्यासाठी त्यांनी ज्येष्ठ इतिहासकार बाबासाहेब पुरंदरे यांचे मार्गदर्शन घेऊन सुरुवात केली. पुण्याच्या मनीषा अय्यर यांच्या स्टुडियो बानीमध्ये याचं संपूर्ण शिवणकाम केलं आहे.\n" +
                                    "\n" +
                                    "\n" +
                                    "आता ही क्विल्ट म्हणजेच गोधडी 25 ते 27 जानेवारी चेन्नई इथे होणाऱ्या इंडिया क्विल्ट फेस्टिवलमध्ये सहभागी होणार आहे. शिवाय फेस्टिवलमध्येही छत्रपतींच्या राज्याभिषेकाची गोधडी अर्थात क्विल्ट प्रमुख आकर्षया असणार आहे.\n" +
                                    "\n" +
                                    "\n" +
                                    "तत्पूर्वी सांगलीच्या आभाळमाया फाऊंडेशनेने श्रुती दांडेकर यांची ही कलाकृती आणि छत्रपतींच्या इतिहासाला सांगलीकरांच्या प्रथम समोर आणण्याच्या उद्देशाने आज मोठ्या दिमाखात शानदार सोहळ्यात अनावरण केले आहे.\n" +
                                    "\n" +
                                    "\n" +
                                    "दांडेकर यांच्या कला शिक्षिकीच्या हस्ते यावेळी या भव्य दिव्य शिवराज्याभिषकाच्या सुंदर कलाकृतीचे अनावरण संपन्न झाले . ही कलाकृती पाहण्यासाठी सांगलीकरानी मोठी गर्दी केली होती.\n");
                        }
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
            titile = "";
            if (titile.equals("")) {
                txt_title.setText("गोधडीवर साकारला शिवराज्याभिषेक सोहळा...");

                txt_news_title.setText("गोधडीवर साकारला शिवराज्याभिषेक सोहळा...");
            }
            if (description.equals("")) {
                txt_description.setText("छत्रपती शिवाजी महाराज यांच्या राज्याभिषेक सोहळ्याची सुंदर कलाकृती एका 'क्विल्ट' वर म्हणजेच 'गोधडी'वर साकारण्यात आली आहे. तब्बल 20 हजार 888 कापडी तुकडे, 288 रंगछटा आणि 19 बाय 8 फुटांचा थक्क करणारा राज्याभिषेक सोहळा सांगलीच्या महिला आर्किटेक्ट श्रुती दांडेकर साकारला आहे. सांगलीत मोठ्य दिमाखात या शिवराज्यभिषेक गोधडीचे अनावरण करण्यात आलं..\n" +
                        "\n" +
                        "डिझायनर असणाऱ्या सांगलीच्या श्रुती दांडेकर गेल्या काही वर्षांपासून पारंपारिक गोधडीला नव्या रुपात जगासमोर आणण्याचा काम करत आहेत. आजीबाईची समजली जाणारी गोधडी दांडेकर यांनी क्विल्टच्या रुपाने सातासमुद्रापार नेली आहे. आज त्यांच्या या गोधडीला म्हणजेच क्विल्टला छत्रपती शिवाजी महाराज यांच्या राज्याभिषेक सोहळ्याच्या रुपाने नवा आयाम मिळाला आहे. छत्रपतीचा इतिहास जगभर पोहोचावा या उद्देशाने श्रुती दांडेकर यांनी या कलाकृतीची निर्मिती केली आहे.\n" +
                        "\n" +
                        "क्विल्टमध्ये अगदी 3 मिमी इतक्या लहान कपड्यांच्या तुकड्याचा समावेश आहे. राज्याभिषेकाच्या पेंटिंगप्रमाणे हुबेहूब कलाकृती या गोधडीमध्ये रेखीव स्वरुपात साकारली आहे. प्रत्येक गोष्ट याठिकाणी अत्यंत बारकाईने जोडण्यात आली आहे.\n" +
                        "\n" +
                        "\n" +
                        "ही गोधडी साकारण्यासाठी श्रुती दांडेकर यांना तब्बल दहा महिने आणि 807 तासाचा कालावधी लागला आहे. तर गोधडीवर शिवराज्याभिषिक साकारण्यासाठी त्यांनी ज्येष्ठ इतिहासकार बाबासाहेब पुरंदरे यांचे मार्गदर्शन घेऊन सुरुवात केली. पुण्याच्या मनीषा अय्यर यांच्या स्टुडियो बानीमध्ये याचं संपूर्ण शिवणकाम केलं आहे.\n" +
                        "\n" +
                        "\n" +
                        "आता ही क्विल्ट म्हणजेच गोधडी 25 ते 27 जानेवारी चेन्नई इथे होणाऱ्या इंडिया क्विल्ट फेस्टिवलमध्ये सहभागी होणार आहे. शिवाय फेस्टिवलमध्येही छत्रपतींच्या राज्याभिषेकाची गोधडी अर्थात क्विल्ट प्रमुख आकर्षया असणार आहे.\n" +
                        "\n" +
                        "\n" +
                        "तत्पूर्वी सांगलीच्या आभाळमाया फाऊंडेशनेने श्रुती दांडेकर यांची ही कलाकृती आणि छत्रपतींच्या इतिहासाला सांगलीकरांच्या प्रथम समोर आणण्याच्या उद्देशाने आज मोठ्या दिमाखात शानदार सोहळ्यात अनावरण केले आहे.\n" +
                        "\n" +
                        "\n" +
                        "दांडेकर यांच्या कला शिक्षिकीच्या हस्ते यावेळी या भव्य दिव्य शिवराज्याभिषकाच्या सुंदर कलाकृतीचे अनावरण संपन्न झाले . ही कलाकृती पाहण्यासाठी सांगलीकरानी मोठी गर्दी केली होती.\n");
            }
            description = "";
            image = "";
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}