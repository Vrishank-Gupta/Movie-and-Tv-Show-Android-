package com.vrishankgupta.movies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.vrishankgupta.movies.Movies.TopRatedMovie;
import com.vrishankgupta.movies.Movies.UpcomingMovies;

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
    static int t=0;
    ListView lvMovie;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_main);
        String upId = getIntent().getExtras().getString("upcoming");
        String topId = getIntent().getExtras().getString("popular");
        lvMovie = findViewById(R.id.lvMovie);

        if(upId == null && topId == null) {
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
        }

        else {
            if(upId !=null)
            {
                new titleTask().execute("https://api.themoviedb.org/3/movie/"+upId+"?api_key=091aa3d78da969a59546613254d71896&language=en-US");
                new upcomingTask().execute("https://api.themoviedb.org/3/movie/"+upId+"/recommendations?api_key=091aa3d78da969a59546613254d71896&language=en-US&page=1\n");
            }

            else if(topId !=null)
            {
                new titleTask().execute("https://api.themoviedb.org/3/movie/"+topId+"?api_key=091aa3d78da969a59546613254d71896&language=en-US");
                new TopRatedTask().execute("https://api.themoviedb.org/3/movie/"+topId+"/recommendations?api_key=091aa3d78da969a59546613254d71896&language=en-US&page=1\n");

            }
        }

        lvMovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MovieMain.this, MovieDetail.class);
                if(t==1 || t==2)
                intent.putExtra("MOVIE_DETAILS_TOP", (TopRatedMovie)parent.getItemAtPosition(position));

                else
                    intent.putExtra("MOVIE_UPCOMING",(UpcomingMovies)parent.getItemAtPosition(position));
                startActivity(intent);
            }
        });
    }

    class upcomingTask extends AsyncTask<String,Void,String>
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

                ArrayList<UpcomingMovies> upcomingMovies = new ArrayList<>();

                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for(int i = 0;i<jsonArray.length();i++)
                {
                    JSONObject object = jsonArray.getJSONObject(i);
                    UpcomingMovies movies = new UpcomingMovies();
                    movies.setOriginal_title(object.getString("original_title"));
                    movies.setId(object.getString("id"));
                    movies.setOverview(object.getString("overview"));
                    movies.setPopularity(object.getString("popularity"));
                    movies.setRelease_date(object.getString("release_date"));
                    movies.setVote_average(object.getString("vote_average"));
                    movies.setPoster_path(object.getString("poster_path"));
                    movies.setBackdrop_path(object.getString("backdrop_path"));
                    upcomingMovies.add(movies);
                }
                UpcomingMovieAdapter movieArrayAdapter = new UpcomingMovieAdapter(MovieMain.this,R.layout.lv_detail,upcomingMovies);

                lvMovie.setAdapter(movieArrayAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class TopRatedTask extends AsyncTask<String,Void,String>
    {
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

                ArrayList<TopRatedMovie> movieList = new ArrayList<>();

                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for (int i =0; i<jsonArray.length();i++)
                {
                    JSONObject object = jsonArray.getJSONObject(i);
                    TopRatedMovie movieDetails = new TopRatedMovie();
                    movieDetails.setOriginal_title(object.getString("original_title"));
                    movieDetails.setVote_average(object.getDouble("vote_average"));
                    movieDetails.setId(object.getString("id"));
                    movieDetails.setOverview(object.getString("overview"));
                    movieDetails.setRelease_date(object.getString("release_date"));
                    movieDetails.setPoster_path(object.getString("poster_path"));
                    movieDetails.setBackdrop_path(object.getString("backdrop_path"));
                    movieList.add(movieDetails);
                }

                MovieArrayAdapter movieArrayAdapter = new MovieArrayAdapter(MovieMain.this,R.layout.lv_detail,movieList);

                lvMovie.setAdapter(movieArrayAdapter);
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
                setTitle("Similar to "+jsonObject.getString("original_title"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}

