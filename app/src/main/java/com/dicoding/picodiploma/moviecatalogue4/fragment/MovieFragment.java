package com.dicoding.picodiploma.moviecatalogue4.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dicoding.picodiploma.moviecatalogue4.R;
import com.dicoding.picodiploma.moviecatalogue4.activity.DetailsActivity;
import com.dicoding.picodiploma.moviecatalogue4.adapter.MovieAdapter;
import com.dicoding.picodiploma.moviecatalogue4.adapter.PagerAdapter;
import com.dicoding.picodiploma.moviecatalogue4.utility.HasLoading;
import com.dicoding.picodiploma.moviecatalogue4.utility.Movie;
import com.dicoding.picodiploma.moviecatalogue4.utility.Searchable;
import com.dicoding.picodiploma.moviecatalogue4.viewmodel.FavoriteViewModel;
import com.dicoding.picodiploma.moviecatalogue4.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;


public class MovieFragment extends Fragment implements HasLoading, Searchable {
    private ProgressBar progressBar;
    private MovieAdapter adapter;
    private boolean isMainActivity;

    private MainViewModel mainViewModel;


    public MovieFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress_bar);
        showLoading(true);

        adapter = new MovieAdapter();
        adapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie movie) {
                detailsIntent(movie.getId());
            }
        });
        adapter.notifyDataSetChanged();

        RecyclerView rvMovies = view.findViewById(R.id.rv_movies);
        rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovies.setAdapter(adapter);

        isMainActivity = false;
        if (getArguments() != null) {
            isMainActivity = getArguments().getBoolean(PagerAdapter.EXTRA_BOOLEAN);
        }
        prepareViewModel(isMainActivity);
    }

    @Override
    public void showLoading(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void detailsIntent(int id) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_BOOL, true);
        intent.putExtra(DetailsActivity.EXTRA_BOOL2, isMainActivity);
        intent.putExtra(DetailsActivity.EXTRA_ID, id);
        startActivity(intent);
    }


    private void prepareViewModel(boolean isMainActivity) {
        if (isMainActivity) {
            mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
            mainViewModel.loadMovies();
            mainViewModel.getListMovies().observe(this, new Observer<ArrayList<Movie>>() {
                @Override
                public void onChanged(@Nullable ArrayList<Movie> movies) {
                    if (movies != null) {
                        adapter.setData(movies);
                        showLoading(false);
                    }
                }
            });
        } else {
            FavoriteViewModel favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
            favoriteViewModel.loadMovies();
            favoriteViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
                @Override
                public void onChanged(@Nullable List<Movie> movies) {
                   if (movies != null) {
                        adapter.setData((ArrayList<Movie>) movies);
                        showLoading(false);
                    }
                }
            });
        }
    }

    @Override
    public void onSubmit(String text) {
        if(text.isEmpty())
            mainViewModel.loadMovies();
        else
            mainViewModel.loadMovies(text);
    }
}
