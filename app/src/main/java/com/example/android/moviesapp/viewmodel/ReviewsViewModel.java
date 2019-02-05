package com.example.android.moviesapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.android.moviesapp.model.ReviewsList;
import com.example.android.moviesapp.model.TrailersList;
import com.example.android.moviesapp.utilities.GetDataService;
import com.example.android.moviesapp.utilities.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewsViewModel extends ViewModel {
    private static final String TAG = ReviewsViewModel.class.getSimpleName();

    private MutableLiveData<ReviewsList> reviewsList;

    public LiveData<ReviewsList> getReviews(String movieID){
        if(reviewsList == null){
            reviewsList = new MutableLiveData<>();
            loadAllReviews(movieID);
        }
        return reviewsList;
    }

    private void loadAllReviews(String movieId){
        GetDataService api = RetrofitClientInstance.getApiService();

        Call<ReviewsList> call = api.getAllReviews(movieId);

        call.enqueue(new Callback<ReviewsList>() {
            @Override
            public void onResponse(Call<ReviewsList> call, Response<ReviewsList> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "Successful api call");
                    reviewsList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ReviewsList> call, Throwable t) {

            }
        });
    }
}
