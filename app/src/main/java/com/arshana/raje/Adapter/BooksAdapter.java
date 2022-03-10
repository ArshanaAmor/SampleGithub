
package com.arshana.raje.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.arshana.raje.Model.BooksModel;
import com.arshana.raje.R;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.VideoGalleryHolder> {
    private ArrayList<BooksModel> booksModelArrayList;
    private Context context;
    public BooksAdapter(ArrayList<BooksModel> booksModelArrayList, Context context) {
        this.booksModelArrayList = booksModelArrayList;
        this.context = context;
    }


    class VideoGalleryHolder extends RecyclerView.ViewHolder {

        CardView crd_povade;
        TextView img_view;


        VideoGalleryHolder(View itemView) {

            super(itemView);
            img_view =  itemView.findViewById(R.id.img_view);
            crd_povade =  itemView.findViewById(R.id.crd_povade);


        }
    }

    @Override
    public VideoGalleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bookadapter, parent, false);

        return new VideoGalleryHolder(view);
    }

    @Override
    public void onBindViewHolder(final VideoGalleryHolder holder,  int position) {


        holder.img_view.setText(booksModelArrayList.get(position).getBook_name());

        holder.crd_povade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(booksModelArrayList.get(holder.getAdapterPosition()).getBook_writer_name()));
                context.startActivity(browserIntent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return booksModelArrayList.size();
    }


}