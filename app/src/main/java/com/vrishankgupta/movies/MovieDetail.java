package com.vrishankgupta.movies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vrishankgupta.movies.Movies.TopRatedMovie;
import com.vrishankgupta.movies.Movies.UpcomingMovies;

public class MovieDetail extends AppCompatActivity {

    ImageView detailImage;
    TextView detailTitle,date,rating,popularity,overview;
    Button youButMovie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        detailImage = findViewById(R.id.detailImage);
        youButMovie = findViewById(R.id.youButMovie);

        detailTitle = findViewById(R.id.detailTitle);
        date = findViewById(R.id.date);
        rating = findViewById(R.id.rating);
        popularity = findViewById(R.id.popularity);
        overview = findViewById(R.id.overview);

        if(MovieMain.t ==1 || MovieMain.t ==2)
        {
            TopRatedMovie topRatedMovie = (TopRatedMovie) getIntent().getExtras().getSerializable("MOVIE_DETAILS_TOP");
            if(topRatedMovie != null)
            {
                Picasso.with(this).load("https://image.tmdb.org/t/p/w500/" + topRatedMovie.getPoster_path()).into(detailImage);
                detailTitle.setText(topRatedMovie.getOriginal_title());
                date.setText(topRatedMovie.getRelease_date());
                rating.setText(rating.getText()+String.valueOf(topRatedMovie.getVote_average()));
                popularity.setText(popularity.getText()+"Not Available");
                overview.setText(topRatedMovie.getOverview());
            }
            else
                Toast.makeText(this, "No TopRatedMovie or Popular", Toast.LENGTH_SHORT).show();
        }

        else if(MovieMain.t==3)
        {
            UpcomingMovies upcomingMovies = (UpcomingMovies) getIntent().getExtras().getSerializable("MOVIE_UPCOMING");
            if(upcomingMovies != null)
            {
                Picasso.with(this).load("https://image.tmdb.org/t/p/w500/" + upcomingMovies.getPoster_path()).into(detailImage);
                detailTitle.setText(upcomingMovies.getOriginal_title());
                date.setText(upcomingMovies.getRelease_date());
                rating.setText(rating.getText()+String.valueOf(upcomingMovies.getVote_average()));
                popularity.setText(popularity.getText()+upcomingMovies.getPopularity());
                overview.setText(upcomingMovies.getOverview());
            }
            else
                Toast.makeText(this, "No Upcoming", Toast.LENGTH_SHORT).show();
        }

        else
            Toast.makeText(this, "Some Error", Toast.LENGTH_SHORT).show();

    }
}
