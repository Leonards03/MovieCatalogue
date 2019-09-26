package com.dicoding.picodiploma.moviecatalogue4.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.dicoding.picodiploma.moviecatalogue4.utility.Movie;
import com.dicoding.picodiploma.moviecatalogue4.utility.TvShow;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT * FROM movies WHERE id = :id")
    Movie getMovie(int id);

    @Query("SELECT * FROM shows WHERE id = :id")
    TvShow getShow(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertShow(TvShow show);

    @Delete
    void deleteShow(TvShow show);

    @Query("SELECT * FROM shows")
    LiveData<List<TvShow>> getAllShows();
}
