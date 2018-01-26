package com.vrishankgupta.movies;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vrishankgupta.movies.TvShow.Tv;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vrishankgupta on 09/01/18.
 */

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.vHolder>
{
    private ArrayList<Tv> tvList;
    private Context context;
    public TvAdapter(Context context, ArrayList<Tv> tvList) {
        this.tvList = tvList;
        this.context = context;

    }

    @Override
    public vHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.movie_card,parent,false);

        return new vHolder(itemView);
    }

    @Override
    public void onBindViewHolder(vHolder holder, int position) {

        Tv tv = tvList.get(position);
        holder.rating.setText(String.valueOf(tv.getVote_average()));
        holder.movieName.setText(tv.getOriginal_name());
        holder.edittext.setText(tv.getFirst_air_date());
        Picasso.with(context).load("https://image.tmdb.org/t/p/w500/"+ tv.getPoster_path()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return tvList.size();
    }

    public class vHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView movieName,rating,edittext;
        ImageView imageView;
        CardView cardView;
        public vHolder(View itemView) {
            super(itemView);

            movieName = itemView.findViewById(R.id.lvTv_card);
            rating = itemView.findViewById(R.id.lvRating_card);
            edittext = itemView.findViewById(R.id.editText_card);
            imageView = itemView.findViewById(R.id.image_card);
            cardView = itemView.findViewById(R.id.mov_cardView);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(context,TvDetail.class);
            i.putExtra("TvIntent", tvList.get(getPosition()));
            context.startActivity(i);
        }
    }
}