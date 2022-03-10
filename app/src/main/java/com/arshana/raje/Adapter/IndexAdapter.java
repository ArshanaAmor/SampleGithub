
package com.arshana.raje.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.arshana.raje.R;

public class IndexAdapter extends RecyclerView.Adapter<IndexAdapter.VideoGalleryHolder> {
    private String[] id;
    private Context context;
    private String[] titles;
    private ViewPager myViewPager;
    DrawerLayout drawer_layout;


    public IndexAdapter(String[] id, Context context, String[] titles, ViewPager myViewPager, DrawerLayout drawer_layout) {
        this.id = id;
        this.drawer_layout = drawer_layout;
        this.context = context;
        this.titles = titles;
        this.myViewPager = myViewPager;
    }

    class VideoGalleryHolder extends RecyclerView.ViewHolder {
        TextView txt_title;
        public VideoGalleryHolder(View itemView) {
            super(itemView);
            txt_title =  itemView.findViewById(R.id.txt_title);
        }
    }

    @Override
    public VideoGalleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.indexadapter, parent, false);
        return new VideoGalleryHolder(view);
    }
    @Override
    public void onBindViewHolder(final VideoGalleryHolder holder, final int position) {
        holder.txt_title.setText(titles[holder.getAdapterPosition()]);
        holder.txt_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myViewPager.setCurrentItem(holder.getAdapterPosition());
                drawer_layout.closeDrawer(GravityCompat.END);

            }
        });
    }



    @Override
    public int getItemCount() {
        return titles.length;
    }


}
