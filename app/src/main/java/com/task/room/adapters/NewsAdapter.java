package com.task.room.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;


import com.task.room.FavNews;
import com.task.room.FavNewsDao;
import com.task.room.Main2Activity;
import com.task.room.NewsRepository;
import com.task.room.NoteViewModel;
import com.task.room.OnItemClick;
import com.task.room.R;
import com.task.room.fragments.NewsFragment;
import com.task.room.model.NewsArticle;
import com.task.room.model.NewsResponse;
import com.task.room.model.Result;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    Context context;
    ArrayList<Result> articles;
    NoteViewModel noteViewModel;


    public NewsAdapter(Context context, ArrayList<Result> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_fragment_items, parent, false);
        return new  NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, final int position) {
        holder.tvName.setText(articles.get(position).getWebTitle());
        holder.tvDesCription.setText(articles.get(position).getSectionName());





        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Main2Activity.class);
                intent.putExtra("url",articles.get(position).getWebUrl());

                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{

        TextView tvName;
        TextView tvDesCription;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvDesCription = itemView.findViewById(R.id.tvDesCription);


        }
    }
}