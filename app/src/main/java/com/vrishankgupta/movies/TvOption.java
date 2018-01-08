package com.vrishankgupta.movies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TvOption extends AppCompatActivity {

    ArrayList<String> tvOptions = new ArrayList<>();
    MyAdapter adapter;
    ListView tvType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_option);


        tvOptions.add("Latest");
        tvOptions.add("Top Rated");
        tvOptions.add("Upcoming");
        adapter = new MyAdapter();
        tvType = findViewById(R.id.tvType);
        tvType.setAdapter(adapter);


    }

    class MyAdapter extends BaseAdapter
    {
        @Override
        public int getCount() {
            return tvOptions.size();
        }

        @Override
        public String getItem(int position) {
            return tvOptions.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater li = getLayoutInflater();
            View itemView = li.inflate(R.layout.simple_detail,parent,false);

            String curr = getItem(position);

            TextView tv = itemView.findViewById(R.id.simple_de);
            tv.setText(curr);
            return itemView;
        }
    }
}
