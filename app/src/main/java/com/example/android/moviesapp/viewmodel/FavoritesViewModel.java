package com.example.android.moviesapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.moviesapp.database.AppDatabase;
import com.example.android.moviesapp.model.Movie;

import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {

    private static final String TAG = FavoritesViewModel.class.getSimpleName();

    private LiveData<List<Movie>> movies;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Load movies from database");
        movies = database.movieDao().loadAllMovies();
    }

    public LiveData<List<Movie>> getMovies(){
        return movies;
    }
}
