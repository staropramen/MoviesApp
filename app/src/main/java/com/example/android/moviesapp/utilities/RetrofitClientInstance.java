package com.example.android.moviesapp.utilities;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    //Base URL for API request
    private static final String MOVIE_DATABASE_URL_POPULAR =
            "http://api.themoviedb.org/3/movie/";

    private static Retrofit getRetrofitInstance() {

        return new Retrofit.Builder()
                .baseUrl(MOVIE_DATABASE_URL_POPULAR)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static GetDataService getApiService() {
        return getRetrofitInstance().create(GetDataService.class);
    }
}


