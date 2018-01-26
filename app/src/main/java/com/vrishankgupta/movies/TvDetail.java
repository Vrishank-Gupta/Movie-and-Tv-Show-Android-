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

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;
import com.vrishankgupta.movies.TvShow.SimilarTvAdapter;
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

public class TvDetail extends YouTubeBaseActivity {

    ImageView detailImage1;
    TextView detailTitle1,date1,rating1,overview1,language;
    String key;
    ImageView youButTv;
    Button recommendTv,seasons;
    RatingBar ratingBarTv;
    Tv show;
    ScrollView tvDet;
    RecyclerView rv_tv;
    YouTubePlayerView videoView;
    YouTubePlayer.OnInitializedListener onInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_detail);
        detailImage1 = findViewById(R.id.detailImage1);
        youButTv = findViewById(R.id.youButTv);
        detailTitle1 = findViewById(R.id.detailTitle1);
        date1 = findViewById(R.id.date1);
        ratingBarTv = findViewById(R.id.ratingBarTv);
        rating1 = findViewById(R.id.rating1);
        tvDet = findViewById(R.id.tvDet);
        seasons = findViewById(R.id.seasons);
        videoView=findViewById(R.id.youtube_player_tv);
        rv_tv = findViewById(R.id.rv_tv);
        language = findViewById(R.id.language1);
        recommendTv = findViewById(R.id.recommendTv);
        overview1 = findViewById(R.id.overview1);
//        Picasso.with(this).load("http://icons.iconarchive.com/icons/dakirby309/simply-styled/256/YouTube-icon.png").into(youButTv);


        show = (Tv)getIntent().getExtras().getSerializable("TvIntent");



        if(show !=null)
        {
            setTitle(show.getOriginal_name());
            Picasso.with(this).load("https://image.tmdb.org/t/p/w500/" + show.getBackdrop_path()).into(detailImage1);
            detailTitle1.setText(show.getOriginal_name());
            date1.setText(show.getFirst_air_date());
            rating1.setText(rating1.getText()+String.valueOf(show.getVote_average()));
            overview1.setText(show.getOverview());
            language.setText(language.getText()+show.getLanguage());
            ratingBarTv.setRating(Float.valueOf(show.getVote_average()));
            new simTask().execute("https://api.themoviedb.org/3/tv/"+show.getId()+"/recommendations?api_key=091aa3d78da969a59546613254d71896&language=en-US&page=1");
            new myTask().execute("http://api.themoviedb.org/3/tv/"+show.getId() +"/videos?api_key=091aa3d78da969a59546613254d71896");
        }
        else
            Toast.makeText(this, "Some Error", Toast.LENGTH_SHORT).show();


        recommendTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TvDetail.this,TvShowMain.class);
                i.putExtra("TvRecommend",show.getId());
                startActivity(i);
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
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                key = jsonObject1.getString("key");
                Log.d("Youtube", key);

                onInitializedListener = new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.loadVideo(key);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                    }
                };

                videoView.initialize(PlayerConfig.API_KEY,onInitializedListener);

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

            ArrayList<Tv> upcomingMovies = new ArrayList<>();
            try {
                jsonObject = new JSONObject(s);


                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    show = new Tv();
                    show.setOriginal_name(object.getString("original_name"));
                    show.setOverview(object.getString("overview"));
                    show.setId(object.getString("id"));
                    show.setFirst_air_date(object.getString("first_air_date"));
                    show.setVote_average(object.getString("vote_average"));
                    show.setPoster_path(object.getString("poster_path"));
                    show.setBackdrop_path(object.getString("backdrop_path"));
                    show.setLanguage(object.getString("original_language"));
                    upcomingMovies.add(show);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            SimilarTvAdapter adapter = new SimilarTvAdapter(getApplicationContext(),upcomingMovies);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
            rv_tv.setLayoutManager(mLayoutManager);
            rv_tv.setAdapter(adapter);


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