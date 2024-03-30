package com.banquemisr.challenge05

import androidx.room.Database
import androidx.room.RoomDatabase
import com.banquemisr.challenge05.movie.nowplaying.data.source.local.dao.NowPlayingMoviesDao
import com.banquemisr.challenge05.movie.nowplaying.data.source.local.dao.NowPlayingRemoteKeysDao
import com.banquemisr.challenge05.movie.nowplaying.data.source.local.entity.NowPlayingMovieEntity
import com.banquemisr.challenge05.movie.nowplaying.data.source.local.entity.NowPlayingRemoteKeyEntity
import com.banquemisr.challenge05.movie.popular.data.source.local.dao.PopularMoviesDao
import com.banquemisr.challenge05.movie.popular.data.source.local.dao.PopularRemoteKeysDao
import com.banquemisr.challenge05.movie.popular.data.source.local.entity.PopularMovieEntity
import com.banquemisr.challenge05.movie.popular.data.source.local.entity.PopularRemoteKeyEntity
import com.banquemisr.challenge05.movie.upcoming.data.source.local.dao.UpcomingMoviesDao
import com.banquemisr.challenge05.movie.upcoming.data.source.local.dao.UpcomingRemoteKeysDao
import com.banquemisr.challenge05.movie.upcoming.data.source.local.entity.UpcomingMovieEntity
import com.banquemisr.challenge05.movie.upcoming.data.source.local.entity.UpcomingRemoteKeyEntity

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