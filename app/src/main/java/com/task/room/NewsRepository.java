package com.task.room;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.task.room.model.NewsResponse;
import com.task.room.networking.NewsApi;
import com.task.room.networking.RetrofitService;
import com.task.room.viewModels.FavNewsDatabase;

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

    public void insert(FavNews favNews) {
        Runnable myRunnable = createRunnable(favNews);
        myRunnable.run();
    }

//    public void offlineData(FavNews favNews) {
//        Runnable myRunnable = createRunnable2(favNews);
//        myRunnable.run();
//    }


    public void update(FavNews favNews) {
        new UpdateNoteAsyncTask(favNewsDao).execute(favNews);
    }

    public void delete(FavNews favNews) {
        new DeleteNoteAsyncTask(favNewsDao).execute(favNews);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(favNewsDao).execute();
    }

    public LiveData<List<FavNews>> getAllNotes() {
        return allNotes;
    }



    private static class InsertNoteAsyncTask extends AsyncTask<FavNews, Void, Void> {
        private FavNewsDao favNewsDao;

        private InsertNoteAsyncTask(FavNewsDao favNewsDao) {
            this.favNewsDao = favNewsDao;
        }

        @Override
        protected Void doInBackground(FavNews... favNews) {
            favNewsDao.insert(favNews[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<FavNews, Void, Void> {
        private FavNewsDao favNewsDao;

        private UpdateNoteAsyncTask(FavNewsDao favNewsDao) {
            this.favNewsDao = favNewsDao;
        }

        @Override
        protected Void doInBackground(FavNews... favNews) {
            favNewsDao.update(favNews[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<FavNews, Void, Void> {
        private FavNewsDao favNewsDao;

        private DeleteNoteAsyncTask(FavNewsDao favNewsDao) {
            this.favNewsDao = favNewsDao;
        }

        @Override
        protected Void doInBackground(FavNews... favNews) {
            favNewsDao.delete(favNews[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private FavNewsDao favNewsDao;

        private DeleteAllNotesAsyncTask(FavNewsDao favNewsDao) {
            this.favNewsDao = favNewsDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            favNewsDao.deleteAllNotes();
            return null;
        }
    }



    private Runnable createRunnable(final FavNews favNews){

        Runnable aRunnable = new Runnable(){
            public void run(){
                favNewsDao.insert(favNews);
            }
        };

        return aRunnable;

    }
//    private Runnable createRunnable2(final FavNews favNews){
//
//        Runnable aRunnable = new Runnable(){
//            public void run(){
//                favNewsDao.offlineDate(favNews);
//            }
//        };
//
//        return aRunnable;
//
//    }



}