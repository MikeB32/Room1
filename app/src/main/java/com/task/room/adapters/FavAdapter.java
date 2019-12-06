package com.task.room.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.task.room.FavNews;
import com.task.room.R;
import com.task.room.model.Result;

import java.util.ArrayList;
import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavAdapterViewHolder> {

    List<FavNews> favNews = new ArrayList<>();



    @NonNull
    @Override
    public FavAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_fragment_items, parent, false);

        return new FavAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavAdapterViewHolder holder,  int position) {

        FavNews favNews2 = favNews.get(position);
            holder.tvName.setText(favNews2.getTitle());
            holder.tvDesCription.setText(favNews2.getDescription());
    }


    @Override
    public int getItemCount() {
        return favNews.size();
    }

    public void setNotes(List<FavNews> favNews){

        this.favNews = favNews ;

        notifyDataSetChanged();
    }

    public class FavAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView tvName;
        TextView tvDesCription;

        public FavAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName2);
            tvDesCription = itemView.findViewById(R.id.tvDesCription2);


        }
    }


}
