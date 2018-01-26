package com.vrishankgupta.movies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Entertainment");
        setContentView(R.layout.welcome);

        Handler h = new Handler();

        Runnable r = new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(
                                        welcome.this,
                                        Option.class
                                        );
                startActivity(i);
                finish();
            }
        };

        h.postDelayed(r,3000);
    }

}