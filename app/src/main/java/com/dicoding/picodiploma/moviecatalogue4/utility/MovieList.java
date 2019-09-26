package com.dicoding.picodiploma.moviecatalogue4.utility;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieList {
    @SerializedName("results")
    private ArrayList<Movie> movies;

    public MovieList(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }
}
