package com.example.marvelcomics;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<String> titles, descrptions;
    ArrayList<String> imgs;
    Context context;
    String searchResult = "";

    public MyAdapter (Context ct, ArrayList<String> title, ArrayList<String> description, ArrayList<String> images){
        context = ct;
        titles = title;
        descrptions = description;
        imgs = images;

    }

    public void update (ArrayList<String> title, ArrayList<String> desc, ArrayList<String> img){
        titles = new ArrayList<String>();
        descrptions = new ArrayList<String>();
        imgs = new ArrayList<String>();

        Log.e("wielkosc", String.valueOf(title.size()));

        if (title.size() == 0){
            searchResult = "There's no such comic....";
            titles = title;
            descrptions = desc;
            imgs = img;
        }
        else{
            titles = title;
            descrptions = desc;
            imgs = img;
            searchResult = "";
        }

        notifyDataSetChanged();
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
        holder.titleView.setText(titles.get(position));
        holder.descView.setText(descrptions.get(position));
        holder.searchView.setText(searchResult);
        holder.coverView.setImageDrawable(Drawable.createFromPath(imgs.get(position)));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleView, descView, searchView;
        ImageView coverView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.comic_title);
            descView = itemView.findViewById(R.id.comic_description);
            coverView = itemView.findViewById(R.id.comic_cover);
            searchView = itemView.findViewById(R.id.search);
        }

        public void getFilter() {

        }
    }


}
