package com.vrishankgupta.movies;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
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

public class MovieMain extends AppCompatActivity {
    static int t = 0;
    RecyclerView lvm;
    RelativeLayout movMain;
    String type;
    RatingBar ratingBar;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_recyc);
        String upId = getIntent().getExtras().getString("upcoming");
        String topId = getIntent().getExtras().getString("popular");
        lvm = (RecyclerView) findViewById(R.id.mov_rec2);
        ratingBar = findViewById(R.id.ratingBarMovie);
        cardView = findViewById(R.id.mov_cardView);

        if (upId == null && topId == null) {
            type = getIntent().getExtras().getString("Type");


            if (type != null) {
                if (type.equals("Popular")) {
                    t = 1;
                    setTitle("Popular Movies");
                    type = null;
                } else if (type.equals("Top Rated")) {
                    t = 2;
                    type = null;
                    setTitle("Top Rated Movies");
                } else if (type.equals("Upcoming")) {
                    t = 3;
                    setTitle("Upcoming Movies");
                    type = null;
                } else
                    Toast.makeText(this, "Ohh,Snap!", Toast.LENGTH_SHORT).show();

                if (t == 1) {
                    new TopRatedTask().execute("https://api.themoviedb.org/3/movie/popular?api_key=091aa3d78da969a59546613254d71896&language=en-US&page=1");
                } else if (t == 2) {
                    new TopRatedTask().execute("https://api.themoviedb.org/3/movie/top_rated?api_key=091aa3d78da969a59546613254d71896&language=en-US&page=1");
                } else if (t == 3) {
                    new upcomingTask().execute("https://api.themoviedb.org/3/movie/upcoming?api_key=091aa3d78da969a59546613254d71896&language=en-US&page=1");
                }
            }
        } else {
            if (upId != null) {
                new titleTask().execute("https://api.themoviedb.org/3/movie/" + upId + "?api_key=091aa3d78da969a59546613254d71896&language=en-US");
                new upcomingTask().execute("https://api.themoviedb.org/3/movie/" + upId + "/recommendations?api_key=091aa3d78da969a59546613254d71896&language=en-US&page=1\n");
            } else if (topId != null) {
                new titleTask().execute("https://api.themoviedb.org/3/movie/" + topId + "?api_key=091aa3d78da969a59546613254d71896&language=en-US");
                new TopRatedTask().execute("https://api.themoviedb.org/3/movie/" + topId + "/recommendations?api_key=091aa3d78da969a59546613254d71896&language=en-US&page=1\n");

            }
        }
    }


    class upcomingTask extends AsyncTask<String, Void, String> {
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

                ArrayList<Movies> upcomingMovies = new ArrayList<>();

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

                MovieCardAdapter cardAdapter = new MovieCardAdapter(upcomingMovies, MovieMain.this);

                if (lvm != null) {
                    Log.d("Recycle", lvm.toString());
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    lvm.setLayoutManager(mLayoutManager);
                    lvm.setAdapter(cardAdapter);
                } else
                    Log.d("LVM", String.valueOf(R.id.mov_rec2));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class TopRatedTask extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
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

                ArrayList<Movies> movieList = new ArrayList<>();

                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    Movies movieDetails = new Movies();
                    movieDetails.setOriginal_title(object.getString("original_title"));
                    movieDetails.setVote_average(object.getString("vote_average"));
                    movieDetails.setId(object.getString("id"));
                    movieDetails.setOverview(object.getString("overview"));
                    movieDetails.setRelease_date(object.getString("release_date"));
                    movieDetails.setPoster_path(object.getString("poster_path"));
                    movieDetails.setBackdrop_path(object.getString("backdrop_path"));
                    movieDetails.setLanguage(object.getString("original_language"));
                    movieList.add(movieDetails);
                }

                MovieCardAdapter movieArrayAdapter = new MovieCardAdapter(movieList, getApplicationContext());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                lvm.setLayoutManager(mLayoutManager);

                lvm.setAdapter(movieArrayAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class titleTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
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


        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                setTitle("Similar to " + jsonObject.getString("original_title"));


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}


