package com.vrishankgupta.movies;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class MovieOption extends AppCompatActivity {

    ArrayList<String> mOptions = new ArrayList<>();
    myAdapter adapter;
    ListView movieType;
    RelativeLayout movOpt;
    TextView tv;
    public static final String TAG = "Check";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_option);
        tv = findViewById(R.id.title);
        movOpt = findViewById(R.id.movOpt);

        Picasso.with(this).load("https://i.stack.imgur.com/DoNjc.png").into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                movOpt.setBackground(new BitmapDrawable(bitmap));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        setTitle("Movies");
        mOptions.add("Popular");
        mOptions.add("Top Rated");
        mOptions.add("Upcoming");
        adapter = new myAdapter();
        movieType = findViewById(R.id.movieType);
        movieType.setAdapter(adapter);
        movieType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MovieOption.this, MovieMain.class);
                intent.putExtra("Type", (String) parent.getItemAtPosition(position));
                Log.d(TAG, (String) parent.getItemAtPosition(position));
                startActivity(intent);
            }
        });


    }

    class myAdapter extends BaseAdapter
    {
        @Override
        public int getCount() {
            return mOptions.size();
        }

        @Override
        public String getItem(int position) {
            return mOptions.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater li = getLayoutInflater();
            View itemView = li.inflate(R.layout.det,parent,false);

            String curr = getItem(position);

            TextView tv = itemView.findViewById(R.id.simple_de);
            tv.setText(curr);
            return itemView;
        }
    }
}
