package com.task.room.viewModels;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.task.room.FavNews;
import com.task.room.FavNewsDao;

@Database(entities = {FavNews.class}, version = 1,exportSchema = false)
public abstract class FavNewsDatabase extends RoomDatabase {

    private static FavNewsDatabase instance;

    public abstract FavNewsDao noteDao();



    public static synchronized FavNewsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FavNewsDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private FavNewsDao favNewsDao;

        private PopulateDbAsyncTask(FavNewsDatabase db) {
            favNewsDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
//            favNewsDao.insert(new FavNews("Title 1", "Description 1"));
//            favNewsDao.insert(new FavNews("Title 2", "Description 2"));
//            favNewsDao.insert(new FavNews("Title 3", "Description 3"));
            return null;
        }
    }
}