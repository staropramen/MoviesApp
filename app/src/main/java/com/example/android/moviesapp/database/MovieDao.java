package com.example.android.moviesapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.android.moviesapp.model.Movie;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie ORDER BY movie_id")
    List<Movie> loadAllMovies();

    @Query("SELECT * FROM movie WHERE movie_id = :movie_id")
    Movie checkForMovie(String movie_id);

    @Insert
    void insertMovie(Movie movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Query("DELETE FROM movie WHERE movie_id = :movie_id")
    void deleteByMovieId(String movie_id);
}
