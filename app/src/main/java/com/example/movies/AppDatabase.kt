package com.example.movies

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movies.movie.nowplaying.data.source.local.dao.NowPlayingMoviesDao
import com.example.movies.movie.nowplaying.data.source.local.dao.NowPlayingRemoteKeysDao
import com.example.movies.movie.nowplaying.data.source.local.entity.NowPlayingMovieEntity
import com.example.movies.movie.nowplaying.data.source.local.entity.NowPlayingRemoteKeyEntity
import com.example.movies.movie.popular.data.source.local.dao.PopularMoviesDao
import com.example.movies.movie.popular.data.source.local.dao.PopularRemoteKeysDao
import com.example.movies.movie.popular.data.source.local.entity.PopularMovieEntity
import com.example.movies.movie.popular.data.source.local.entity.PopularRemoteKeyEntity
import com.example.movies.movie.upcoming.data.source.local.dao.UpcomingMoviesDao
import com.example.movies.movie.upcoming.data.source.local.dao.UpcomingRemoteKeysDao
import com.example.movies.movie.upcoming.data.source.local.entity.UpcomingMovieEntity
import com.example.movies.movie.upcoming.data.source.local.entity.UpcomingRemoteKeyEntity

@Database(
    entities = [
        PopularMovieEntity::class,
        PopularRemoteKeyEntity::class,
        NowPlayingMovieEntity::class,
        NowPlayingRemoteKeyEntity::class,
        UpcomingMovieEntity::class,
        UpcomingRemoteKeyEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun popularMoviesDao(): PopularMoviesDao
    abstract fun popularRemoteKeyDao(): PopularRemoteKeysDao
    abstract fun nowPlayingMoviesDao(): NowPlayingMoviesDao
    abstract fun nowPlayingRemoteKeyDao(): NowPlayingRemoteKeysDao
    abstract fun upcomingMoviesDao(): UpcomingMoviesDao
    abstract fun upcomingRemoteKeyDao(): UpcomingRemoteKeysDao
}