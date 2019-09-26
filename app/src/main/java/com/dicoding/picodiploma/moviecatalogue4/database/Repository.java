package com.dicoding.picodiploma.moviecatalogue4.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dicoding.picodiploma.moviecatalogue4.BuildConfig;
import com.dicoding.picodiploma.moviecatalogue4.network.ApiClient;
import com.dicoding.picodiploma.moviecatalogue4.network.ApiInterface;
import com.dicoding.picodiploma.moviecatalogue4.utility.Movie;
import com.dicoding.picodiploma.moviecatalogue4.utility.MovieList;
import com.dicoding.picodiploma.moviecatalogue4.utility.TvShow;
import com.dicoding.picodiploma.moviecatalogue4.utility.TvShowList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private static Repository instance;

    private static final String API_KEY = BuildConfig.TMDB_API_KEY;
    private static final String TAG = Repository.class.getSimpleName();

    private FavoriteDao favoriteDao;
    private ApiInterface apiInterface;

    private MutableLiveData<ArrayList<Movie>> movies = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TvShow>> shows = new MutableLiveData<>();

    private MutableLiveData<Movie> movie = new MutableLiveData<>();
    private MutableLiveData<TvShow> show = new MutableLiveData<>();

    private LiveData<List<Movie>> favoriteMovies;
    private LiveData<List<TvShow>> favoriteShows;

    private Repository(Application application) {
        FavoriteDatabase database = FavoriteDatabase.getInstance(application);
        favoriteDao = database.favoriteDao();
        favoriteMovies = favoriteDao.getAllMovies();
        favoriteShows = favoriteDao.getAllShows();
        loadMovies();
        loadShows();
    }

    public static Repository getInstance(Application application) {
        if (instance == null) {
            synchronized (Repository.class) {
                if (instance == null) {
                    instance = new Repository(application);
                }
            }
        }
        return instance;
    }

    public MutableLiveData<Movie> getMovie(int id) {
        Movie item = null;
        try {
            item = new LoadMovieAsync(favoriteDao).execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        movie.postValue(item);
        return movie;
    }

    public MutableLiveData<TvShow> getShow(int id) {
        TvShow item = null;
        try {
            item = new LoadShowAsync(favoriteDao).execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        show.postValue(item);
        return show;
    }

    public void insert(Movie movie) {
        new InsertMovieAsync(favoriteDao).execute(movie);
    }

    public void insert(TvShow show) {
        new InsertShowAsync(favoriteDao).execute(show);
    }

    public void delete(Movie movie) {
        new DeleteMovieAsync(favoriteDao).execute(movie);
    }

    public void delete(TvShow show) {
        new DeleteShowAsync(favoriteDao).execute(show);
    }

    private void loadMovies() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieList> client = apiInterface.getMovies(API_KEY);
        client.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(@NonNull Call<MovieList> call, @NonNull Response<MovieList> response) {
                if (response.body() != null) {
                    movies.postValue(response.body().getMovies());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieList> call, @NonNull Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private void loadShows() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TvShowList> client = apiInterface.getShows(API_KEY);
        client.enqueue(new Callback<TvShowList>() {
            @Override
            public void onResponse(@NonNull Call<TvShowList> call, @NonNull Response<TvShowList> response) {
                if (response.body() != null) {
                    shows.postValue(response.body().getShows());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvShowList> call, @NonNull Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void loadMovies(String query){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieList> client = apiInterface.getMovies(API_KEY,query);
        client.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(@NonNull Call<MovieList> call, @NonNull Response<MovieList> response) {
                if(response.body() != null){
                    movies.postValue(response.body().getMovies());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieList> call,@NonNull Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void loadShows(String query){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TvShowList> client = apiInterface.getShows(API_KEY,query);
        client.enqueue(new Callback<TvShowList>() {
            @Override
            public void onResponse(@NonNull Call<TvShowList> call,@NonNull Response<TvShowList> response) {
                if(response.body() != null){
                    shows.postValue(response.body().getShows());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvShowList> call,@NonNull Throwable t) {
                Log.e(TAG,t.getMessage());
            }
        });
    }

    public void loadMovies(String gte, String lte){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieList> client = apiInterface.getMovies(API_KEY,gte,lte);
        client.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(@NonNull Call<MovieList> call,@NonNull Response<MovieList> response) {
                if(response.body() != null){
                    movies.postValue(response.body().getMovies());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieList> call,@NonNull Throwable t) {
                Log.e(TAG,t.getMessage());
            }
        });
    }
    public void loadShows(String gte,String lte){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TvShowList> client = apiInterface.getShows(API_KEY,gte,lte);
        client.enqueue(new Callback<TvShowList>() {
            @Override
            public void onResponse(@NonNull Call<TvShowList> call,@NonNull Response<TvShowList> response) {
                if(response.body() != null){
                    shows.postValue(response.body().getShows());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvShowList> call,@NonNull Throwable t) {
                Log.e(TAG,t.getMessage());
            }
        });
    }

    public MutableLiveData<Movie> loadMovie(int id) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Movie> movieCall = apiInterface.getMovie(id, API_KEY);
        movieCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                movie.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
        return movie;
    }

    public MutableLiveData<TvShow> loadShow(int id) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TvShow> showCall = apiInterface.getShow(id, API_KEY);
        showCall.enqueue(new Callback<TvShow>() {
            @Override
            public void onResponse(@NonNull Call<TvShow> call, @NonNull Response<TvShow> response) {
                show.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TvShow> call, @NonNull Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
        return show;
    }

    public MutableLiveData<ArrayList<Movie>> getMovies() {
        return movies;
    }

    public MutableLiveData<ArrayList<TvShow>> getShows() {
        return shows;
    }

    public void setMovie(MutableLiveData<Movie> movie) {
        this.movie = movie;
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return favoriteMovies;
    }

    public LiveData<List<TvShow>> getFavoriteShows() {
        return favoriteShows;
    }

    private static class LoadMovieAsync extends AsyncTask<Integer, Void, Movie> {
        private FavoriteDao favoriteDao;

        LoadMovieAsync(FavoriteDao favoriteDao) {
            this.favoriteDao = favoriteDao;
        }

        @Override
        protected Movie doInBackground(Integer... integers) {
            return favoriteDao.getMovie(integers[0]);
        }
    }

    private static class LoadShowAsync extends AsyncTask<Integer, Void, TvShow> {
        private FavoriteDao favoriteDao;

        LoadShowAsync(FavoriteDao favoriteDao) {
            this.favoriteDao = favoriteDao;
        }


        @Override
        protected TvShow doInBackground(Integer... integers) {
            return favoriteDao.getShow(integers[0]);
        }
    }

    private static class InsertMovieAsync extends AsyncTask<Movie, Void, Void> {

        private FavoriteDao favoriteDao;

        private InsertMovieAsync(FavoriteDao favoriteDao) {
            this.favoriteDao = favoriteDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            favoriteDao.insertMovie(movies[0]);
            return null;
        }
    }

    private static class DeleteMovieAsync extends AsyncTask<Movie, Void, Void> {
        private FavoriteDao favoriteDao;

        private DeleteMovieAsync(FavoriteDao favoriteDao) {
            this.favoriteDao = favoriteDao;
        }


        @Override
        protected Void doInBackground(Movie... movies) {
            favoriteDao.deleteMovie(movies[0]);
            return null;
        }
    }

    private static class InsertShowAsync extends AsyncTask<TvShow, Void, Void> {
        private FavoriteDao favoriteDao;

        private InsertShowAsync(FavoriteDao favoriteDao) {
            this.favoriteDao = favoriteDao;
        }

        @Override
        protected Void doInBackground(TvShow... tvShows) {
            favoriteDao.insertShow(tvShows[0]);
            return null;
        }
    }


    private static class DeleteShowAsync extends AsyncTask<TvShow, Void, Void> {
        private FavoriteDao favoriteDao;

        private DeleteShowAsync(FavoriteDao favoriteDao) {
            this.favoriteDao = favoriteDao;
        }

        @Override
        protected Void doInBackground(TvShow... tvShows) {
            favoriteDao.deleteShow(tvShows[0]);
            return null;
        }
    }
}

