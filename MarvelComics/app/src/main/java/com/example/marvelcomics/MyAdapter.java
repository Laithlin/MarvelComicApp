package com.example.marvelcomics;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    String titles[], descrptions[];
    String[] imgs;
    Context context;

    public MyAdapter (Context ct, String[] title, String[] description, String[] images){
        context = ct;
        titles = title;
        descrptions = description;
        imgs = images;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.titleView.setText(titles[position]);
        holder.descView.setText(descrptions[position]);
        holder.coverView.setImageDrawable(Drawable.createFromPath(imgs[position]));
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleView, descView;
        ImageView coverView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.comic_title);
            descView = itemView.findViewById(R.id.comic_description);
            coverView = itemView.findViewById(R.id.comic_cover);
        }

        public void getFilter() {

        }
    }


}
