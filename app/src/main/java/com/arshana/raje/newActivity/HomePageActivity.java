package com.arshana.raje.newActivity;

import android.app.ProgressDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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
import com.arshana.raje.data.InitViews;
import com.arshana.raje.notification.AlarmService;
import com.arshana.raje.notification.NotiManager;
import com.arshana.raje.sharedPref.SharedPref;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.google.android.material.navigation.NavigationView;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.kobakei.ratethisapp.RateThisApp;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    CardView lnr_rajmudra, lnr_gallery, lnr_shivcharitra, lnr_book, lnr_history, lnr_video, lnr_fort, lnr_mavale, lnr_status, card_news;
    Intent intent;
    NavigationView arcNavigationView;
    DrawerLayout drawer;
    Toolbar toolbar;
    TextView txt_news_title, txt_description;
    ImageView img_news;
    RelativeLayout rlt_add_to_whatsapp;

    ProgressDialog progressDialog = null;
    String titile = "", description = "", image = "";
    String code = "";
    Uri uri;
    InterstitialAd interstitialAd;
    private final String TAG = HomePageActivity.class.getSimpleName();

    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        upateApp();
        NotiManager.createOngoinNotification(this);
        interstitialAd = new InterstitialAd(this, com.arshana.raje.data.Constant.HOME_INT);
        interstitialAd.loadAd();

        NotiManager.setAlarmUsingService(getApplicationContext());
        LinearLayout adContainer = findViewById(R.id.banner_container);
        adView = InitViews.initBanner(getApplicationContext(), adContainer, com.arshana.raje.data.Constant.HOME_BANNER);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        TextView txt_share_data_on_whatsapp = findViewById(R.id.txt_share_data_on_whatsapp);
        ImageView img_connectWhatsapp = findViewById(R.id.img_connectWhatsapp);
        img_connectWhatsapp.setOnClickListener(this);
        ImageView img_connectInstagram = findViewById(R.id.img_connectInstagram);
        img_connectInstagram.setOnClickListener(this);
        ImageView img_connectFacebook = findViewById(R.id.img_connectFacebook);
        img_connectFacebook.setOnClickListener(this);
        ImageView img_connectGmail = findViewById(R.id.img_connectGmail);
        img_connectGmail.setOnClickListener(this);
        txt_share_data_on_whatsapp.setSelected(true);
        txt_share_data_on_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackageManager packageManager = getPackageManager();
                Intent i = new Intent(Intent.ACTION_VIEW);

                try {
                    String url = "https://api.whatsapp.com/send?phone=919326896183";
                    i.setPackage("com.whatsapp");
                    i.setData(Uri.parse(url));
                    if (i.resolveActivity(packageManager) != null) {
                        startActivity(i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        int total_app_launches = SharedPref.getAppLaunches(this);
        SharedPref.saveAppLaunches(this, total_app_launches + 1);
        if (SharedPref.getAppLaunches(this) % 25 == 0) {
            if (SharedPref.getShowDialog(this) == 0) {
                rateUsDialog();
            }

        }

        arcNavigationView = findViewById(R.id.arcNavigationView);
        arcNavigationView.setNavigationItemSelectedListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(true);
        lnr_rajmudra = findViewById(R.id.lnr_rajmudra);
        rlt_add_to_whatsapp = findViewById(R.id.rlt_add_to_whatsapp);
        lnr_shivcharitra = findViewById(R.id.lnr_shivcharitra);
        lnr_gallery = findViewById(R.id.lnr_gallery);
        lnr_book = findViewById(R.id.lnr_book);
        lnr_history = findViewById(R.id.lnr_history);
        lnr_video = findViewById(R.id.lnr_video);
        lnr_fort = findViewById(R.id.lnr_fort);
        lnr_mavale = findViewById(R.id.lnr_mavale);
        lnr_status = findViewById(R.id.lnr_status);
        txt_news_title = findViewById(R.id.txt_news_title);
        txt_description = findViewById(R.id.txt_description);
        img_news = findViewById(R.id.img_news);
        card_news = findViewById(R.id.card_news);
        lnr_rajmudra.setOnClickListener(this);
        lnr_video.setOnClickListener(this);
        lnr_fort.setOnClickListener(this);
        lnr_mavale.setOnClickListener(this);
        lnr_gallery.setOnClickListener(this);
        rlt_add_to_whatsapp.setOnClickListener(this);
        lnr_book.setOnClickListener(this);
        lnr_status.setOnClickListener(this);
        card_news.setOnClickListener(this);
        lnr_history.setOnClickListener(this);
        lnr_shivcharitra.setOnClickListener(this);
        drawer = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar1);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getLatestNews();


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_share_app:
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                String sAux = "अखंड महाराष्ट्राचे आराध्य दैवत श्रीमंतयोगी छत्रपती शिवाजी महाराज यांच्यावर बनवलेला अप्रतिम अँप्लिकेशन" + Constant.PLAYSTORE_URL;
                intent.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(intent);
                break;

            case R.id.nav_rate_ap:
                uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
                intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                }
                break;
            case R.id.nav_about_us:

                intent = new Intent(HomePageActivity.this, AboutUsActivity.class);
                startActivity(intent);

                break;

            case R.id.nav_more_apps:


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    JobScheduler jobScheduler = (JobScheduler) getApplicationContext()
                            .getSystemService(JOB_SCHEDULER_SERVICE);

                    ComponentName componentName = new ComponentName(this,
                            AlarmService.class);

                    JobInfo jobInfo = null;
                    jobInfo = new JobInfo.Builder(1, componentName)
                            .setPeriodic(900000).build();
                    jobScheduler.schedule(jobInfo);

                }
