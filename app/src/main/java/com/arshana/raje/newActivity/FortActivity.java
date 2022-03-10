package com.arshana.raje.newActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
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

import com.arshana.raje.Adapter.FortAdapter;
import com.arshana.raje.Constant.API;
import com.arshana.raje.Model.FortModel;
import com.arshana.raje.R;
import com.arshana.raje.data.Constant;
import com.arshana.raje.data.InitViews;
import com.facebook.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FortActivity extends AppCompatActivity {
    RecyclerView recyclerPhotoGallery;
    FortAdapter photoGalleryAdapter;
    Context context = this;
    ProgressDialog progressDialog=null;
    public static ArrayList<FortModel> statusList = new ArrayList<>();
    String code,message;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fort);
        LinearLayout adContainer = findViewById(R.id.banner_container);
        adView = InitViews.initBanner(getApplicationContext(), adContainer, Constant.FORT_BANNER);

        init();

        getStatusAPI();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }
    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
    private void getStatusAPI() {


        if(API.isNetworkConnected(FortActivity.this))
        {
            progressDialog.show();
            recyclerPhotoGallery.setVisibility(View.VISIBLE);
//            lnr_net_connection.setVisibility(View.GONE);
            statusList.clear();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, API.getFortAPI, new Response.Listener<String>() {
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
                                FortModel statusModel = new FortModel();
                                statusModel.setId(jsonObject1.getString("id"));
                                statusModel.setName(jsonObject1.getString("name"));
                                statusModel.setInfo(jsonObject1.getString("info"));
                                statusModel.setHistory(jsonObject1.getString("history"));
                                statusModel.setHowtoreach(jsonObject1.getString("how_to_reach"));
                                statusModel.setImage(jsonObject1.getString("image"));
                                statusList.add(statusModel);
                            }
                            photoGalleryAdapter = new FortAdapter(statusList, context);
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


        }
        else {
            AlertDialog.Builder builderSingle1 = new AlertDialog.Builder(FortActivity.this);
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
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        recyclerPhotoGallery = (RecyclerView) findViewById(R.id.recyclerVideoGallery);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerPhotoGallery.setLayoutManager(gridLayoutManager);
        recyclerPhotoGallery.setItemAnimator(new DefaultItemAnimator());
        ImageView back = (ImageView) findViewById(R.id.img_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        TextView toolbarsave =  findViewById(R.id.txt_tool_title);
        toolbarsave.setText(getApplicationContext().getResources().getString(R.string.fort));


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

}

