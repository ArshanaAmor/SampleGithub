package com.arshana.raje.Adapter;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.arshana.raje.Constant.API;
import com.arshana.raje.Constant.Constant;
import com.arshana.raje.Model.WallpaperModel;
import com.arshana.raje.R;
import com.arshana.raje.newActivity.FortActivity;
import com.arshana.raje.sharedPref.SharedPref;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class PhotoGalleryAdapter extends RecyclerView.Adapter<PhotoGalleryAdapter.VideoGalleryHolder> {
    private ArrayList<WallpaperModel> wallpaperModels;
    private Context context;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    NotificationCompat.BigTextStyle bigText;
    Intent intent;
    String outputFilePath;
    File directory;

    public PhotoGalleryAdapter(ArrayList<WallpaperModel> wallpaperModels, Context context) {
        this.wallpaperModels = wallpaperModels;
        this.context = context;
    }

    class VideoGalleryHolder extends RecyclerView.ViewHolder {
        ImageView img_view, img_whatsapp, img_facebook, img_instgram, img_download;

        VideoGalleryHolder(View itemView) {
            super(itemView);
            img_view = itemView.findViewById(R.id.img_view);
            img_whatsapp = itemView.findViewById(R.id.img_whatsapp);
            img_facebook = itemView.findViewById(R.id.img_facebook);
            img_instgram = itemView.findViewById(R.id.img_instgram);
            img_download = itemView.findViewById(R.id.img_download);
        }
    }

    @Override
    public VideoGalleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_adapter, parent, false);
        return new VideoGalleryHolder(view);
    }

    @Override
    public void onBindViewHolder(final VideoGalleryHolder holder, final int position) {
        String imagePath = API.IMAGE_PATH + wallpaperModels.get(position).getWallpaper();
//        Picasso.with(context).load(imagePath).fit().centerCrop().into(holder.img_view);
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(imagePath).apply(requestOptions).into(holder.img_view);

        holder.img_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imageurl = API.IMAGE_PATH + wallpaperModels.get(position).getWallpaper();
                InputStream in = null;
                try {
                    URL url = new URL(imageurl);
                    URLConnection urlConn = url.openConnection();
                    HttpURLConnection httpConn = (HttpURLConnection) urlConn;
                    httpConn.connect();

                    in = httpConn.getInputStream();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap loadedImage = BitmapFactory.decodeStream(in);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, Constant.PLAYSTORE_URL);
                String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), loadedImage, "", null);
                Uri screenshotUri = Uri.parse(path);
                intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                intent.setPackage("com.whatsapp");
                intent.setType("image/*");
                try {
                    context.startActivity(intent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(context, "Whatsapp is not installed on this device", Toast.LENGTH_SHORT).show();
                }
            }
        });


        holder.img_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap loadedImage = null;
                try {
                    URL url = new URL(API.IMAGE_PATH + wallpaperModels.get(position).getWallpaper());
                    loadedImage = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (IOException e) {
                }
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, Constant.PLAYSTORE_URL);
                String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), loadedImage, "", null);
                Uri screenshotUri = Uri.parse(path);
                intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                intent.setPackage("com.facebook.katana");
                intent.setType("image/*");
                try {
                    context.startActivity(intent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(context, "Facebook is not installed on this device", Toast.LENGTH_SHORT).show();
                }
            }
        });


        holder.img_instgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap loadedImage = null;
                try {
                    URL url = new URL(API.IMAGE_PATH + wallpaperModels.get(position).getWallpaper());
                    loadedImage = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (IOException e) {
                }
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, Constant.PLAYSTORE_URL);
                String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), loadedImage, "", null);
                Uri screenshotUri = Uri.parse(path);
                intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                intent.setPackage("com.instagram.android");
                intent.setType("image/*");
                try {
                    context.startActivity(intent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(context, "Instagram is not installed on this device", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.img_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = API.IMAGE_PATH + wallpaperModels.get(position).getWallpaper();

                directory = new File(Environment.getExternalStorageDirectory() + File.separator + "Chatrapati Shivaji Maharaj");
                directory.mkdirs();


                outputFilePath = directory + File.separator + "Raje_" + url.substring(url.lastIndexOf("/") + 1);
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("file://" + outputFilePath), "image/*");


                final DownloadTask downloadTask = new DownloadTask(context);
                downloadTask.execute(url);


            }
        });
    }

    @Override
    public int getItemCount() {
        return wallpaperModels.size();
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream(outputFilePath);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mBuilder =
                    new NotificationCompat.Builder(context, "notify_100");

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            bigText = new NotificationCompat.BigTextStyle();
            bigText.bigText("Download in progress");
            bigText.setBigContentTitle("File Download");
            bigText.setSummaryText(context.getResources().getString(R.string.photo_gallery));
            mBuilder.setContentIntent(pendingIntent);
            mBuilder.setSmallIcon(R.drawable.ic_notification);
            mBuilder.setContentTitle("File Download");
            mBuilder.setContentText("Download in progress");
            mBuilder.setPriority(Notification.PRIORITY_MAX);
            mBuilder.setStyle(bigText);
            mBuilder.setAutoCancel(false);
            mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String channelId = "100";
                NotificationChannel channel = new NotificationChannel(
                        channelId,
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_HIGH);
                mNotificationManager.createNotificationChannel(channel);
                mBuilder.setChannelId(channelId);
            }
            mBuilder.setProgress(100, 10, true);

            mNotificationManager.notify(100, mBuilder.build());


        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);

        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            if (result != null) {

                mBuilder.setContentText("फाईल डाउनलोड करण्यात अयशस्वी");
                mBuilder.setProgress(0, 0, false);
                bigText.bigText("फाईल डाउनलोड करण्यात अयशस्वी");
                mBuilder.setAutoCancel(true);
                mNotificationManager.notify(100, mBuilder.build());
            } else {

                mBuilder.setContentText("डाउनलोड पूर्ण झाले");
                mBuilder.setProgress(0, 0, false);
                bigText.bigText("डाउनलोड पूर्ण झाले");
                mBuilder.setAutoCancel(true);
                mNotificationManager.notify(100, mBuilder.build());

            }
        }
    }


}

