package com.arshana.raje.newActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.arshana.raje.Adapter.StatusAdapter;
import com.arshana.raje.Constant.API;
import com.arshana.raje.Constant.Constant;
import com.arshana.raje.Model.StatusModel;
import com.arshana.raje.R;
import com.arshana.raje.data.InitViews;
import com.facebook.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StatusActivity extends AppCompatActivity {
    RecyclerView recyclerPhotoGallery;
    StatusAdapter photoGalleryAdapter;
    ProgressDialog progressDialog = null;
    Context context = this;
    Button btn_try_again;
    ArrayList<StatusModel> statusList = new ArrayList<>();
    ImageView img_add;
    String code, message;
    LinearLayout lnr_net_connection;

    TextToSpeech tts;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        LinearLayout adContainer = findViewById(R.id.banner_container);
        adView = InitViews.initBanner(getApplicationContext(), adContainer, com.arshana.raje.data.Constant.STATUS_BANNER);

        init();

    }



    private void getStatusAPI() {


        if (API.isNetworkConnected(StatusActivity.this)) {
            progressDialog.show();
            recyclerPhotoGallery.setVisibility(View.VISIBLE);
            lnr_net_connection.setVisibility(View.GONE);
            statusList.clear();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, API.getStatusAPI, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        code = jsonObject.getString("code");
                        message = jsonObject.getString("message");
                        if (code.equals("1")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("list");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                StatusModel statusModel = new StatusModel();
                                statusModel.setId(jsonObject1.getString("id"));
                                statusModel.setStatus(jsonObject1.getString("status"));
                                statusModel.setAdded_date(jsonObject1.getString("added_date"));
                                statusModel.setUpdated(jsonObject1.getString("updated_date"));
                                statusList.add(statusModel);
                            }
                            photoGalleryAdapter = new StatusAdapter(statusList, context, tts);
                            recyclerPhotoGallery.setAdapter(photoGalleryAdapter);
                            progressDialog.dismiss();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        } else {
            androidx.appcompat.app.AlertDialog.Builder builderSingle1 = new AlertDialog.Builder(StatusActivity.this);
            builderSingle1.setMessage(getResources().getString(R.string.net_connection));
            builderSingle1.setTitle(getResources().getString(R.string.net_connection_title));
            builderSingle1.setPositiveButton("ठीक आहे", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            builderSingle1.setCancelable(false);
            builderSingle1.show();
        }

    }


    private void init() {

        recyclerPhotoGallery = findViewById(R.id.recyclerVideoGallery);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerPhotoGallery.setLayoutManager(layoutManager);
        recyclerPhotoGallery.setItemAnimator(new DefaultItemAnimator());
        img_add = findViewById(R.id.img_add);
        lnr_net_connection = findViewById(R.id.lnr_net_connection);
        ImageView back = findViewById(R.id.img_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        TextView toolbarsave = findViewById(R.id.txt_tool_title);
        toolbarsave.setText(getApplicationContext().getResources().getString(R.string.status));
        btn_try_again = findViewById(R.id.btn_try_again);
        btn_try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStatusAPI();
            }
        });
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        tts = Constant.TTS(context);
        getStatusAPI();

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
        if (adView != null) {
            adView.destroy();
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
