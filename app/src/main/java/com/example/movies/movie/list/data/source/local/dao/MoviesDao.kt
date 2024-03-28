package com.example.movies.movie.list.data.source.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.movie.list.data.source.local.entity.MovieEntity

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Query("SELECT * from movies Order By id ASC")
    fun getPagingMovies(): PagingSource<Int, MovieEntity>

    @Query("DELETE FROM movies")
    suspend fun clearAll()
}