package com.task.room.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.task.room.FavNews;
import com.task.room.activities.WebPageActivity;
import com.task.room.viewModels.NewsViewModel;
import com.task.room.R;
import com.task.room.model.Result;
import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context context;
    private ArrayList<Result> articles;
    private NewsViewModel newsViewModel;
    private FavNews favNews;

    public NewsAdapter(Context context, ArrayList<Result> articles , NewsViewModel newsViewModel) {
        this.context = context;
        this.articles = articles;
        this.newsViewModel = newsViewModel;
    }


    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_fragment_items, parent, false);
        return new  NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder holder, final int position) {
        holder.title.setText(articles.get(position).getWebTitle());
        holder.section.setText(articles.get(position).getSectionName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebPageActivity.class);
                intent.putExtra("url",articles.get(position).getWebUrl());

                context.startActivity(intent);
            }
        });
        holder.fav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    favNews = new FavNews();
                    String title = articles.get(position).getWebTitle();
                    String section = articles.get(position).getSectionName();
                    String webUrl = articles.get(position).getWebUrl();
                    favNews.setTitle(title);
                    favNews.setDescription(section);
                    favNews.setUrl(webUrl);

                   newsViewModel.insert(favNews);
                   Toast.makeText(context,"Add to favorite",Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public int getItemCount() {
        return articles.size();
    }




    public class NewsViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView section;
        private ImageButton fav_btn;

        private NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.news_title);
            section = itemView.findViewById(R.id.news_category);
            fav_btn = itemView.findViewById(R.id.favorite_button);



        }
    }


}