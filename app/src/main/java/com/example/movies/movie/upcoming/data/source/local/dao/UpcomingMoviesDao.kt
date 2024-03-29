package com.example.movies.movie.upcoming.data.source.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.movie.upcoming.data.source.local.entity.UpcomingMovieEntity

@Dao
interface UpcomingMoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<UpcomingMovieEntity>)

    @Query("SELECT * from upcoming_movies Order By id ASC")
    fun getPagingMovies(): PagingSource<Int, UpcomingMovieEntity>

    @Query("DELETE FROM upcoming_movies")
    suspend fun clearAll()
}