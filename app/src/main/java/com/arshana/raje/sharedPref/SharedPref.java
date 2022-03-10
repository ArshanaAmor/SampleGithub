package com.arshana.raje.sharedPref;

import android.content.Context;
import android.content.SharedPreferences;

import com.arshana.raje.R;

/**
 * Created by Darshana Chorat on 2020-05-02.
 */
public class SharedPref {
    static String prefName = "ShivajiRaje";
    static String fontSize = "fontSize";
    static String lastPage = "lastPage";
    static String showRateDialog = "showRateDialog";
    static String appLaunches = "appLaunches";
    static String lastPageHistory = "lastPageHistory";

    public static void saveFontSize(Context context, int size) {
        SharedPreferences prefs = context.getSharedPreferences(
                prefName, Context.MODE_PRIVATE);

        prefs.edit().putInt(fontSize, size).apply();
    }

    public static void saveAppLaunches(Context context, int number) {
        SharedPreferences prefs = context.getSharedPreferences(
                prefName, Context.MODE_PRIVATE);

        prefs.edit().putInt(appLaunches, number).apply();
    }


    public static void saveShowDialog(Context context, int size) {
        //0 for show 1 for no
        SharedPreferences prefs = context.getSharedPreferences(
                prefName, Context.MODE_PRIVATE);

        prefs.edit().putInt(showRateDialog, size).apply();
    }


    public static int getShowDialog(Context context) {
        //0 for show 1 for no

        SharedPreferences prefs = context.getSharedPreferences(
                prefName, Context.MODE_PRIVATE);

        return prefs.getInt(showRateDialog, 0);
    }


    public static int getFontSize(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                prefName, Context.MODE_PRIVATE);

        return prefs.getInt(fontSize, 14);
    }

    public static int getAppLaunches(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                prefName, Context.MODE_PRIVATE);

        return prefs.getInt(appLaunches, 0);
    }

    public static void saveLastPage(Context context, int pageNo) {
//        int pageNo = Integer.parseInt(lastPage);
        SharedPreferences prefs = context.getSharedPreferences(
                prefName, Context.MODE_PRIVATE);

        prefs.edit().putInt(lastPage, pageNo).apply();
    }

    public static void saveLastPageHistory(Context context, int pageNo) {
//        int pageNo = Integer.parseInt(lastPage);
        SharedPreferences prefs = context.getSharedPreferences(
                prefName, Context.MODE_PRIVATE);

        prefs.edit().putInt(lastPageHistory, pageNo).apply();
    }

    public static int getLastPage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                prefName, Context.MODE_PRIVATE);

        return prefs.getInt(lastPage, 0);
    }


    public static int getLastPageHistory(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                prefName, Context.MODE_PRIVATE);

        return prefs.getInt(lastPageHistory, 0);
    }


    public static void saveShivCharitraNotification(Context context, String title) {

        SharedPreferences prefs = context.getSharedPreferences(
                prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("ShivCharitraNotificationTitle", title);
        editor.apply();
    }


    public static String getShivCharitraNotificationTitle(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                prefName, Context.MODE_PRIVATE);

        return prefs.getString("ShivCharitraNotificationTitle", context.getResources().getString(R.string.episode0_title));
    }

    public static String getShivCharitraNotificationContent(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                prefName, Context.MODE_PRIVATE);

        return prefs.getString("ShivCharitraNotificationContent", "संपूर्ण भागाबद्दल अधिक माहिती वाचण्यासाठी येथे क्लिक करा");
    }

    public static void saveHistoryNotification(Context context, String title) {

        SharedPreferences prefs = context.getSharedPreferences(
                prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("HistoryNotificationTitle", title);
        editor.apply();
    }

    public static String getHistoryNotificationTitle(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                prefName, Context.MODE_PRIVATE);
        return prefs.getString("HistoryNotificationTitle", "छत्रपती शिवाजी महाराज यांचा जन्म");
    }

    public static String getHistoryNotificationContent(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                prefName, Context.MODE_PRIVATE);
        return prefs.getString("HistoryNotificationContent", "संपूर्ण माहिती वाचण्यासाठी येथे क्लिक करा");
    }


    public static String getRajmudraNotificationContent(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                prefName, Context.MODE_PRIVATE);

        return prefs.getString("RajmudraNotificationContent", "प्रतिपच्चंद्रलेखेव वर्धिष्णुर्विश्ववंदिता शाहसुनोः शिवस्यैषा मुद्रा भद्राय राजते।\n\nसंपूर्ण राजमुद्रा वाचण्यासाठी येथे क्लिक करा");
    }


    public static String getBooksNotificationContent(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                prefName, Context.MODE_PRIVATE);

        return prefs.getString("RajmudraNotificationContent", "छत्रपती शिवाजी महाराजांविषयी अधिक पुस्तके वाचण्यासाठी येथे क्लिक करा");
    }


    public static String getFortNotificationContent(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                prefName, Context.MODE_PRIVATE);

        return prefs.getString("FortNotificationContent", "महाराष्ट्रातील किल्ल्यांची यादी येथे आहे जी आपल्याला भारतातील या राज्याचा गौरवशाली आणि शाही इतिहास समजण्यास मदत करेल आणि एकाच वेळी या आकर्षक केंद्रांना भेट देण्यास पुष्कळ कारणे देईल.\n छत्रपती शिवाजी महाराज यांच्या किल्ल्याबद्दल अधिक जाणून घेण्यासाठी येथे क्लिक करा");
    }

    public static String getGalleryNotificationContent(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                prefName, Context.MODE_PRIVATE);

        return prefs.getString("GalleryNotificationContent", "आम्ही गॅलरीमध्ये नवीन फोटो जोडले आहेत हे पाहण्यासाठी येथे क्लिक करा\n");
    }

    public static String getStatusNotificationContent(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                prefName, Context.MODE_PRIVATE);

        return prefs.getString("StatusNotificationContent", "छत्रपती शिवाजी महाराजांविषयी सोशल मीडियावर पाठवण्यासाठी सुंदर सुप्रभात आणि शुभ रात्री स्टेटस. अधिक जाणून घेण्यासाठी येथे क्लिक करा");
    }


}
