package com.arshana.raje.notification;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.arshana.raje.R;
import com.arshana.raje.newActivity.BookActivity;
import com.arshana.raje.newActivity.FortActivity;
import com.arshana.raje.newActivity.HistoryActivity;
import com.arshana.raje.newActivity.HomePageActivity;
import com.arshana.raje.newActivity.PhotoGalleryActivity;
import com.arshana.raje.newActivity.RajmudraActivity;
import com.arshana.raje.newActivity.ShivCharitraActivity;
import com.arshana.raje.newActivity.StatusActivity;
import com.arshana.raje.sharedPref.SharedPref;

import java.util.Calendar;

/**
 * Created by Darshana Chorat on 2020-05-04.
 */
public class NotiManager {


//        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (1000 * 30), sender);

    public static void setAlarmUsingService(Context context) {

        //Alarm For shivcharitra
        Calendar calShivcharitra = Calendar.getInstance();
        calShivcharitra.set(Calendar.HOUR_OF_DAY, 18);
        Intent intentShivCharitra = new Intent(context, AlarmService.class);
        intentShivCharitra.putExtra("type", "Shivcharitra");
        @SuppressLint("WrongConstant") PendingIntent senderShivCharitra = PendingIntent.getService(context, 1, intentShivCharitra, 1);
        AlarmManager amShivcharitra = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (amShivcharitra != null) {
            amShivcharitra.setRepeating(AlarmManager.RTC_WAKEUP, calShivcharitra.getTimeInMillis(), AlarmManager.INTERVAL_DAY, senderShivCharitra);

        }

        //Alarm For Rajmudra
        Calendar calRajmudra = Calendar.getInstance();
        calRajmudra.set(Calendar.HOUR_OF_DAY, 10);
        Intent intentRajmudra = new Intent(context, AlarmService.class);
        intentRajmudra.putExtra("type", "Rajmudra");
        @SuppressLint("WrongConstant") PendingIntent senderRajmudra = PendingIntent.getService(context, 2, intentRajmudra, 1);
        AlarmManager amRajmudra = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (amRajmudra != null) {
            amRajmudra.setRepeating(AlarmManager.RTC_WAKEUP, calRajmudra.getTimeInMillis(), AlarmManager.INTERVAL_DAY, senderRajmudra);

        }

        //Alarm For History
        Calendar calHistory = Calendar.getInstance();
        calHistory.set(Calendar.HOUR_OF_DAY, 12);
        Intent intentHistory = new Intent(context, AlarmService.class);
        intentHistory.putExtra("type", "History");
        @SuppressLint("WrongConstant") PendingIntent senderHistory = PendingIntent.getService(context, 3, intentHistory, 1);
        AlarmManager amHistory = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (amHistory != null) {
            amHistory.setRepeating(AlarmManager.RTC_WAKEUP, calHistory.getTimeInMillis(), AlarmManager.INTERVAL_DAY, senderHistory);

        }


        //Alarm For Fort
        Calendar calFort = Calendar.getInstance();
        calFort.set(Calendar.HOUR_OF_DAY, 14);
        Intent intentFort = new Intent(context, AlarmService.class);
        intentFort.putExtra("type", "Fort");
        @SuppressLint("WrongConstant") PendingIntent senderFort = PendingIntent.getService(context, 4, intentFort, 1);
        AlarmManager amFort = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (amFort != null) {
            amFort.setRepeating(AlarmManager.RTC_WAKEUP, calFort.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, senderFort);

        }


        //Alarm For Gallery
        Calendar calGallery = Calendar.getInstance();
        calGallery.set(Calendar.HOUR_OF_DAY, 8);
        Intent intentGallery = new Intent(context, AlarmService.class);
        intentGallery.putExtra("type", "Gallery");
        @SuppressLint("WrongConstant") PendingIntent sender = PendingIntent.getService(context, 5, intentGallery, 1);
        AlarmManager amGallery = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (amGallery != null) {
            amGallery.setRepeating(AlarmManager.RTC_WAKEUP, calGallery.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, sender);

        }

        //Alarm For Status
        Calendar calStatus = Calendar.getInstance();
        calStatus.set(Calendar.HOUR_OF_DAY, 22);
        Intent intentStatus = new Intent(context, AlarmService.class);
        intentStatus.putExtra("type", "Status");
        @SuppressLint("WrongConstant") PendingIntent senderStatus = PendingIntent.getService(context, 6, intentStatus, 1);
        AlarmManager amStatus = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (amStatus != null) {
            amStatus.setRepeating(AlarmManager.RTC_WAKEUP, calStatus.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 15, senderStatus);

        }

        //Alarm For Book
        Calendar calBook = Calendar.getInstance();
        calBook.set(Calendar.HOUR_OF_DAY, 20);
        Intent intentBook = new Intent(context, AlarmService.class);
        intentBook.putExtra("type", "Book");
        @SuppressLint("WrongConstant") PendingIntent senderBook = PendingIntent.getService(context, 7, intentBook, 1);
        AlarmManager amBook = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (amBook != null) {
            amBook.setRepeating(AlarmManager.RTC_WAKEUP, calBook.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 15, senderBook);

        }


    }

