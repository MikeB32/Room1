package com.task.room.viewModels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.task.room.FavNews;
import com.task.room.model.NewsResponse;
import com.task.room.repository.NewsRepository;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {
    private NewsRepository repository;
        private LiveData<List<FavNews>> allNotes;

    public NewsViewModel(Application application) {
        super(application);
        repository = new NewsRepository(application);

    }

    public MutableLiveData<NewsResponse> getNewsRepository(int pageNo) {
        return repository.getNews( pageNo,"b70d396f-d5b4-43f0-8b67-222524937f25");
    }



    public void insert(FavNews favNews) {

        repository.insert(favNews);
    }

    public String singleLoad(int id) {


        return repository.singleLoad(id);
    }

    public void update(FavNews favNews) {

        repository.update(favNews);
    }

    public void delete(FavNews favNews) {
        repository.delete(favNews);
    }


    public LiveData<List<FavNews>> getAllNotes() {

        allNotes = repository.getAllNotes();

        return allNotes;
    }
}