package com.dicoding.picodiploma.moviecatalogue4.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.dicoding.picodiploma.moviecatalogue4.R;
import com.dicoding.picodiploma.moviecatalogue4.adapter.PagerAdapter;
import com.dicoding.picodiploma.moviecatalogue4.fragment.MovieFragment;
import com.dicoding.picodiploma.moviecatalogue4.fragment.TvShowFragment;

public class FavoritesActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private String MOVIE_TAB;
    private String TV_TAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        MOVIE_TAB = getResources().getString(R.string.movie_tab);
        TV_TAB = getResources().getString(R.string.tv_tab);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.favorite));
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewpager);
        prepareFragment(viewPager);

        tabLayout = findViewById(R.id.tabs);
        prepareTab();
    }

    private void prepareFragment(ViewPager viewPager) {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        bundle.putBoolean(PagerAdapter.EXTRA_BOOLEAN, false);
        MovieFragment movieFragment = new MovieFragment();
        movieFragment.setArguments(bundle);

        TvShowFragment showFragment = new TvShowFragment();
        showFragment.setArguments(bundle);

        adapter.addFragment(movieFragment, MOVIE_TAB);
        adapter.addFragment(showFragment, TV_TAB);
        viewPager.setAdapter(adapter);
    }

    private void prepareTab() {
        tabLayout.addTab(tabLayout.newTab().setText(MOVIE_TAB));
        tabLayout.addTab(tabLayout.newTab().setText(TV_TAB));
        tabLayout.setupWithViewPager(viewPager);
    }

}
