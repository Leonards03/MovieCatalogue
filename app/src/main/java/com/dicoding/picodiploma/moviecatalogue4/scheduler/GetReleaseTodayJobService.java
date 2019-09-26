package com.dicoding.picodiploma.moviecatalogue4.scheduler;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;

import com.dicoding.picodiploma.moviecatalogue4.BuildConfig;
import com.dicoding.picodiploma.moviecatalogue4.network.ApiClient;
import com.dicoding.picodiploma.moviecatalogue4.network.ApiInterface;
import com.dicoding.picodiploma.moviecatalogue4.utility.Movie;
import com.dicoding.picodiploma.moviecatalogue4.utility.MovieList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetReleaseTodayJobService extends JobService {
    private static final String API_KEY = BuildConfig.TMDB_API_KEY;
    private ApiInterface apiInterface;
    @Override
    public boolean onStartJob(JobParameters params) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    private void getReleaseToday(final JobParameters jobs){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        Date date = new Date();
        String text = formatter.format(date);
        Call<MovieList> movieListCall = apiInterface.getMovies(text,text);
        movieListCall.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                ArrayList<Movie> movies = response.body().getMovies();
                for(Movie movie : movies){
                    
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {

            }
        });
    }
}
