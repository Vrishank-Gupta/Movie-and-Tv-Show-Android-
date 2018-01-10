package com.vrishankgupta.movies;

import android.content.Intent;
import android.graphics.Movie;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class MovieDetail extends AppCompatActivity {

    ImageView detailImage;
    TextView detailTitle,date,rating,overview,language;
    ImageButton youButMovie;
    Button recommendMovie;
    String key;
    Boolean flag;
    Movies topRatedMovie;
    Movies movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        recommendMovie = findViewById(R.id.recommendMovie);
        detailImage = findViewById(R.id.detailImage);
        youButMovie = findViewById(R.id.youButMovie);
        language = findViewById(R.id.language);

        detailTitle = findViewById(R.id.detailTitle);
        date = findViewById(R.id.date);
        rating = findViewById(R.id.rating);
        overview = findViewById(R.id.overview);

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
                overview.setText(topRatedMovie.getOverview());
                language.setText(language.getText()+topRatedMovie.getLanguage());
                flag =false;
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
                overview.setText(movies.getOverview());
                language.setText(language.getText() + movies.getLanguage());
                flag = true;
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
}
