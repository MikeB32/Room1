package com.task.room.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.task.room.FavNews;
import com.task.room.local.FavNewsDao;
import com.task.room.local.FavNewsDatabase;
import com.task.room.model.NewsResponse;
import com.task.room.networking.NewsApi;
import com.task.room.networking.RetrofitService;
import com.task.room.threads.DefaultExecutorSupplier;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private FavNewsDao favNewsDao;
    private LiveData<List<FavNews>> allNotes;
    private NewsApi newsApi;
    private FavNewsDatabase database;
    public MutableLiveData<NewsResponse> getNews(int pageNo,String key){
        newsApi = RetrofitService.cteateService(NewsApi.class);

        final MutableLiveData<NewsResponse> newsData = new MutableLiveData<>();
        newsApi.getNewsList(pageNo,key).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call,
                                   Response<NewsResponse> response) {

                if (response.isSuccessful()){
                    newsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                newsData.setValue(null);
            }
        });
        return newsData;
    }



    public NewsRepository(Application application) {
         database = FavNewsDatabase.getInstance(application);
        favNewsDao = database.favDao();
        allNotes = favNewsDao.getAllNotes();
    }



    public String singleLoad(final int id) throws ExecutionException, InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(1);
        FutureTask<String> futureTask = new FutureTask<String>(new gettingDatabyId(favNewsDao,id));
        executor.execute(futureTask);

            return futureTask.get();
    }





    public void insert(final FavNews favNews){
        DefaultExecutorSupplier.getInstance().forBackgroundTasks()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        favNewsDao.insert(favNews);
                    }
                });
    }
    public void update(final FavNews favNews){
        DefaultExecutorSupplier.getInstance().forBackgroundTasks()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        favNewsDao.update(favNews);
                    }
                });
    }

    public void delete(final FavNews favNews){
        DefaultExecutorSupplier.getInstance().forBackgroundTasks()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        favNewsDao.delete(favNews);
                    }
                });
    }


    public LiveData<List<FavNews>> getAllNotes() {
        return allNotes;
    }

    public static class gettingDatabyId implements Callable<String> {
             private   FavNewsDao favNewsDao;
             private   int id;
        private gettingDatabyId(FavNewsDao favNewsDao,int id) {
            this.favNewsDao = favNewsDao;
            this.id = id;

        }

        @Override
        public String call(){
            return favNewsDao.loadSingle(id);
        }
    }

}