package com.vrishankgupta.movies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        String query = getIntent().getExtras().getString("query");
        lv = findViewById(R.id.searchLv);
        setTitle("Search result for:- " + query.replaceAll("%20"," "));
        new myTask().execute("https://api.themoviedb.org/3/search/multi?api_key=091aa3d78da969a59546613254d71896&query="+query+"&page=1&include_adult=true");
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SearchActivity.this,SearchDetail.class);
                i.putExtra("searchIntent",(SearchItem)parent.getItemAtPosition(position));
                startActivity(i);
            }
        });
    }


    class myTask extends AsyncTask<String,Void,String>
    {
        @Override

        protected String doInBackground(String... strings) {

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String s = bufferedReader.readLine();
                bufferedReader.close();
                return s;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONObject jsonObject;

            try {
                jsonObject = new JSONObject(s);
                ArrayList<SearchItem> searchItems = new ArrayList<>();
               JSONArray jsonArray =  jsonObject.getJSONArray("results");

                for (int i =0; i<jsonArray.length();i++)
                {
                    JSONObject object = jsonArray.getJSONObject(i);
                    SearchItem searchItem = new SearchItem();

                    if(object.getString("backdrop_path") == null || object.getString("backdrop_path").equals("") )
                    {
                        searchItem.setBackdrop_path(object.getString("poster_path"));
                    }
                    else
                      searchItem.setBackdrop_path(object.getString("backdrop_path"));
                    searchItem.setOriginal_language(object.getString("original_language"));
                    searchItem.setOverview(object.getString("overview"));
                    searchItem.setPoster_path(object.getString("poster_path"));
                    if(object.getString("media_type").equals("movie"))
                    {
                        searchItem.setRelease_date(object.getString("release_date"));
                        searchItem.setOriginal_title(object.getString("original_title"));
                    }
                    else {
                        searchItem.setRelease_date(object.getString("first_air_date"));
                        searchItem.setOriginal_title(object.getString("original_name"));
                    }
                    searchItem.setVote_average(object.getString("vote_average"));
                    searchItem.setId(object.getString("id"));
                    searchItem.setType(object.getString("media_type"));
                    searchItems.add(searchItem);

                }

                SearchAdapter movieArrayAdapter = new SearchAdapter(SearchActivity.this,R.layout.lv_detail,searchItems);
                lv.setAdapter(movieArrayAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
