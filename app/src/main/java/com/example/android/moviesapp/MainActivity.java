package com.example.android.moviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.moviesapp.utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkUtils.check();
    }
}
