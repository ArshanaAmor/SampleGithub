
package com.arshana.raje.Adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arshana.raje.Constant.Constant;
import com.arshana.raje.Model.StatusModel;
import com.arshana.raje.R;

import java.util.ArrayList;
import java.util.Locale;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.VideoGalleryHolder> {
    ArrayList<StatusModel> statusList;
    Context context;
    TextToSpeech t1;

    public StatusAdapter(ArrayList<StatusModel> statusList, Context context, TextToSpeech t1) {
        this.statusList = statusList;
        this.context = context;
        this.t1 = t1;
    }


    class VideoGalleryHolder extends RecyclerView.ViewHolder {
        CardView crd_status;
        TextView img_view;
        ImageView img_share, img_copy, img_mic;

        public VideoGalleryHolder(View itemView) {

            super(itemView);
            img_view = itemView.findViewById(R.id.img_view);
            crd_status = itemView.findViewById(R.id.crd_status);
            img_share = itemView.findViewById(R.id.img_share);
            img_mic = itemView.findViewById(R.id.img_mic);
            img_copy = itemView.findViewById(R.id.img_copy);


        }


    }

    @Override
    public VideoGalleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.status_adapter, parent, false);

        return new VideoGalleryHolder(view);
    }

    @Override
    public void onBindViewHolder(final VideoGalleryHolder holder, final int position) {


        holder.img_view.setText(statusList.get(position).getStatus());

        holder.img_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("message", Constant.PLAYSTORE_URL + statusList.get(position).getStatus());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "मजकूर कॉपी केला", Toast.LENGTH_SHORT).show();

            }
        });
        holder.img_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toSpeak = statusList.get(position).getStatus();
                Constant.Speak(t1, toSpeak);

            }
        });
        holder.img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uriUrl = Uri.parse("whatsapp://send?text=" + Constant.PLAYSTORE_URL + statusList.get(position).getStatus() + "");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                try {
                    context.startActivity(launchBrowser);

                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(context, "Whatsapp is not installed on this device", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }


}
