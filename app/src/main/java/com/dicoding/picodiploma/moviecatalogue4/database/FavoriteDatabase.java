package com.dicoding.picodiploma.moviecatalogue4.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.dicoding.picodiploma.moviecatalogue4.utility.Movie;
import com.dicoding.picodiploma.moviecatalogue4.utility.TvShow;

@Database(entities = {Movie.class, TvShow.class}, version = 2, exportSchema = false)
public abstract class FavoriteDatabase extends RoomDatabase {

    private static FavoriteDatabase instance;

    public abstract FavoriteDao favoriteDao();

    static FavoriteDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (FavoriteDatabase.class) {
                instance = Room.databaseBuilder(context.getApplicationContext(), FavoriteDatabase.class, "favoritedb")
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return instance;
    }
}
