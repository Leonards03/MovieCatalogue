package com.dicoding.picodiploma.moviecatalogue4.utility;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Genre {
    @SerializedName("name")
    private String name;

    public Genre(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
