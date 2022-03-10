package com.arshana.raje.Constant;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.net.ConnectivityManager.TYPE_MOBILE;
import static android.net.ConnectivityManager.TYPE_WIFI;

public class API {
    public static String SERVER_URL = "http://139.59.86.73/ShivajiRaje/";
    public static String WEBSERVICE_PATH = SERVER_URL + "API/";
    public static String IMAGE_PATH = SERVER_URL + "upload/gallery/";
    public static String IMAGE_PATH_NEWS = SERVER_URL + "upload/news/";
    public static String FORT_PATH = SERVER_URL + "upload/Fort/";
    public static String MORE_APPI_MAGE_PATH = SERVER_URL + "upload/AppLogo/";
    public static String PDF_PATH = SERVER_URL + "upload/PDF/";
    public static String getStatusAPI = WEBSERVICE_PATH + "getStatusAPI.php";
    public static String getMoreAppsAPI = WEBSERVICE_PATH + "getMoreApps.php";
    public static String getFortAPI = WEBSERVICE_PATH + "getFortAPI.php";
    public static String getLatestNewsAPI = WEBSERVICE_PATH + "getLatestNewsAPI.php";
    public static String getVideoAPI = WEBSERVICE_PATH + "getVideoAPI.php";
    public static String getWallpaperAPI = WEBSERVICE_PATH + "getWallpaperAPI.php";
    public static String addUserAPI = WEBSERVICE_PATH + "addUserAPI.php";

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnectedOrConnecting();
        boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting();


        if (is3g || isWifi) {
            return true;
        }
        return false;

    }


}
