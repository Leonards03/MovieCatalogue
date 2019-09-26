package com.dicoding.picodiploma.moviecatalogue4.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dicoding.picodiploma.moviecatalogue4.R;
import com.dicoding.picodiploma.moviecatalogue4.adapter.PagerAdapter;
import com.dicoding.picodiploma.moviecatalogue4.fragment.MovieFragment;
import com.dicoding.picodiploma.moviecatalogue4.fragment.TvShowFragment;
import com.dicoding.picodiploma.moviecatalogue4.utility.Searchable;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements Searchable {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private String MOVIE_TAB;
    private String TV_TAB;

    private WeakReference<Searchable> movieFragmentRef;
    private WeakReference<Searchable> showFragmentRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MOVIE_TAB = getResources().getString(R.string.movie_tab);
        TV_TAB = getResources().getString(R.string.tv_tab);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewpager);
        prepareFragment(viewPager);

        tabLayout = findViewById(R.id.tabs);
        prepareTab();

        FloatingActionButton fabFavorites = findViewById(R.id.fab_favorites);
        fabFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FavoritesActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if(searchManager != null){
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    Toast.makeText(MainActivity.this, s.trim()+ " "+getTabName(), Toast.LENGTH_SHORT).show();
                    if(!s.isEmpty())
                        onSubmit(s);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    onSubmit(s);
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.language_settings) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.reminder_settings){
            startActivity(new Intent(MainActivity.this,ReminderSettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void prepareFragment(ViewPager viewPager) {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        bundle.putBoolean(PagerAdapter.EXTRA_BOOLEAN, true);

        MovieFragment movieFragment = new MovieFragment();
        movieFragment.setArguments(bundle);

        TvShowFragment showFragment = new TvShowFragment();
        showFragment.setArguments(bundle);

        adapter.addFragment(movieFragment, MOVIE_TAB);
        adapter.addFragment(showFragment, TV_TAB);
        viewPager.setAdapter(adapter);

        showFragmentRef = new WeakReference<>((Searchable)showFragment);
        movieFragmentRef = new WeakReference<>((Searchable)movieFragment);
    }

    private void prepareTab() {
        tabLayout.addTab(tabLayout.newTab().setText(MOVIE_TAB));
        tabLayout.addTab(tabLayout.newTab().setText(TV_TAB));
        tabLayout.setupWithViewPager(viewPager);
    }

    private String getTabName(){
        int position = tabLayout.getSelectedTabPosition();
        if(position == 0){
            return MOVIE_TAB;
        }
        else{
            return TV_TAB;
        }
    }

    @Override
    public void onSubmit(String text) {
        String tabName = getTabName();
        if(tabName.equals(MOVIE_TAB)){
            movieFragmentRef.get().onSubmit(text);
        }
        else
        {
            showFragmentRef.get().onSubmit(text);
        }
    }
}
