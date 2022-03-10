package com.arshana.raje.Constant;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class Constant {
    static TextToSpeech t1;
    public static int fort_position;
    public static String PLAYSTORE_URL = "\n\n"+"*"+"छत्रपती शिवाजी महाराजांबद्दल अधिक जाणून घेण्यासाठी डाउनलोड करायला विसरू नका"+"*"+"\n\nhttps://play.google.com/store/apps/details?id=com.arshana.raje\n\n";
    public static String PLAYSTORE_LINK = "https://play.google.com/store/apps/details?id=com.arshana.raje";

    public static TextToSpeech TTS(Context context) {
        t1 = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    Locale loc = new Locale("mr", "IN");

                    t1.setLanguage(loc);

                    t1.setOnUtteranceCompletedListener(new TextToSpeech.OnUtteranceCompletedListener() {

                        @Override
                        public void onUtteranceCompleted(final String utteranceId) {


                        }
                    });
                }
            }
        });
        return t1;

    }

    public static void Speak(TextToSpeech t1, String toSpeak) {
        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }
}

