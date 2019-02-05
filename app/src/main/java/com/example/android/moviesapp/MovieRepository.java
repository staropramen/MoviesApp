package com.example.android.moviesapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.android.moviesapp.model.MoviesList;
import com.example.android.moviesapp.utilities.GetDataService;
import com.example.android.moviesapp.utilities.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private GetDataService apiService;

    private static class SingletonHelper {
        private static final MovieRepository INSTANCE = new MovieRepository();
    }

    public static MovieRepository getInstance() {
        return SingletonHelper.INSTANCE;
    }
    public MovieRepository()
    {
        apiService= RetrofitClientInstance.getApiService();
    }

    public LiveData<MoviesList> getAllMovies(String path) {
        final MutableLiveData<MoviesList> data = new MutableLiveData<>();
        apiService.getAllMovies(path)
                .enqueue(new Callback<MoviesList>() {
                    @Override
                    public void onResponse(Call<MoviesList> call, Response<MoviesList> response) {
                        if(response.isSuccessful()){
                            data.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesList> call, Throwable t) {
                        data.setValue(null);
                    }
                });

        return data;
    }
}
