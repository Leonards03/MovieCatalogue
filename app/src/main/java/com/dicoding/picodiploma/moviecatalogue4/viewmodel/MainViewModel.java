package com.dicoding.picodiploma.moviecatalogue4.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.dicoding.picodiploma.moviecatalogue4.database.Repository;
import com.dicoding.picodiploma.moviecatalogue4.utility.Movie;
import com.dicoding.picodiploma.moviecatalogue4.utility.TvShow;

import java.util.ArrayList;

public class MainViewModel extends AndroidViewModel {
    private Repository repository;

    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TvShow>> listShows = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
    }

    public void loadMovies() {
        listMovies = repository.getMovies();
    }

    public void loadShows() {
        listShows = repository.getShows();
    }

    public void loadMovies(String query){
        repository.loadMovies(query);
        listMovies.postValue(repository.getMovies().getValue());
    }

    public void loadShows(String query){
        repository.loadShows(query);
        listShows.postValue(repository.getShows().getValue());
    }

    public void loadMovies(String gte, String lte){
        repository.loadMovies(gte,lte);
    }

    public void loadShows(String gte, String lte){
        repository.loadShows(gte,lte);
    }

    public MutableLiveData<ArrayList<Movie>> getListMovies() {
        return listMovies;
    }

    public MutableLiveData<ArrayList<TvShow>> getListShows() {
        return listShows;
    }

}
