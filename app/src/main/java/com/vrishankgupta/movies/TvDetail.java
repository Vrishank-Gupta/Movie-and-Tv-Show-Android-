package com.vrishankgupta.movies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vrishankgupta.movies.TvShow.Tv;

public class TvDetail extends AppCompatActivity {

    ImageView detailImage1;
    TextView detailTitle1,date1,rating1,overview1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_detail);
        detailImage1 = findViewById(R.id.detailImage1);

        detailTitle1 = findViewById(R.id.detailTitle1);
        date1 = findViewById(R.id.date1);
        rating1 = findViewById(R.id.rating1);
        overview1 = findViewById(R.id.overview1);

        Tv show = (Tv)getIntent().getExtras().getSerializable("TvIntent");

        if(show !=null)
        {
            Picasso.with(this).load("https://image.tmdb.org/t/p/w500/" + show.getPoster_path()).into(detailImage1);
            detailTitle1.setText(show.getOriginal_name());
            date1.setText(show.getFirst_air_date());
            rating1.setText(rating1.getText()+String.valueOf(show.getVote_average()));
            overview1.setText(show.getOverview());
        }
        else
            Toast.makeText(this, "Some Error", Toast.LENGTH_SHORT).show();
    }
}
