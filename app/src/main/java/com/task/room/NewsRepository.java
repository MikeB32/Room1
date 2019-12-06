package com.task.room;

import android.app.Application;
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
    private static NewsRepository newsRepository;


    public static NewsRepository getInstance(){
        if (newsRepository == null){
            newsRepository = new NewsRepository();
        }
        return newsRepository;
    }

    private NewsRepository(){
        newsApi = RetrofitService.cteateService(NewsApi.class);
    }





    public MutableLiveData<NewsResponse> getNews(String key){
        final MutableLiveData<NewsResponse> newsData = new MutableLiveData<>();
        newsApi.getNewsList( key).enqueue(new Callback<NewsResponse>() {
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
        new InsertNoteAsyncTask(favNewsDao).execute(favNews);
    }

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
}