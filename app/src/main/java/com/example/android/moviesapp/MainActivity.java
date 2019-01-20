package com.example.android.moviesapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.android.moviesapp.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView tvDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = (TextView) findViewById(R.id.tv_display);

        //Todo delete later
        String myPreference = "popular";

        // TODO Debug Function Delete later
        new FetchMovieTask().execute(myPreference);

    }

    //Asynch Task to Load Data from API
    public class FetchMovieTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            //Return null if param is empty
            if(params.length == 0){
                return null;
            }

            String preferredOrder = params[0];
            URL moviesRequestUrl = NetworkUtils.builtUrl(preferredOrder);

            try {
                String jsonResponse = NetworkUtils.getHttpResponse(moviesRequestUrl);
                return jsonResponse;
            }catch (IOException e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String movieJson) {
            if(movieJson != null){
                tvDisplay.setText(movieJson);
            }
        }
    }
}
