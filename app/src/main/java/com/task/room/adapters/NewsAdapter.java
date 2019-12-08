package com.task.room.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
import com.task.room.viewModels.FavNewsDatabase;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    Context context;
    ArrayList<Result> articles;
    OnItemClick onItemClick;
    List<FavNews> favNews;


    public NewsAdapter(Context context, ArrayList<Result> articles ,OnItemClick onItemClick) {
        this.context = context;
        this.articles = articles;
        this.onItemClick = onItemClick;
    }




    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_fragment_items, parent, false);
        return new  NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder holder, final int position) {
        holder.tvName.setText(articles.get(position).getWebTitle());
        holder.tvDesCription.setText(articles.get(position).getSectionName());
            FavNews  favNews = new FavNews();

//        if(FavNewsDatabase.getInstance(context).noteDao().isFavorite(articles.get(position).getWebTitle().equals("")))
//        {
//
//            holder.fav_btn.setImageDrawable(ContextCompat.getDrawable(context, android.R.drawable.btn_star_big_on));
//        }else
//         {
//            holder.fav_btn.setImageDrawable(ContextCompat.getDrawable(context, android.R.drawable.alert_dark_frame));
//        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Main2Activity.class);
                intent.putExtra("url",articles.get(position).getWebUrl());

                context.startActivity(intent);
            }
        });
        holder.fav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    onItemClick.onClick(articles.get(position).getWebTitle(), articles.get(position).getSectionName(), articles.get(position).getWebUrl());
                    holder.fav_btn.setImageDrawable(ContextCompat.getDrawable(context, android.R.drawable.btn_star_big_on));

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
        ImageButton fav_btn;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvDesCription = itemView.findViewById(R.id.tvDesCription);
            fav_btn = itemView.findViewById(R.id.favorite_button);



        }
    }

}