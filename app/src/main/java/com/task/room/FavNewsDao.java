package com.task.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavNewsDao {

    @Insert
    void insert(FavNews favNews);

    @Insert
    void offlineDate(FavNews favNews);

    @Update
    void update(FavNews favNews);

    @Delete
    void delete(FavNews favNews);

    @Query("DELETE FROM news_table")
    void deleteAllNotes();

    @Query("SELECT * FROM news_table")
    LiveData<List<FavNews>> getAllNotes();

    @Query("SELECT EXISTS (SELECT * FROM news_table WHERE title=:title)")
    Boolean  isFavorite(Boolean title);


}