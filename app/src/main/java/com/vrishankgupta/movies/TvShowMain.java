package com.vrishankgupta.movies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vrishankgupta.movies.Movies.UpcomingMovies;
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

    ListView lvTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_main);


        String type = getIntent().getExtras().getString("tvType");
        Log.d("Check", type);

        lvTv = findViewById(R.id.lvTv_show);
        lvTv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(TvShowMain.this,TvDetail.class);
                i.putExtra("TvIntent",(Tv)parent.getItemAtPosition(position));
                Log.d("mainInt", "onItemClick: ");
                startActivity(i);
            }
        });

        if(type.equals("On Air"))
            new tvTask().execute("https://api.themoviedb.org/3/tv/on_the_air?api_key=091aa3d78da969a59546613254d71896&language=en-US&page=1");

        else if(type.equals("Popular"))
            new tvTask().execute("https://api.themoviedb.org/3/tv/popular?api_key=091aa3d78da969a59546613254d71896&language=en-US&page=1");

        else if(type.equals("Top Rated"))
            new tvTask().execute("https://api.themoviedb.org/3/tv/top_rated?api_key=091aa3d78da969a59546613254d71896&language=en-US&page=1");


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
            JSONObject jsonObject;

            try {
                jsonObject = new JSONObject(s);

                ArrayList<Tv> upcomingMovies = new ArrayList<>();

                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for(int i = 0;i<jsonArray.length();i++)
                {
                    JSONObject object = jsonArray.getJSONObject(i);
                    Tv movies = new Tv();
                    movies.setOriginal_name(object.getString("original_name"));
                    movies.setOverview(object.getString("overview"));
                    movies.setId(object.getString("id"));
                    movies.setFirst_air_date(object.getString("first_air_date"));
                    movies.setVote_average(object.getString("vote_average"));
                    movies.setPoster_path(object.getString("poster_path"));
                    upcomingMovies.add(movies);
                }
                TvAdapter movieArrayAdapter = new TvAdapter(TvShowMain.this,R.layout.lv_detail,upcomingMovies);

                lvTv.setAdapter(movieArrayAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        }
 }

