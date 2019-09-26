package com.dicoding.picodiploma.moviecatalogue4.activity;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dicoding.picodiploma.moviecatalogue4.R;
import com.dicoding.picodiploma.moviecatalogue4.network.ApiClient;
import com.dicoding.picodiploma.moviecatalogue4.utility.HasLoading;
import com.dicoding.picodiploma.moviecatalogue4.utility.Movie;
import com.dicoding.picodiploma.moviecatalogue4.utility.TvShow;
import com.dicoding.picodiploma.moviecatalogue4.viewmodel.DetailsViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity implements HasLoading, View.OnClickListener {
    public static final String EXTRA_BOOL = "extra_bool";
    public static final String EXTRA_BOOL2 = "extra_bool2";
    public static final String EXTRA_ID = "extra_id";

    private DetailsViewModel detailsViewModel;
    private ConstraintLayout detailsContent;
    private ProgressBar progressBar;
    private ImageButton btnFavorite;
    private ImageView imgBackdrop, imgPoster;
    private TextView tvTitle, tvGenre, tvRating, tvReleaseDate, tvOverview;
    private RatingBar ratingBar;
    private boolean fromAPI;
    private boolean isFavorite;
    boolean itemIsMovie;

    private Movie movie;
    private TvShow tvShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        btnFavorite = findViewById(R.id.img_favorites);
        btnFavorite.setOnClickListener(this);

        imgBackdrop = findViewById(R.id.img_backdrop);
        imgPoster = findViewById(R.id.img_poster);

        ratingBar = findViewById(R.id.rating_bar);
        tvTitle = findViewById(R.id.tv_title);
        tvGenre = findViewById(R.id.tv_genre);
        tvRating = findViewById(R.id.tv_rating);
        tvReleaseDate = findViewById(R.id.tv_release_date);
        tvOverview = findViewById(R.id.tv_overview);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        detailsContent = findViewById(R.id.details_content);
        progressBar = findViewById(R.id.progress_bar);
        showLoading(true);

        int id = getIntent().getIntExtra(EXTRA_ID, 0);
        itemIsMovie = getIntent().getBooleanExtra(EXTRA_BOOL, false);
        fromAPI = getIntent().getBooleanExtra(EXTRA_BOOL2, false);

        setFavorite(!fromAPI);
        setTitle(itemIsMovie);
        detailsViewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
        loadData(id, itemIsMovie, fromAPI);
    }

    @Override
    public void showLoading(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            detailsContent.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            detailsContent.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void bind(Movie movie) {

        this.movie = movie;

        Glide.with(this).load(ApiClient.getImageLink(movie.getBackdrop())).into(imgBackdrop);
        Glide.with(this).load(ApiClient.getImageLink(movie.getPoster())).into(imgPoster);
        if (fromAPI)
            movie.genreToString();
        tvTitle.setText(movie.getTitle());
        tvGenre.setText(movie.getGenre());
        tvRating.setText(movie.getRating());
        ratingBar.setRating(Float.parseFloat(movie.getRating()) / 2);
        tvReleaseDate.setText(toDate(movie.getReleaseDate()));
        tvOverview.setText(movie.getOverview());
    }

    private void bind(TvShow show) {

        this.tvShow = show;

        Glide.with(this).load(ApiClient.getImageLink(show.getBackdrop())).into(imgBackdrop);
        Glide.with(this).load(ApiClient.getImageLink(show.getPoster())).into(imgPoster);
        if (fromAPI)
            show.genreToString();
        tvTitle.setText(show.getTitle());
        tvGenre.setText(show.getGenre());
        tvRating.setText(show.getRating());
        ratingBar.setRating(Float.parseFloat(show.getRating()) / 2);
        tvReleaseDate.setText(toDate(show.getReleaseDate()));
        tvOverview.setText(show.getOverview());
    }

    private void setTitle(boolean itemIsMovie) {
        if (getSupportActionBar() != null) {
            if (itemIsMovie)
                getSupportActionBar().setTitle(getResources().getString(R.string.movie_details));
            else
                getSupportActionBar().setTitle(getResources().getString(R.string.tv_details));
        }
    }

    private String toDate(String format) {
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = formatter.parse(format);
            formatter.applyPattern("dd MMMM yyyy");
            return formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadData(int id, boolean itemIsMovie, boolean fromAPI) {
        if (itemIsMovie) {
            if (fromAPI)
                detailsViewModel.loadMovie(id);
            else
                detailsViewModel.getMovie(id);

            Observer<Movie> movieObserver = new Observer<Movie>() {
                @Override
                public void onChanged(@Nullable Movie movie) {
                    if (movie != null) {
                        bind(movie);
                        showLoading(false);
                    }
                }
            };
            detailsViewModel.getMovie().observe(this, movieObserver);
        } else {
            if (fromAPI)
                detailsViewModel.loadShow(id);
            else
                detailsViewModel.getShow(id);

            Observer<TvShow> showObserver = new Observer<TvShow>() {
                @Override
                public void onChanged(@Nullable TvShow show) {
                    if (show != null) {
                        bind(show);
                        showLoading(false);
                    }
                }
            };
            detailsViewModel.getShow().observe(this, showObserver);
        }
    }

    private void setFavorite(boolean state) {
        isFavorite = state;
        if (state) {
            Glide.with(this).load(R.drawable.ic_favorite_24dp).into(btnFavorite);
        } else {
            Glide.with(this).load(R.drawable.ic_favorite_border_24dp).into(btnFavorite);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_favorites) {
            String insertText = " " + getResources().getString(R.string.favorite_add);
            String deleteText = " " + getResources().getString(R.string.favorite_delete);
            if (isFavorite) {
                setFavorite(false);
                if (itemIsMovie) {
                    detailsViewModel.delete(movie);
                    Toast.makeText(this, movie.getTitle() + deleteText, Toast.LENGTH_SHORT).show();
                } else {
                    detailsViewModel.delete(tvShow);
                    Toast.makeText(this, tvShow.getTitle() + deleteText, Toast.LENGTH_SHORT).show();
                }
            } else {
                setFavorite(true);
                if (itemIsMovie) {
                    detailsViewModel.insert(movie);
                    Toast.makeText(this, movie.getTitle() + insertText, Toast.LENGTH_SHORT).show();
                } else {
                    detailsViewModel.insert(tvShow);
                    Toast.makeText(this, tvShow.getTitle() + insertText, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detailsViewModel.getMovie().setValue(null);
        detailsViewModel.getShow().setValue(null);
    }
}
