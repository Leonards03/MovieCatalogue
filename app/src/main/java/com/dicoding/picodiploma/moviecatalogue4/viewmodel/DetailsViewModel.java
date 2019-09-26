package com.dicoding.picodiploma.moviecatalogue4.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.dicoding.picodiploma.moviecatalogue4.database.Repository;
import com.dicoding.picodiploma.moviecatalogue4.utility.Movie;
import com.dicoding.picodiploma.moviecatalogue4.utility.TvShow;

public class DetailsViewModel extends AndroidViewModel {
    private Repository repository;

    private MutableLiveData<Movie> movie = new MutableLiveData<>();
    private MutableLiveData<TvShow> show = new MutableLiveData<>();

    public DetailsViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
    }

    public void loadMovie(int id) {
        movie = repository.loadMovie(id);
    }

    public void loadShow(int id) {
        show = repository.loadShow(id);
    }

    public void getMovie(int id) {
        movie = repository.getMovie(id);
    }

    public void getShow(int id) {
        show = repository.getShow(id);
    }

    public void insert(Movie movie) {
        repository.insert(movie);
    }

    public void insert(TvShow show) {
        repository.insert(show);
    }

    public void delete(Movie movie) {
        repository.delete(movie);
    }

    public void delete(TvShow show) {
        repository.delete(show);
    }

    public MutableLiveData<Movie> getMovie() {
        return movie;
    }

    public MutableLiveData<TvShow> getShow() {
        return show;
    }
}
