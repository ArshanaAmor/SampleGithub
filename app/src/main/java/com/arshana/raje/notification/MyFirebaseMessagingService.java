package com.arshana.raje.notification;

import android.content.Context;
import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            handleNotification(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
            }
        }
    }

    private void handleNotification(String message) {
        sendNotification(message);
    }

    private void handleDataMessage(JSONObject json) {

        try {
            JSONObject data = json.getJSONObject("data");
            String message = data.getString("message");
            sendNotification(message);
        } catch (JSONException ignored) {
        } catch (Exception ignored) {
        }
    }


    public void sendNotification(String message) {
        if (message.equalsIgnoreCase("Book")) {
            NotiManager.createNotificationBooks(getApplicationContext());

        } else if (message.equalsIgnoreCase("Fort")) {
            NotiManager.createNotificationFort(getApplicationContext());

        } else if (message.equalsIgnoreCase("Rajmudra")) {
            NotiManager.createNotificationRajmudra(getApplicationContext());

        } else if (message.equalsIgnoreCase("Shivcharitra")) {
            NotiManager.createNotificationShivcharitra(getApplicationContext());

        } else if (message.equalsIgnoreCase("Status")) {
            NotiManager.createNotificationStatus(getApplicationContext());

        } else if (message.equalsIgnoreCase("History")) {
            NotiManager.createNotificationHistory(getApplicationContext());

        } else if (message.equalsIgnoreCase("Gallery")) {
            NotiManager.createNotificationGallery(getApplicationContext());

        } else {
            NotiManager.createFirebaseNotification(getApplicationContext(), message);

        }

    }

}