package com.example.android.moviesapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.example.android.moviesapp.model.Movie;
import com.example.android.moviesapp.model.MoviesList;
import com.example.android.moviesapp.utilities.GetDataService;
import com.example.android.moviesapp.utilities.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularViewModel extends ViewModel {
    private static final String TAG = PopularViewModel.class.getSimpleName();
    private String SORT_ORDER = "popular";

    private MutableLiveData<MoviesList> moviesList;


    public LiveData<MoviesList> getMovies(){
        if(moviesList == null){
            moviesList = new MutableLiveData<>();
            loadAllMovies();
        }
        return moviesList;
    }

    private void loadAllMovies(){
        GetDataService api = RetrofitClientInstance.getApiService();

        Call<MoviesList> call = api.getAllMovies(SORT_ORDER);

        call.enqueue(new Callback<MoviesList>() {
            @Override
            public void onResponse(Call<MoviesList> call, Response<MoviesList> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "Successful api call");
                    moviesList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MoviesList> call, Throwable t) {
            }
        });
    }
}
