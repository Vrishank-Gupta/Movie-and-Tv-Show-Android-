package com.vrishankgupta.movies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vrishankgupta.movies.Search.SearchItem;
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

public class SearchDetail extends AppCompatActivity {

    ImageView detailImage1;
    TextView detailTitle1,date1,rating1,overview1,language;
    String key;
    ImageView youButTv;
    Button recommendTv;
    SearchItem show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);
        detailImage1 = findViewById(R.id.detailImage2);
        youButTv = findViewById(R.id.youButTv1);
        detailTitle1 = findViewById(R.id.detailTitle2);
        date1 = findViewById(R.id.date2);
        rating1 = findViewById(R.id.rating2);
        language = findViewById(R.id.language2);
        recommendTv = findViewById(R.id.recommendTv1);
        overview1 = findViewById(R.id.overview2);

        show = (SearchItem)getIntent().getExtras().getSerializable("searchIntent");

        if(show !=null)
        {
            setTitle(show.getOriginal_title());
            Picasso.with(this).load("https://image.tmdb.org/t/p/w500/" + show.getBackdrop_path()).into(detailImage1);
            detailTitle1.setText(show.getOriginal_title());
            date1.setText(show.getRelease_date());
            rating1.setText(rating1.getText()+String.valueOf(show.getVote_average()));
            overview1.setText(show.getOverview());
            language.setText(language.getText()+show.getOriginal_language());
            if(show.getType().equals("tv"))
                new myTask().execute("http://api.themoviedb.org/3/tv/"+show.getId() +"/videos?api_key=091aa3d78da969a59546613254d71896");

            else if(show.getType().equals("movie"))
            {
               new myTask().execute("http://api.themoviedb.org/3/movie/"+show.getId() +"/videos?api_key=091aa3d78da969a59546613254d71896");
            }
        }
        else
            Toast.makeText(this, "Some Error", Toast.LENGTH_SHORT).show();

        youButTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TYD", "onClick: ");
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
                startActivity(i);
            }
        });

        recommendTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(show.getType().equals("movie")) {
                    Intent i;
                    i = new Intent(SearchDetail.this, MovieMain.class);
                    i.putExtra("popular", show.getId());
                    startActivity(i);
                }
                if(show.getType().equals("tv"))
                {
                    Intent i;
                    i = new Intent(SearchDetail.this, TvShowMain.class);
                    i.putExtra("TvRecommend", show.getId());
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
//                INTENT for Youtube

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
