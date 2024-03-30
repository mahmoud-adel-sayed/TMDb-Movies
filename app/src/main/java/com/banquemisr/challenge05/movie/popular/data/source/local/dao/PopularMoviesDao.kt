package com.banquemisr.challenge05.movie.popular.data.source.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.banquemisr.challenge05.movie.popular.data.source.local.entity.PopularMovieEntity

@Dao
interface PopularMoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<PopularMovieEntity>)

    @Query("SELECT * from popular_movies Order By id ASC")
    fun getPagingMovies(): PagingSource<Int, PopularMovieEntity>

    @Query("SELECT * from popular_movies")
    suspend fun getPopularMovies(): List<PopularMovieEntity>

    @Query("DELETE FROM popular_movies")
    suspend fun clearAll()
}