package com.task.room.repository;

import android.app.Application;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private FavNewsDao favNewsDao;
    private LiveData<List<FavNews>> allNotes;
    private NewsApi newsApi;

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
        FavNewsDatabase database = FavNewsDatabase.getInstance(application);
        favNewsDao = database.noteDao();
        allNotes = favNewsDao.getAllNotes();
    }



    public String singleLoad(int id){

        return  favNewsDao.loadSingle(id);
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


}