package com.example.android.moviesapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.android.moviesapp.model.TrailersList;
import com.example.android.moviesapp.utilities.GetDataService;
import com.example.android.moviesapp.utilities.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrailersViewModel extends ViewModel {
    private static final String TAG = TrailersViewModel.class.getSimpleName();

    private MutableLiveData<TrailersList> trailersList;

    public LiveData<TrailersList> getTrailers(String movieID){
        if(trailersList == null){
            trailersList = new MutableLiveData<>();
            loadAllTrailers(movieID);
        }
        return trailersList;
    }

    private void loadAllTrailers(String movieId){
        GetDataService api = RetrofitClientInstance.getApiService();

        Call<TrailersList> call = api.getAllTrailers(movieId);

        call.enqueue(new Callback<TrailersList>() {
            @Override
            public void onResponse(Call<TrailersList> call, Response<TrailersList> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "Successful api call");
                    trailersList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TrailersList> call, Throwable t) {

            }
        });
    }
}
