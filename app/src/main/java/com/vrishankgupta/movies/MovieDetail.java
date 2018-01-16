package com.vrishankgupta.movies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vrishankgupta.movies.Movies.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MovieDetail extends AppCompatActivity {

    ImageView detailImage;
    TextView detailTitle,date,rating,overview,language;
    ImageView youButMovie;
    Button recommendMovie;
    String key;
    Boolean flag;
    Movies topRatedMovie;
    RatingBar ratingBarMovie;
    Movies movies;
    ScrollView scrollView;
    RecyclerView rv_movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        recommendMovie = findViewById(R.id.recommendMovie);
        detailImage = findViewById(R.id.detailImage);
        youButMovie = findViewById(R.id.youButMovie);
        language = findViewById(R.id.language);
        rv_movie = findViewById(R.id.rv_movie);
        scrollView = findViewById(R.id.movDet);
        ratingBarMovie = findViewById(R.id.ratingBarMovie);
        detailTitle = findViewById(R.id.detailTitle);
        date = findViewById(R.id.date);
        rating = findViewById(R.id.rating);
        overview = findViewById(R.id.overview);
        Picasso.with(this).load("http://icons.iconarchive.com/icons/dakirby309/simply-styled/256/YouTube-icon.png").into(youButMovie);


        if(MovieMain.t ==1 || MovieMain.t ==2)
        {

            topRatedMovie = (Movies) getIntent().getExtras().getSerializable("MOVIE_DETAILS_TOP");
            if(topRatedMovie != null)
            {
                setTitle(topRatedMovie.getOriginal_title());
                Picasso.with(this).load("https://image.tmdb.org/t/p/w500/" + topRatedMovie.getBackdrop_path()).into(detailImage);
                detailTitle.setText(topRatedMovie.getOriginal_title());
                date.setText(topRatedMovie.getRelease_date());
                rating.setText(rating.getText()+String.valueOf(topRatedMovie.getVote_average()));
                ratingBarMovie.setRating(Float.valueOf(topRatedMovie.getVote_average()));
                overview.setText(topRatedMovie.getOverview());
                language.setText(language.getText()+topRatedMovie.getLanguage());
                flag =false;
                new simTask().execute("https://api.themoviedb.org/3/movie/"+topRatedMovie.getId()+"/recommendations?api_key=091aa3d78da969a59546613254d71896&language=en-US&page=1");
                new myTask().execute("http://api.themoviedb.org/3/movie/"+topRatedMovie.getId() +"/videos?api_key=091aa3d78da969a59546613254d71896");

            }
            else
                Toast.makeText(this, "No TopRatedMovie or Popular", Toast.LENGTH_SHORT).show();
        }

        else if(MovieMain.t==3)
        {
            movies = (Movies) getIntent().getExtras().getSerializable("MOVIE_UPCOMING");
            if(movies != null)
            {
                setTitle(movies.getOriginal_title());
                Picasso.with(this).load("https://image.tmdb.org/t/p/w500/" + movies.getBackdrop_path()).into(detailImage);
                detailTitle.setText(movies.getOriginal_title());
                date.setText(movies.getRelease_date());
                rating.setText(rating.getText()+String.valueOf(movies.getVote_average()));
                ratingBarMovie.setRating(Float.valueOf(movies.getVote_average()));

                overview.setText(movies.getOverview());
                language.setText(language.getText() + movies.getLanguage());
                flag = true;
                new simTask().execute("https://api.themoviedb.org/3/movie/"+movies.getId()+"/recommendations?api_key=091aa3d78da969a59546613254d71896&language=en-US&page=1");
                new myTask().execute("http://api.themoviedb.org/3/movie/"+ movies.getId() +"/videos?api_key=091aa3d78da969a59546613254d71896");

            }
            else
                Toast.makeText(this, "No Upcoming", Toast.LENGTH_SHORT).show();
        }

        else
            Toast.makeText(this, "Some Error", Toast.LENGTH_SHORT).show();

        youButMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TYD", "onClick: ");
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
                startActivity(i);
            }
        });

        recommendMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag)
                {
                    Intent i = new Intent(MovieDetail.this,MovieMain.class);
                    i.putExtra("upcoming", movies.getId());
                    startActivity(i);
                }

                else
                {
                    Intent i = new Intent(MovieDetail.this,MovieMain.class);
                    i.putExtra("popular",topRatedMovie.getId());
                    startActivity(i);
                }

            }
        });

    }


    class myTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                HttpURLConnection urlConnection;
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String s = bufferedReader.readLine();
                bufferedReader.close();

                return s;

            } catch (IOException e) {
                Log.e("Error: ", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONObject jsonObject;

            try {
                jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                key = jsonObject1.getString("key");
                Log.d("Youtube", key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class simTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONObject jsonObject;

                ArrayList<Movies> upcomingMovies = new ArrayList<>();
            try {
                jsonObject = new JSONObject(s);


                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    Movies movies = new Movies();
                    movies.setOriginal_title(object.getString("original_title"));
                    movies.setId(object.getString("id"));
                    movies.setOverview(object.getString("overview"));
                    movies.setPopularity(object.getString("popularity"));
                    movies.setRelease_date(object.getString("release_date"));
                    movies.setVote_average(object.getString("vote_average"));
                    movies.setPoster_path(object.getString("poster_path"));
                    movies.setBackdrop_path(object.getString("backdrop_path"));
                    movies.setLanguage(object.getString("original_language"));
                    upcomingMovies.add(movies);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            SimilarMoviesAdapter adapter = new SimilarMoviesAdapter(getApplicationContext(),upcomingMovies);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
            rv_movie.setLayoutManager(mLayoutManager);
            rv_movie.setAdapter(adapter);

        }

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                HttpURLConnection urlConnection;
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String s = bufferedReader.readLine();
                bufferedReader.close();

                return s;

            } catch (IOException e) {
                Log.e("Error: ", e.getMessage(), e);
            }
            return null;
        }
    }

}
