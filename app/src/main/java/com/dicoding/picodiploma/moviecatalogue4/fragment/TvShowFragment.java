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
import com.dicoding.picodiploma.moviecatalogue4.adapter.PagerAdapter;
import com.dicoding.picodiploma.moviecatalogue4.adapter.TvShowAdapter;
import com.dicoding.picodiploma.moviecatalogue4.utility.HasLoading;
import com.dicoding.picodiploma.moviecatalogue4.utility.Searchable;
import com.dicoding.picodiploma.moviecatalogue4.utility.TvShow;
import com.dicoding.picodiploma.moviecatalogue4.viewmodel.FavoriteViewModel;
import com.dicoding.picodiploma.moviecatalogue4.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class TvShowFragment extends Fragment implements HasLoading, Searchable {
    private ProgressBar progressBar;
    private TvShowAdapter adapter;
    private boolean isMainActivity;

    private MainViewModel mainViewModel;

    public TvShowFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress_bar);
        showLoading(true);

        adapter = new TvShowAdapter();
        adapter.setOnItemClickCallback(new TvShowAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(TvShow show) {
                detailsIntent(show.getId());
            }
        });
        adapter.notifyDataSetChanged();

        RecyclerView rvShows = view.findViewById(R.id.rv_shows);
        rvShows.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvShows.setAdapter(adapter);

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
        intent.putExtra(DetailsActivity.EXTRA_BOOL, false);
        intent.putExtra(DetailsActivity.EXTRA_BOOL2, isMainActivity);
        intent.putExtra(DetailsActivity.EXTRA_ID, id);
        startActivity(intent);
    }


    private void prepareViewModel(boolean isMainActivity) {
        if (isMainActivity) {
            mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
            mainViewModel.loadShows();
            mainViewModel.getListShows().observe(this, new Observer<ArrayList<TvShow>>() {
                @Override
                public void onChanged(@Nullable ArrayList<TvShow> tvShows) {
                    if (tvShows != null) {
                        adapter.setData(tvShows);
                        showLoading(false);
                    }
                }
            });
        } else {
            FavoriteViewModel favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
            favoriteViewModel.loadShows();
            favoriteViewModel.getShows().observe(this, new Observer<List<TvShow>>() {
                @Override
                public void onChanged(@Nullable List<TvShow> tvShows) {
                    if (tvShows != null) {
                        adapter.setData((ArrayList<TvShow>) tvShows);
                        showLoading(false);
                    }
                }
            });
        }
    }

    @Override
    public void onSubmit(String text) {
        if(text.isEmpty())
            mainViewModel.loadShows();
        else
            mainViewModel.loadShows(text);
    }
}
