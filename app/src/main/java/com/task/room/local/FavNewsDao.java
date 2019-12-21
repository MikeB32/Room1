package com.task.room.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.task.room.FavNews;
import java.util.List;


@Dao
public interface FavNewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavNews favNews);

    @Update
    void update(FavNews favNews);

    @Delete
    void delete(FavNews favNews);

    @Query("DELETE FROM news_table")
    void deleteAllNotes();

    @Query("SELECT * FROM news_table")
    LiveData<List<FavNews>> getAllNotes();

    @Query("SELECT offlineData FROM news_table WHERE id =:id")
    String loadSingle(int id);




}