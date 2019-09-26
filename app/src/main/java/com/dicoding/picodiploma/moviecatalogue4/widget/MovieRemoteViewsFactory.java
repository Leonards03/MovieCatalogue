package com.dicoding.picodiploma.moviecatalogue4.widget;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.dicoding.picodiploma.moviecatalogue4.R;
import com.dicoding.picodiploma.moviecatalogue4.database.Repository;
import com.dicoding.picodiploma.moviecatalogue4.network.ApiClient;
import com.dicoding.picodiploma.moviecatalogue4.utility.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private List<Movie> movies = new ArrayList<>();
    private Context context;
    private Repository repository;

    MovieRemoteViewsFactory(Context context) {
        this.context = context;
        repository = Repository.getInstance((Application) context.getApplicationContext());
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if(repository.getFavoriteMovies().getValue() != null)
            movies.addAll(repository.getFavoriteMovies().getValue());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        Bitmap bitmap = null;
        Glide.with(context)
                .asBitmap()
                .load(ApiClient.getImageLink(movies.get(position).getBackdrop()))
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        rv.setImageViewBitmap(R.id.image_view, resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
        rv.setImageViewBitmap(R.id.image_view, bitmap);
        Bundle extras = new Bundle();
        extras.putInt(FavoriteMovieWidget.EXTRA_ITEM, position);
        Intent fillIntIntent = new Intent();
        fillIntIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.image_view, fillIntIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
