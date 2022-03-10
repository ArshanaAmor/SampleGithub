package com.arshana.raje.newActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.arshana.raje.R;
import com.squareup.picasso.Picasso;

public class SplashActivity extends AppCompatActivity {
    String id;
    int SPLASH_TIME_OUT = 1500;
    ImageView img_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        img_splash = findViewById(R.id.img_splash);
        Picasso.with(getApplicationContext()).load(R.drawable.splash).into(img_splash);
        getSplashscreens();
    }
    public void getSplashscreens() {

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Thread thread = new Thread() {
                    public void run() {
                        try {
                            sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {

                            Intent i = new Intent(SplashActivity.this, HomePageActivity.class);
                            startActivity(i);
                            finish();
                        }

                    }
                };
                thread.start();
            }
        }, SPLASH_TIME_OUT);

    }

}