    public static void createOngoinNotification(Context context) {
        Intent intent = new Intent(context, HomePageActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 101, intent, 0);

        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle("This is the title");
        builder.setContentText("This is the text");
        builder.setSubText("Some sub text");
        builder.setNumber(101);
        builder.setContentIntent(pendingIntent);
        builder.setTicker("Fancy Notification");
        builder.setOngoing(true);
        builder.setSmallIcon(R.drawable.whatsapp_icon);
//        builder.setLargeIcon(bm);
        builder.setAutoCancel(true);
        builder.setPriority(Notification.PRIORITY_HIGH);
        Notification notification = builder.build();
        NotificationManager notificationManger =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "6";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManger.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }

        notificationManger.notify(101, notification);

    }
 public static void createNotificationFort(Context context) {
        NotificationManager mNotificationManager;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, "notify_006");
        Intent ii = new Intent(context, FortActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, 0);
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(SharedPref.getFortNotificationContent(context));
        bigText.setBigContentTitle("महाराष्ट्रातील बहुतेक किल्ले छत्रपती शिवाजी महाराज आणि त्यांचे मराठा साम्राज्य यांनी बांधले होते.");
        bigText.setSummaryText(context.getResources().getString(R.string.fort));
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_notification);
        mBuilder.setContentTitle("महाराष्ट्रातील बहुतेक किल्ले छत्रपती शिवाजी महाराज आणि त्यांचे मराठा साम्राज्य यांनी बांधले होते.");
        mBuilder.setContentText(SharedPref.getFortNotificationContent(context));
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);
        mBuilder.setAutoCancel(true);
        mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "6";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(6, mBuilder.build());


    }

    public static void createNotificationStatus(Context context) {
        NotificationManager mNotificationManager;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, "notify_003");
        Intent ii = new Intent(context, StatusActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, 0);
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(SharedPref.getStatusNotificationContent(context));
        bigText.setBigContentTitle("सोशल मीडियावर पाठवण्यासाठी सुंदर स्टेटस");
        bigText.setSummaryText(context.getResources().getString(R.string.status));
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_notification);
        mBuilder.setContentTitle("सोशल मीडियावर पाठवण्यासाठी सुंदर स्टेटस");
        mBuilder.setContentText(SharedPref.getStatusNotificationContent(context));
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);
        mBuilder.setAutoCancel(true);
        mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "3";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(3, mBuilder.build());


    }

    public static void createNotificationGallery(Context context) {
        NotificationManager mNotificationManager;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, "notify_004");
        Intent ii = new Intent(context, PhotoGalleryActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, 0);
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(SharedPref.getGalleryNotificationContent(context));
        bigText.setBigContentTitle("छत्रपती शिवाजी महाराज फोटोंचा अप्रतिम संग्रह");
        bigText.setSummaryText(context.getResources().getString(R.string.photo_gallery));
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_notification);
        mBuilder.setContentTitle("छत्रपती शिवाजी महाराज फोटोंचा अप्रतिम संग्रह");
        mBuilder.setContentText(SharedPref.getGalleryNotificationContent(context));
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);
        mBuilder.setAutoCancel(true);
        mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "4";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(4, mBuilder.build());


    }

    public static void createNotificationShivcharitra(Context context) {
        NotificationManager mNotificationManager;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, "notify_001");
        Intent ii = new Intent(context, ShivCharitraActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, 0);
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(SharedPref.getShivCharitraNotificationContent(context));
        bigText.setBigContentTitle(SharedPref.getShivCharitraNotificationTitle(context));
        bigText.setSummaryText(context.getResources().getString(R.string.shivcharitra));
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_notification);
        mBuilder.setContentTitle(SharedPref.getShivCharitraNotificationTitle(context));
        mBuilder.setContentText(SharedPref.getShivCharitraNotificationContent(context));
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);
        mBuilder.setAutoCancel(true);
        mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "1";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(1, mBuilder.build());


    }

    public static void createNotificationBooks(Context context) {
        NotificationManager mNotificationManager;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, "notify_002");
        Intent ii = new Intent(context, BookActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, 0);
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(SharedPref.getBooksNotificationContent(context));
        bigText.setBigContentTitle("नवीन पुस्तके जोडली आहेत.");
        bigText.setSummaryText(context.getResources().getString(R.string.books));
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_notification);
        mBuilder.setContentTitle("नवीन पुस्तके जोडली आहेत.");
        mBuilder.setContentText(SharedPref.getBooksNotificationContent(context));
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);
        mBuilder.setAutoCancel(true);
        mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "2";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(2, mBuilder.build());


    }


    public static void createNotificationHistory(Context context) {
        NotificationManager mNotificationManager;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, "notify_005");
        Intent ii = new Intent(context, HistoryActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, 0);
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(SharedPref.getHistoryNotificationContent(context));
        bigText.setBigContentTitle(SharedPref.getHistoryNotificationTitle(context));
        bigText.setSummaryText(context.getResources().getString(R.string.history));
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_notification);
        mBuilder.setContentTitle(SharedPref.getHistoryNotificationTitle(context));
        mBuilder.setContentText(SharedPref.getHistoryNotificationContent(context));
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);
        mBuilder.setAutoCancel(true);
        mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "5";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(5, mBuilder.build());


    }


    public static void createNotificationRajmudra(Context context) {
        NotificationManager mNotificationManager;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, "notify_007");
        Intent ii = new Intent(context, RajmudraActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, 0);
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(SharedPref.getRajmudraNotificationContent(context));
        bigText.setBigContentTitle("|| राजमुद्रा || ");
        bigText.setSummaryText(context.getResources().getString(R.string.rajmudra));
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_notification);
        mBuilder.setContentTitle("|| राजमुद्रा || ");
        mBuilder.setContentText(SharedPref.getRajmudraNotificationContent(context));
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);
        mBuilder.setAutoCancel(true);
        mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "7";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(7, mBuilder.build());


    }

    public static void createFirebaseNotification(Context context,String Message) {
        NotificationManager mNotificationManager;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, "notify_007");
        Intent ii = new Intent(context, HomePageActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, 0);
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(Message);
        bigText.setBigContentTitle(context.getResources().getString(R.string.app_name));
        bigText.setSummaryText(context.getResources().getString(R.string.app_name));
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_notification);
        mBuilder.setContentTitle(context.getResources().getString(R.string.app_name));
        mBuilder.setContentText(Message);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);
        mBuilder.setAutoCancel(true);
        mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "8";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(8, mBuilder.build());


    }


}
