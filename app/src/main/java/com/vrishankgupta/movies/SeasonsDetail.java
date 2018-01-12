package com.vrishankgupta.movies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.vrishankgupta.movies.TvShow.Tv;
import com.vrishankgupta.movies.TvShow.TvSeasons;

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

public class SeasonsDetail extends AppCompatActivity {

    Tv movies;
    RecyclerView seasonLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season_detail);
        seasonLv = findViewById(R.id.seasonLv);

        String id;
        if(getIntent().hasExtra("For_season")) {
            id = getIntent().getExtras().getString("For_season");
            new seasonTask().execute("https://api.themoviedb.org/3/tv/"+id+"?api_key=091aa3d78da969a59546613254d71896&language=en-US");
        }
        else
        {
            Toast.makeText(this, "Errr", Toast.LENGTH_SHORT).show();
        }
    }


    class seasonTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("seasons");
                ArrayList<TvSeasons> seasonList = new ArrayList<>();

                for (int i =0;i<jsonArray.length();i++)
                {
                    JSONObject object = jsonArray.getJSONObject(i);
                    TvSeasons tvSeasons = new TvSeasons();
                    tvSeasons.setId(object.getString("id"));
                    tvSeasons.setDate(object.getString("air_date"));
                    tvSeasons.setEpisodes(object.getString("episode_count"));
                    tvSeasons.setSeasonNo(object.getString("season_number"));
                    seasonList.add(tvSeasons);
                }

                SeasonAdapter seasonAdapter = new SeasonAdapter(seasonList,SeasonsDetail.this);
                seasonLv.setLayoutManager(new LinearLayoutManager(SeasonsDetail.this));
                seasonLv.setAdapter(seasonAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
    }
}
