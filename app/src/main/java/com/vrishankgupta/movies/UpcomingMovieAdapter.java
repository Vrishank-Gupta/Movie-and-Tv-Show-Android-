package com.vrishankgupta.movies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vrishankgupta.movies.Movies.UpcomingMovies;

import java.util.List;

/**
 * Created by vrishankgupta on 08/01/18.
 */

public class UpcomingMovieAdapter extends ArrayAdapter {
    private List<UpcomingMovies> movieDetailsList;

    private int resource;

    private Context context;


    public UpcomingMovieAdapter(Context context, int resource, List<UpcomingMovies> movieDetails) {
        super(context, resource, movieDetails);
        this.context = context;
        this.movieDetailsList = movieDetails;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        UpcomingMovies details = movieDetailsList.get(position);

        View view = LayoutInflater.from(context).inflate(resource,parent,false);

        TextView movieName = view.findViewById(R.id.lvTv);
        ImageView image = view.findViewById(R.id.lvImage);

        movieName.setText(details.getOriginal_title());

        Picasso.with(context).load("https://image.tmdb.org/t/p/w500/"+ details.getPoster_path()).into(image);


        return  view;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return movieDetailsList.get(position);
    }
}
