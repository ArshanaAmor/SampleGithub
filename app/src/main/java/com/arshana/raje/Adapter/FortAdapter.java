
package com.arshana.raje.Adapter;

import android.content.Context;
import android.content.Intent;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arshana.raje.Constant.API;
import com.arshana.raje.Constant.Constant;
import com.arshana.raje.Model.FortModel;
import com.arshana.raje.R;
import com.arshana.raje.newActivity.ViewFortActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FortAdapter extends RecyclerView.Adapter<FortAdapter.VideoGalleryHolder> {
    ArrayList<FortModel>statuslist;
    Context context;
    public FortAdapter(ArrayList<FortModel>statuslist, Context context) {
        this.statuslist = statuslist;
        this.context = context;
    }


    class VideoGalleryHolder extends RecyclerView.ViewHolder {
        CardView crd_povade;
        TextView img_view;
        ImageView img_fort;


        public VideoGalleryHolder(View itemView) {

            super(itemView);
            img_view = (TextView) itemView.findViewById(R.id.img_view);
            crd_povade = (CardView) itemView.findViewById(R.id.crd_status);
            img_fort =  itemView.findViewById(R.id.img_fort);


        }
    }

    @Override
    public VideoGalleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.povade_adapter, parent, false);

        return new VideoGalleryHolder(view);
    }

    @Override
    public void onBindViewHolder(final VideoGalleryHolder holder, final int position) {


        holder.img_view.setText(statuslist.get(position).getName());
        Picasso.with(context).load(API.FORT_PATH + statuslist.get(position).getImage()).into(holder.img_fort);

        holder.crd_povade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.fort_position=position;
                Intent intent = new Intent(context, ViewFortActivity.class);
                intent.putExtra("title",statuslist.get(position).getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return statuslist.size();
    }


}
