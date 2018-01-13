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
import com.vrishankgupta.movies.TvShow.Tv;

import java.util.List;

/**
 * Created by vrishankgupta on 09/01/18.
 */

public class TvAdapter extends ArrayAdapter {

    private List<Tv> tvList;
    private int resource;
    private Context context;


    public TvAdapter(Context context, int resource, List<Tv> tvList) {
        super(context, resource, tvList);
        this.context = context;
        this.tvList = tvList;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Tv details = tvList.get(position);
        View view = LayoutInflater.from(context).inflate(resource,parent,false);
        TextView movieName = view.findViewById(R.id.lvTv);
        ImageView image = view.findViewById(R.id.lvImage);
        TextView lvRating = view.findViewById(R.id.lvRating);
        TextView editText = view.findViewById(R.id.editText);
        editText.setText(details.getFirst_air_date());
        lvRating.setText(String.valueOf(details.getVote_average()));
        movieName.setText(details.getOriginal_name());
        Picasso.with(context).load("https://image.tmdb.org/t/p/w500/"+ details.getPoster_path()).into(image);
        return  view;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return tvList.get(position);
    }
}
