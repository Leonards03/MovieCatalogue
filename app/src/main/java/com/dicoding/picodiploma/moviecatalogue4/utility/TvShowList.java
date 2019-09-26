package com.dicoding.picodiploma.moviecatalogue4.utility;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TvShowList {
    @SerializedName("results")
    private ArrayList<TvShow> shows;

    public TvShowList(ArrayList<TvShow> shows) {
        this.shows = shows;
    }

    public ArrayList<TvShow> getShows() {
        return shows;
    }
}
