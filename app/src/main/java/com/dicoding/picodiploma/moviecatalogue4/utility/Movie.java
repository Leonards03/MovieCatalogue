package com.dicoding.picodiploma.moviecatalogue4.utility;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@Entity(tableName = "movies")
public class Movie {
    @PrimaryKey
    @SerializedName("id")
    private int id;
    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    private String backdrop;
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String poster;
    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String title;
    @Ignore
    @SerializedName("genres")
    private ArrayList<Genre> dummyGenres;
    @ColumnInfo(name = "genres")
    private String genre;
    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    private String overview;
    @ColumnInfo(name = "rating")
    @SerializedName("vote_average")
    private String rating;
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    private String releaseDate;

    public Movie() {
    }

    @Ignore
    public Movie(int id, String backdrop, String poster, String title, ArrayList<Genre> dummyGenres, String overview, String rating, String releaseDate) {
        this.id = id;
        this.backdrop = backdrop;
        this.poster = poster;
        this.title = title;
        this.overview = overview;
        this.rating = rating;
        this.releaseDate = releaseDate;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < dummyGenres.size(); i++) {
            if (i == dummyGenres.size() - 1)
                builder.append(dummyGenres.get(i).getName());
            else
                builder.append(dummyGenres.get(i).getName()).append(", ");
        }
        this.genre = builder.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void genreToString() {
        if(dummyGenres.size() == 0){
            genre = " ";
        }
        else {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < dummyGenres.size(); i++) {
                if (i == dummyGenres.size() - 1)
                    builder.append(dummyGenres.get(i).getName());
                else
                    builder.append(dummyGenres.get(i).getName()).append(", ");
            }
            genre = builder.toString();
        }
    }
}
