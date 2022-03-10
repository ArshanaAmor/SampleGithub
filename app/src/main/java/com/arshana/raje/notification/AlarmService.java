package com.arshana.raje.notification;

import android.annotation.SuppressLint;
import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * Created by Darshana Chorat on 2020-05-04.
 */
public class AlarmService extends JobService {
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        String notificationType = intent.getStringExtra("type");
//        if (notificationType.equalsIgnoreCase("Rajmudra")) {
//            NotiManager.createNotificationRajmudra(this);
//
//        } else if (notificationType.equalsIgnoreCase("Shivcharitra")) {
//            NotiManager.createNotificationShivcharitra(this);
//
//        } else if (notificationType.equalsIgnoreCase("History")) {
//            NotiManager.createNotificationHistory(this);
//
//        } else if (notificationType.equalsIgnoreCase("Fort")) {
//            NotiManager.createNotificationFort(this);
//
//        } else if (notificationType.equalsIgnoreCase("Gallery")) {
//            NotiManager.createNotificationGallery(this);
//
//        } else if (notificationType.equalsIgnoreCase("Status")) {
//            NotiManager.createNotificationStatus(this);
//
//        } else if (notificationType.equalsIgnoreCase("Book")) {
//            NotiManager.createNotificationBooks(this);
//
//        }
//        return START_STICKY;
//    }

//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
//        String notificationType = intent.getStringExtra("type");
//        if (notificationType.equalsIgnoreCase("Rajmudra")) {
            NotiManager.createNotificationRajmudra(this);

//        } else if (notificationType.equalsIgnoreCase("Shivcharitra")) {
//            NotiManager.createNotificationShivcharitra(this);
//
//        } else if (notificationType.equalsIgnoreCase("History")) {
//            NotiManager.createNotificationHistory(this);
//
//        } else if (notificationType.equalsIgnoreCase("Fort")) {
//            NotiManager.createNotificationFort(this);
//
//        } else if (notificationType.equalsIgnoreCase("Gallery")) {
//            NotiManager.createNotificationGallery(this);
//
//        } else if (notificationType.equalsIgnoreCase("Status")) {
//            NotiManager.createNotificationStatus(this);
//
//        } else if (notificationType.equalsIgnoreCase("Book")) {
//            NotiManager.createNotificationBooks(this);
//
//        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
