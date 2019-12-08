package com.task.room.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.task.room.FavNews;
import com.task.room.Main2Activity;
import com.task.room.NoteViewModel;
import com.task.room.OnItemClick;
import com.task.room.R;
import com.task.room.model.Result;
import com.task.room.viewModels.FavNewsDatabase;




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavAdapterViewHolder> {

    List<FavNews> favNews = new ArrayList<>();
    Context context;
    FavNews favNewsItem;
    NoteViewModel noteViewModel;
    String test;
    String xx;
    FavNews favNews7;

    public FavAdapter(Context context,NoteViewModel noteViewModel) {
        this.context = context;
        this.noteViewModel = noteViewModel;

    }

    @NonNull
    @Override
    public FavAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_fragment_items, parent, false);

        return new FavAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavAdapterViewHolder holder, final int position) {

         favNewsItem = favNews.get(position);
            holder.tvName.setText(favNewsItem.getTitle());
            holder.tvDesCription.setText(favNewsItem.getDescription());

            holder.offlineBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Runnable myRunnable = createRunnable(favNewsItem.getUrl());

                    myRunnable.run();
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    FavNews favNews = new FavNews();
//                    String xx =favNews.getOfflineData();
//                    Log.e("what",favNews.getOfflineData());
                    Intent intent = new Intent(context, Main2Activity.class);
                    if(favNewsItem.getUrl()!=null) {
                        intent.putExtra("url", favNewsItem.getUrl());
                        Log.e("url1", favNewsItem.getUrl());
                    }if(xx!=null) {
                        SharedPreferences sharedPreferences =context.getSharedPreferences("aa",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("data",xx);
                        editor.putString("newUrl",test);

                        editor.apply();
//                        intent.putExtra("data", image64);
//                        Log.e("data1", xx);
                    }
                    context.startActivity(intent);
                }
            });
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
        ImageButton offlineBtn;

        public FavAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName2);
            tvDesCription = itemView.findViewById(R.id.tvDesCription2);
            offlineBtn = itemView.findViewById(R.id.offline_button);


        }
    }

    public static String getHtml(String url) throws IOException {


        // Build and set timeout values for the request.
        URLConnection connection = (new URL(url)).openConnection();
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.connect();

        // Read and store the result line by line then return the entire string.
        InputStream in = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder html = new StringBuilder();
        for (String line; (line = reader.readLine()) != null; ) {
            html.append(line);
        }
        in.close();

        return html.toString();
    }

    private Runnable createRunnable(final String url){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        Runnable aRunnable = new Runnable(){
            public void run() {
                try {
                    test  = url;
                    xx =  getHtml(url);
                     favNews7 = new FavNews();
                    favNews7.setOfflineData(xx);
                    FavNewsDatabase.getInstance(context).noteDao().offlineDate(favNews7);
//                    favNews.getOfflineData();
                   Log.e("hell",xx);
                    Log.e("hell2",favNews7.getOfflineData());

                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        };

        return aRunnable;

    }
}
