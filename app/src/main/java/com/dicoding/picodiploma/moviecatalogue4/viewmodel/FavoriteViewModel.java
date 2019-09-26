package com.dicoding.picodiploma.moviecatalogue4.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.dicoding.picodiploma.moviecatalogue4.database.Repository;
import com.dicoding.picodiploma.moviecatalogue4.utility.Movie;
import com.dicoding.picodiploma.moviecatalogue4.utility.TvShow;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {
    private LiveData<List<Movie>> movies;
    private LiveData<List<TvShow>> shows;

    private Repository repository;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
    }

    public void loadMovies() {
        movies = repository.getFavoriteMovies();
    }

    public void loadShows() {
        shows = repository.getFavoriteShows();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public LiveData<List<TvShow>> getShows() {
        return shows;
    }
}