//                uri = Uri.parse("https://play.google.com/store/apps/developer?id=Arshana+Developers");
//                intent = new Intent(Intent.ACTION_VIEW, uri);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
//                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//                try {
//                    startActivity(intent);
//                } catch (ActivityNotFoundException e) {
//                    startActivity(new Intent(Intent.ACTION_VIEW,
//                            Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
//                }
                break;

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void rateUsDialog() {

        RateThisApp.Config config = new RateThisApp.Config();
        config.setTitle(R.string.my_own_title);
        config.setMessage(R.string.my_own_message);
        config.setYesButtonText(R.string.submit);
        config.setNoButtonText(R.string.never);
        config.setCancelButtonText(R.string.not_now);
        RateThisApp.init(config);
        RateThisApp.setCallback(new RateThisApp.Callback() {
            @Override
            public void onYesClicked() {
                SharedPref.saveShowDialog(HomePageActivity.this, 1);
            }

            @Override
            public void onNoClicked() {
                SharedPref.saveShowDialog(HomePageActivity.this, 1);

            }

            @Override
            public void onCancelClicked() {
            }
        });
        RateThisApp.showRateDialog(HomePageActivity.this);
    }

    @Override
    public void onClick(View view) {
        Uri uri;
        Intent likeIng;
        switch (view.getId()) {
            case R.id.lnr_rajmudra:
                intent = new Intent(getApplicationContext(), RajmudraActivity.class);
                startActivity(intent);

                break;
            case R.id.lnr_gallery:
                intent = new Intent(getApplicationContext(), PhotoGalleryActivity.class);
                showAds();
                break;
            case R.id.lnr_history:
                intent = new Intent(getApplicationContext(), HistoryActivity.class);
                showAds();
                break;
            case R.id.lnr_book:
                intent = new Intent(getApplicationContext(), BookActivity.class);
                startActivity(intent);
                break;
            case R.id.card_news:
                intent = new Intent(getApplicationContext(), ViewNewsActivity.class);
                startActivity(intent);
                break;

            case R.id.lnr_shivcharitra:
                intent = new Intent(getApplicationContext(), ShivCharitraActivity.class);
                showAds();
                break;

            case R.id.lnr_fort:
                intent = new Intent(getApplicationContext(), FortActivity.class);
                showAds();
                break;
            case R.id.img_connectWhatsapp:
                Intent intentWhatsapp = new Intent(Intent.ACTION_VIEW);
                String url = getResources().getString(R.string.whatsapp_link);
                intentWhatsapp.setData(Uri.parse(url));
                intentWhatsapp.setPackage("com.whatsapp");
                try {
                    startActivity(intentWhatsapp);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(HomePageActivity.this, "Whatsapp not installed", Toast.LENGTH_SHORT).show();

                }


                break;
            case R.id.img_connectInstagram:
                uri = Uri.parse(getResources().getString(R.string.insta_link));
                likeIng = new Intent(Intent.ACTION_VIEW, uri);
                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(getResources().getString(R.string.insta_link))));
                }

                break;
            case R.id.img_connectFacebook:
                uri = Uri.parse("fb://page/106208061117924");
                likeIng = new Intent(Intent.ACTION_VIEW, uri);
                likeIng.setPackage("com.facebook.katana");
                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(getResources().getString(R.string.facebook_link))));
                }

                break;
            case R.id.img_connectGmail:
                intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("message/rfc822");
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getResources().getString(R.string.gmail_id)});
                try {
                    startActivity(Intent.createChooser(intent, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(HomePageActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.lnr_status:
                intent = new Intent(getApplicationContext(), StatusActivity.class);
                showAds();
                break;


        }

    }

    public static String FACEBOOK_URL = "https://www.facebook.com/shivajimaaharaj-106208061117924";
    public static String FACEBOOK_PAGE_ID = "shivajimaaharaj-106208061117924";

    //method to get the right URL to use in the intent
    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }

    AppUpdateManager mAppUpdateManager;

    public void upateApp() {

        mAppUpdateManager = AppUpdateManagerFactory.create(this);

        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {

                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                    popupSnackbarForCompleteUpdate();

                }
            }
        });


    }

    private void popupSnackbarForCompleteUpdate() {
        AlertDialog.Builder builderSingle1 = new AlertDialog.Builder(HomePageActivity.this);
        builderSingle1.setTitle("नवीनतम अँपचा आवृत्ती उपलब्ध. अपडेटेड अँप");
        builderSingle1.setPositiveButton("स्थापित करा", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String appPackageName = getPackageName(); // package name of the app
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                dialog.dismiss();
            }
        });
        builderSingle1.setNegativeButton("रद्द करा", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        builderSingle1.show();

    }

    private void getLatestNews() {


        if (API.isNetworkConnected(HomePageActivity.this)) {

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
                                txt_news_title.setText("गोधडीवर साकारला शिवराज्याभिषेक सोहळा...");
                            } else {
                                txt_news_title.setText(Html.fromHtml(titile));
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
                                Picasso.with(HomePageActivity.this).load(API.IMAGE_PATH_NEWS + image.replace(" ", "%20")).into(img_news);

                            }
                        } else {
                            progressDialog.dismiss();
                            if (titile.equals("")) {
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
            description = "";
            if (titile.equals("")) {
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
            image = "";
        }

    }

    private void showAds() {

        startActivity(intent);

    }


    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (interstitialAd != null && !interstitialAd.isAdInvalidated()) {
            interstitialAd.show();

        } else {
            finish();

        }

    }

}
