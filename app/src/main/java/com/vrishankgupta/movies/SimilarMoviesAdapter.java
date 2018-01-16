package com.vrishankgupta.movies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vrishankgupta.movies.Movies.Movies;

import java.util.ArrayList;

/**
 * Created by vrishankgupta on 16/01/18.
 */

public class SimilarMoviesAdapter extends RecyclerView.Adapter<SimilarMoviesAdapter.vHolder> {

    private Context context;
    private ArrayList <Movies> arrayList;

    public SimilarMoviesAdapter(Context context, ArrayList<Movies> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public vHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.similar_card,parent,false);
        return new vHolder(itemView);
    }

    @Override
    public void onBindViewHolder(vHolder holder, int position) {

        Movies movies = arrayList.get(position);
        holder.sim_rating.setText(movies.getVote_average());
        holder.sim_name.setText(movies.getOriginal_title());
        Picasso.with(context).load("https://image.tmdb.org/t/p/w500/"+ movies.getPoster_path()).into(holder.sim_image);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class vHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        ImageView sim_image;
        TextView sim_name,sim_rating;
        CardView cardView;
        public vHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.sim_card);
            sim_image = itemView.findViewById(R.id.sim_image);
            sim_name = itemView.findViewById(R.id.sim_name);
            cardView.setOnClickListener(this);
            sim_rating = itemView.findViewById(R.id.sim_rating);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(context,MovieDetail.class);
            i.putExtra("MOVIE_DETAILS_TOP",arrayList.get(getPosition()));
            context.startActivity(i);
        }
    }
}
