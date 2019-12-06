package com.task.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.task.room.model.NewsResponse;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NewsRepository repository;
        private LiveData<List<FavNews>> allNotes;



    public MutableLiveData<NewsResponse> getNewsRepository() {
        repository = NewsRepository.getInstance();
        return repository.getNews( "b70d396f-d5b4-43f0-8b67-222524937f25");
    }

    public NoteViewModel(@NonNull Application application) {
            super(application);
            repository = new NewsRepository(application);
            allNotes = repository.getAllNotes();
    }

    public void insert(FavNews favNews) {
        repository.insert(favNews);
    }

    public void update(FavNews favNews) {
        repository.update(favNews);
    }

    public void delete(FavNews favNews) {
        repository.delete(favNews);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<FavNews>> getAllNotes() {
        return allNotes;
    }
}