package com.vrishankgupta.movies;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Option extends AppCompatActivity {
    TextView tvOption,movieOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        tvOption = findViewById(R.id.tvOption);
        movieOption = findViewById(R.id.movieOption);

        movieOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Option.this,MovieOption.class);
                startActivity(i);
            }
        });


        tvOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Option.this,TvOption.class);
                startActivity(i);
            }
        });
    }
}
