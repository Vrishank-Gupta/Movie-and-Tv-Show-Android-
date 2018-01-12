package com.vrishankgupta.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;

public class Option extends AppCompatActivity {
    TextView tvOption,movieOption;
    EditText searchEt;
    ImageView searchBut;

    public static boolean isConnected() throws InterruptedException, IOException
    {
        String command = "ping -c 1 google.com";
        return (Runtime.getRuntime().exec (command).waitFor() == 0);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        setTitle("Choose Option");
        tvOption = findViewById(R.id.tvOption);
        movieOption = findViewById(R.id.movieOption);

        searchBut = findViewById(R.id.searchBut);
        Picasso.with(this).load("http://icons.iconarchive.com/icons/iconleak/atrous/256/search-icon.png").into(searchBut);
        searchEt = findViewById(R.id.searchEt);
        searchEt.setText("");
        try {
            if (!isConnected())
            {
                Toast.makeText(this, "No connectivity", Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        searchBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (!isConnected())
                        Toast.makeText(Option.this, "No Connectivity", Toast.LENGTH_SHORT).show();
                    else
                    {
                        if (searchEt.getText() == null || searchEt.getText().toString().equals("")) {
                            Toast.makeText(Option.this, "Enter Search Data", Toast.LENGTH_SHORT).show();
                        } else {
                            String query = String.valueOf(searchEt.getText()).replaceAll(" ", "%20");
                            Intent i = new Intent(Option.this, SearchActivity.class);
                            i.putExtra("query", query);
                            startActivity(i);
                        }
                    }
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        });


        movieOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!isConnected())
                        Toast.makeText(Option.this, "No Connectivity", Toast.LENGTH_SHORT).show();
                    else {
                        Intent i = new Intent(Option.this, MovieOption.class);
                        startActivity(i);
                    }
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        });


        tvOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!isConnected())
                        Toast.makeText(Option.this, "No Connectivity", Toast.LENGTH_SHORT).show();
                    else {
                        Intent i = new Intent(Option.this, TvOption.class);
                        startActivity(i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
