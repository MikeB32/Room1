package com.task.room.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.task.room.FavNews;

@Database(entities = {FavNews.class}, version = 1, exportSchema = false)
public abstract class FavNewsDatabase extends RoomDatabase {

    private static FavNewsDatabase instance;

    public abstract FavNewsDao favDao();



    public static synchronized FavNewsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FavNewsDatabase.class, "news_database")
                    .build();
        }
        return instance;
    }

}