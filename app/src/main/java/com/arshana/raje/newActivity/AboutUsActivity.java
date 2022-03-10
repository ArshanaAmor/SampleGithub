package com.arshana.raje.newActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.arshana.raje.R;

public class AboutUsActivity extends AppCompatActivity {
    ImageView img_back;
    TextView txt_title;
    LinearLayout lnr_whatsapp, lnr_mail, lnr_website;
    EditText edt_whatsapp, edt_gmail, edt_website;
    ImageView img_whatsapp, img_gmail, img_website;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        lnr_whatsapp = findViewById(R.id.lnr_whatsapp);
        edt_whatsapp = findViewById(R.id.edt_whatsapp);
        edt_website = findViewById(R.id.edt_website);
        edt_gmail = findViewById(R.id.edt_gmail);
        img_gmail = findViewById(R.id.img_gmail);
        img_website = findViewById(R.id.img_website);
        img_whatsapp = findViewById(R.id.img_whatsapp);


        lnr_mail = findViewById(R.id.lnr_mail);
        lnr_website = findViewById(R.id.lnr_website);
        img_back = findViewById(R.id.img_back);
        txt_title = findViewById(R.id.txt_tool_title);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        txt_title.setText("आमच्याबद्दल");

        lnr_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsapp();

            }
        });
        edt_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsapp();

            }
        });
        img_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsapp();

            }
        });
        lnr_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMail();
            }
        });
        lnr_website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebsite();
            }
        });
        img_website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebsite();
            }
        });
        edt_website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebsite();
            }
        });
        img_gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMail();
            }
        });
        edt_gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMail();
            }
        });
    }


    public void openWhatsapp() {
        intent = new Intent("android.intent.action.MAIN");
        intent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
        intent.putExtra("jid", PhoneNumberUtils.stripSeparators("919326896183") + "@s.whatsapp.net");//phone number without "+" prefix
        try {
            startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(AboutUsActivity.this, "Whatsapp not installed in your mobile", Toast.LENGTH_SHORT).show();
        }
    }

    public void openMail() {

        intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("message/rfc822");
        intent.setData(Uri.parse("mailto:"));
//            intent.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
//            intent.putExtra(Intent.EXTRA_TEXT   , "body of email");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"arshanadevelopers@gmail.com"});
        try {
            startActivity(Intent.createChooser(intent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(AboutUsActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void openWebsite() {

        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://arshana.tk"));
        startActivity(intent);

    }
}
