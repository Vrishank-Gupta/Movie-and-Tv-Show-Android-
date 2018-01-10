package com.vrishankgupta.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Option extends AppCompatActivity {
    TextView tvOption,movieOption;
    EditText searchEt;
    ImageButton searchBut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        setTitle("Choose Option");
        tvOption = findViewById(R.id.tvOption);
        movieOption = findViewById(R.id.movieOption);

        searchBut = findViewById(R.id.searchBut);
        searchEt = findViewById(R.id.searchEt);
        searchEt.setText("");

        searchBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchEt.getText() ==null || searchEt.getText().toString().equals(""))
                {
                    Toast.makeText(Option.this, "Enter Search Data", Toast.LENGTH_SHORT).show();
                }
                else {
                    String query = String.valueOf(searchEt.getText());
                    Intent i = new Intent(Option.this, SearchActivity.class);
                    i.putExtra("query", query);
                    startActivity(i);
                }
            }
        });


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
