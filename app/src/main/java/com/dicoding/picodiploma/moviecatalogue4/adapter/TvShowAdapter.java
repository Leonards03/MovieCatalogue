package com.dicoding.picodiploma.moviecatalogue4.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dicoding.picodiploma.moviecatalogue4.R;
import com.dicoding.picodiploma.moviecatalogue4.network.ApiClient;
import com.dicoding.picodiploma.moviecatalogue4.utility.TvShow;

import java.util.ArrayList;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ViewHolder> {
    private ArrayList<TvShow> shows = new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;

    public void setData(ArrayList<TvShow> items) {
        shows.clear();
        shows.addAll(items);
        notifyDataSetChanged();
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public TvShowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_items, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowAdapter.ViewHolder viewHolder, int position) {
        viewHolder.bind(shows.get(position));
    }

    @Override
    public int getItemCount() {
        return shows.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle;
        TextView tvReleaseYear;
        TextView tvOverview;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvReleaseYear = itemView.findViewById(R.id.tv_release_year);
            tvOverview = itemView.findViewById(R.id.tv_overview);
        }

        void bind(TvShow show) {
            if (show != null) {
                String imageLink = ApiClient.getImageLink(show.getPoster());
                Glide.with(itemView.getContext()).load(imageLink).into(imgPoster);
                tvTitle.setText(show.getTitle());
                String releaseYear = show.getReleaseDate().replaceAll("\\D", "").substring(0, 4);
                tvReleaseYear.setText(releaseYear);
                tvOverview.setText(show.getOverview());
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickCallback.onItemClicked(shows.get(getAdapterPosition()));
                    }
                });
            }
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(TvShow show);
    }
}
