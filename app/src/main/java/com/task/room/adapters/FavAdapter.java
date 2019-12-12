package com.task.room.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.task.room.CheckInternet;
import com.task.room.FavNews;
import com.task.room.activities.WebPageActivity;
import com.task.room.viewModels.NewsViewModel;
import com.task.room.R;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavAdapterViewHolder> {

    private List<FavNews> favNews = new ArrayList<>();
    private Context context;
    private NewsViewModel newsViewModel;
    private Intent intent;

    public FavAdapter(Context context, NewsViewModel newsViewModel) {
        this.context = context;
        this.newsViewModel = newsViewModel;

    }

    @NonNull
    @Override
    public FavAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_fragment_items, parent, false);

        return new FavAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavAdapterViewHolder holder, final int position) {


        holder.title.setText(favNews.get(position).getTitle());
        holder.category.setText(favNews.get(position).getDescription());

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    newsViewModel.delete(favNews.get(position));
                }
            });
            holder.offlineBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        if(CheckInternet.isNetwork(context)){
                            new getWebContentAsyncTask(context, newsViewModel,favNews.get(position)).execute(favNews.get(position).getUrl());
                        } else {
                            Toast.makeText(context,"Internet connection is required to save",Toast.LENGTH_SHORT).show();
                        }

                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     intent = new Intent(context, WebPageActivity.class);
                    if (CheckInternet.isNetwork(context)) {
                        intent.putExtra("url", favNews.get(position).getUrl());
                    }else {
                        intent.putExtra("data", favNews.get(position).getId());
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



    private static String getHtml(String url)  {

        HttpResponse response ;
        HttpGet httpGet;
        HttpClient mHttpClient  ;
        String s = "";

        try {

            mHttpClient = new DefaultHttpClient();
            httpGet = new HttpGet(url);

            response = mHttpClient.execute(httpGet);
            s = EntityUtils.toString(response.getEntity(), "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    private static class getWebContentAsyncTask extends AsyncTask<String, Integer, String> {
        private ProgressDialog dialog;

        private NewsViewModel newsViewModel;
        private String offlineHtml;
        private FavNews favNews;
        private Context context;

        private  getWebContentAsyncTask(Context context, NewsViewModel newsViewModel, FavNews favNews) {
            this.newsViewModel = newsViewModel;
            this.favNews = favNews;
            this.context = context;
            dialog = new ProgressDialog(context);

        }
        @Override
        public void onPreExecute() {
            dialog.setMessage("Saving.....");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... url) {

                offlineHtml = getHtml(url[0]);
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            if (dialog.isShowing()) {

                favNews.setOfflineData(offlineHtml);
                newsViewModel.update(favNews);
                dialog.dismiss();
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
                super.onPostExecute(s);
            }
        }
    }

    public class FavAdapterViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView category;
        private ImageButton offlineBtn;
        private ImageButton deleteBtn;
        private FavAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.fav_news_title);
            category = itemView.findViewById(R.id.fav_news_category);
            offlineBtn = itemView.findViewById(R.id.offline_button);
            deleteBtn = itemView.findViewById(R.id.delete_btn);



        }
    }
}
