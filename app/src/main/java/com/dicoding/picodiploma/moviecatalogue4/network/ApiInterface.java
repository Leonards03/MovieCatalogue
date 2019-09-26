package com.dicoding.picodiploma.moviecatalogue4.network;

import com.dicoding.picodiploma.moviecatalogue4.utility.Movie;
import com.dicoding.picodiploma.moviecatalogue4.utility.MovieList;
import com.dicoding.picodiploma.moviecatalogue4.utility.TvShow;
import com.dicoding.picodiploma.moviecatalogue4.utility.TvShowList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("discover/movie")
    Call<MovieList> getMovies(@Query("api_key") String key);

    @GET("discover/tv")
    Call<TvShowList> getShows(@Query("api_key") String key);

    @GET("discover/movie")
    Call<MovieList> getMovies(@Query("api_key") String key,@Query("primary_release_date.gte") String gte, @Query("primary_release_date.lte") String lte);

    @GET("discover/tv")
    Call<TvShowList> getShows(@Query("api_key") String key,@Query("primary_release_date.gte") String gte, @Query("primary_release_date.lte") String lte);

    @GET("search/movie")
    Call<MovieList> getMovies(@Query("api_key") String key,@Query("query") String query);

    @GET("search/tv")
    Call<TvShowList> getShows(@Query("api_key") String key, @Query("query") String query);

    @GET("movie/{movie_id}")
    Call<Movie> getMovie(@Path("movie_id") int id, @Query("api_key") String key);

    @GET("tv/{tv_id}")
    Call<TvShow> getShow(@Path("tv_id") int id, @Query("api_key") String key);
}
