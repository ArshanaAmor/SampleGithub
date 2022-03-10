package com.arshana.raje.newActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.arshana.raje.Constant.Constant;
import com.arshana.raje.R;

import java.util.HashMap;
import java.util.Locale;

public class RajmudraActivity extends AppCompatActivity {

    TextView txt_rajmudra;
    ImageView img_back;
    TextToSpeech tts;
    String rajmudra_content = "संस्कृत \n\n“प्रतिपच्चंद्रलेखेव वर्धिष्णुर्विश्ववंदिता शाहसुनोः शिवस्यैषा मुद्रा भद्राय राजते।”\n\n\n" +
            "" +
            "मराठी    \n\nप्रतिपदेचा चन्द्र जसा वाढत जातो, आणि सरे विश्व त्याला जसे वंदन करते, तशीच तशीच ही मुद्रा व् तिचा लौकिक वाढत जाईल…..!\n\n\n";

    String toSpeak = "संस्कृत “प्रतिपच्चंद्रलेखेव वर्धिष्णुर्विश्ववंदिता शाहसुनोः शिवस्यैषा मुद्रा भद्राय राजते।” मराठी प्रतिपदेचा चन्द्र जसा वाढत जातो, आणि सरे विश्व त्याला जसे वंदन करते, तशीच तशीच ही मुद्रा व् तिचा लौकिक वाढत जाईल…..!";
    HashMap<String, String> ttsParams;
    AlertDialog.Builder builderSingle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rajmudra);

        init();
    }


    private void init() {
        txt_rajmudra = findViewById(R.id.txt_rajmudra);
        ImageView img_back;
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        txt_rajmudra.setText(rajmudra_content);
        ImageView img_font_size = findViewById(R.id.img_font_size);
        ImageView img_audio = findViewById(R.id.img_audio);
        ImageView img_share = findViewById(R.id.img_share);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        img_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts = new TextToSpeech(RajmudraActivity.this, new TextToSpeech.OnInitListener() {

                    @Override
                    public void onInit(int status) {
                        Locale loc = new Locale("mr", "IN");
                        tts.setLanguage(loc);
                        ttsParams = new HashMap<String, String>();
                        ttsParams.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,
                                RajmudraActivity.this.getPackageName());
                        tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, ttsParams);
                        tts.setOnUtteranceCompletedListener(new TextToSpeech.OnUtteranceCompletedListener() {

                            @Override
                            public void onUtteranceCompleted(final String utteranceId) {


                            }
                        });

                    }
                });


            }
        });


        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uriUrl = Uri.parse("whatsapp://send?text=" + Constant.PLAYSTORE_URL + rajmudra_content + "");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                try {
                    startActivity(launchBrowser);

                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "Whatsapp is not installed on this device", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builderSingle = new AlertDialog.Builder(RajmudraActivity.this);
        builderSingle.setTitle("फॉन्ट आकार निवडा");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(RajmudraActivity.this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("१४");
        arrayAdapter.add("१६");
        arrayAdapter.add("१८");
        arrayAdapter.add("२०");
        arrayAdapter.add("२२");

        builderSingle.setNegativeButton("रद्द करा\n", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int size = 12;

                switch (which) {
                    case 0:
                        size = 14;
                        break;
                    case 1:
                        size = 16;
                        break;
                    case 2:
                        size = 18;
                        break;
                    case 3:
                        size = 20;
                        break;
                    case 4:
                        size = 22;
                        break;
                }

                txt_rajmudra.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);

                dialog.dismiss();

            }
        });
        img_font_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tts != null && tts.isSpeaking()) {
                    AlertDialog.Builder builderSingle1 = new AlertDialog.Builder(RajmudraActivity.this);
                    builderSingle1.setTitle("ऑडिओ चालू आपण आता फॉन्ट बदलू शकत नाही");
                    builderSingle1.setPositiveButton("ठीक आहे", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builderSingle1.show();
                } else {
                    builderSingle.show();

                }


            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (tts != null) {
            tts.shutdown();
        }
        finish();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (tts != null) {
            tts.shutdown();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tts != null) {
            tts.shutdown();
        }
    }
}
