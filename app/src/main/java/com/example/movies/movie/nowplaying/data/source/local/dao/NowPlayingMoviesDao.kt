package com.example.movies.movie.nowplaying.data.source.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.movie.nowplaying.data.source.local.entity.NowPlayingMovieEntity

@Dao
interface NowPlayingMoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<NowPlayingMovieEntity>)

    @Query("SELECT * from now_playing_movies Order By id ASC")
    fun getPagingMovies(): PagingSource<Int, NowPlayingMovieEntity>

    @Query("DELETE FROM now_playing_movies")
    suspend fun clearAll()
}