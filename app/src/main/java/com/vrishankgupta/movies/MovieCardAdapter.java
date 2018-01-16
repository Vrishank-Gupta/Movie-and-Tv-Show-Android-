package com.vrishankgupta.movies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vrishankgupta.movies.Movies.Movies;

import java.util.ArrayList;

import static com.vrishankgupta.movies.MovieMain.t;

/**
 * Created by vrishankgupta on 16/01/18.
 */

public class MovieCardAdapter extends RecyclerView.Adapter<MovieCardAdapter.vHolder> {

    private ArrayList<Movies> movieDetailsList;

    private Context context;

    public MovieCardAdapter(ArrayList<Movies> movieDetailsList, Context context) {
        this.movieDetailsList = movieDetailsList;
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

        Movies movie = movieDetailsList.get(position);
        holder.rating.setText(String.valueOf(movie.getVote_average()));
        holder.movieName.setText(movie.getOriginal_title());
        holder.edittext.setText(movie.getRelease_date());
        Picasso.with(context).load("https://image.tmdb.org/t/p/w500/"+ movie.getPoster_path()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return movieDetailsList.size();
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

            Intent i = new Intent(context,MovieDetail.class);
            Log.d("Click hua", String.valueOf(getPosition()));
            if(t==1 ||t==2)
                i.putExtra("MOVIE_DETAILS_TOP", movieDetailsList.get(getPosition()));
            else
                    i.putExtra("MOVIE_UPCOMING",movieDetailsList.get(getPosition()));

            context.startActivity(i);

        }
    }
}
