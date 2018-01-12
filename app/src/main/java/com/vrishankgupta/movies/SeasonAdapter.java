package com.vrishankgupta.movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vrishankgupta.movies.TvShow.TvSeasons;

import java.util.ArrayList;

/**
 * Created by vrishankgupta on 11/01/18.
 */

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.myHolder> {

    private ArrayList<TvSeasons> movieDetailsList;


    private Context context;

    public SeasonAdapter(ArrayList<TvSeasons> movieDetailsList, Context context) {
        this.movieDetailsList = movieDetailsList;
        this.context = context;
    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.simple_detail, parent, false);

        return new myHolder(itemView);
    }

    @Override
    public void onBindViewHolder(myHolder holder, int position) {
        TvSeasons tvSeasons = movieDetailsList.get(position);
        holder.simple_de.setText(tvSeasons.getSeasonNo());
        holder.episode.setText(tvSeasons.getEpisodes());
        holder.seDate.setText(tvSeasons.getDate());

    }

    @Override
    public int getItemCount() {
        return movieDetailsList.size();
    }

    static class myHolder extends RecyclerView.ViewHolder
    {
        TextView seDate,episode,simple_de;

        public myHolder(View itemView) {
            super(itemView);
            seDate = itemView.findViewById(R.id.seDate);
            episode = itemView.findViewById(R.id.episode);
            simple_de = itemView.findViewById(R.id.simple_de);
        }
    }
}
