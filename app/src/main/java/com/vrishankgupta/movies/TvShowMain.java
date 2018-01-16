package com.vrishankgupta.movies;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.vrishankgupta.movies.TvShow.Tv;

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

public class TvShowMain extends AppCompatActivity {

    RecyclerView lvTv;
    Tv movies;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_recyc);

        String tvId = getIntent().getExtras().getString("TvRecommend");
        lvTv = findViewById(R.id.mov_rec2);


        if (tvId == null) {
            String type = getIntent().getExtras().getString("tvType");

            if (type.equals("On Air")) {
                setTitle("On Air TV Shows");
                new tvTask().execute("https://api.themoviedb.org/3/tv/on_the_air?api_key=091aa3d78da969a59546613254d71896&language=en-US&page=1");
            }
            else if (type.equals("Popular"))
            {
                setTitle("Popular TV Shows");
                new tvTask().execute("https://api.themoviedb.org/3/tv/popular?api_key=091aa3d78da969a59546613254d71896&language=en-US&page=1");
            }

            else if (type.equals("Top Rated"))
            {
                setTitle("Top Rated TV Shows");
                new tvTask().execute("https://api.themoviedb.org/3/tv/top_rated?api_key=091aa3d78da969a59546613254d71896&language=en-US&page=1");
            }
        }

        else
        {
            new titleTask().execute("https://api.themoviedb.org/3/tv/" + tvId+ "?api_key=091aa3d78da969a59546613254d71896&language=en-US");
            new tvTask().execute("https://api.themoviedb.org/3/tv/"+ tvId+ "/recommendations?api_key=091aa3d78da969a59546613254d71896&language=en-US&page=1");

        }

    }

    class tvTask extends AsyncTask<String,Void,String>
    {
        @Override
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

                ArrayList<Tv> upcomingMovies = new ArrayList<>();

                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for(int i = 0;i<jsonArray.length();i++)
                {

                    JSONObject object = jsonArray.getJSONObject(i);
                    movies = new Tv();
                    movies.setOriginal_name(object.getString("original_name"));
                    movies.setOverview(object.getString("overview"));
                    movies.setId(object.getString("id"));
                    movies.setFirst_air_date(object.getString("first_air_date"));
                    movies.setVote_average(object.getString("vote_average"));
                    movies.setPoster_path(object.getString("poster_path"));
                    movies.setBackdrop_path(object.getString("backdrop_path"));
                    movies.setLanguage(object.getString("original_language"));
                    upcomingMovies.add(movies);
                }
                TvAdapter movieArrayAdapter = new TvAdapter(getApplicationContext(), upcomingMovies);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                lvTv.setLayoutManager(mLayoutManager);

                lvTv.setAdapter(movieArrayAdapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    class titleTask extends AsyncTask<String,Void,String>
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
                HttpURLConnection urlConnection = null;
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
            try {
                JSONObject jsonObject = new JSONObject(s);
                setTitle("Similar to "+jsonObject.getString("original_name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


 }

