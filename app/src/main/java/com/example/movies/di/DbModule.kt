package com.example.movies.di

import android.content.Context
import androidx.room.Room
import com.example.movies.AppDatabase
import com.example.movies.movie.nowplaying.data.source.local.dao.NowPlayingMoviesDao
import com.example.movies.movie.nowplaying.data.source.local.dao.NowPlayingRemoteKeysDao
import com.example.movies.movie.popular.data.source.local.dao.PopularMoviesDao
import com.example.movies.movie.popular.data.source.local.dao.PopularRemoteKeysDao
import com.example.movies.movie.upcoming.data.source.local.dao.UpcomingMoviesDao
import com.example.movies.movie.upcoming.data.source.local.dao.UpcomingRemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {
    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        DB_NAME
    ).build()

    @Provides
    @Singleton
    fun providePopularMoviesDao(db: AppDatabase): PopularMoviesDao = db.popularMoviesDao()

    @Provides
    @Singleton
    fun providePopularRemoteKeyDao(db: AppDatabase): PopularRemoteKeysDao = db.popularRemoteKeyDao()

    @Provides
    @Singleton
    fun provideNowPlayingMoviesDao(db: AppDatabase): NowPlayingMoviesDao = db.nowPlayingMoviesDao()

    @Provides
    @Singleton
    fun provideNowPlayingRemoteKeyDao(db: AppDatabase): NowPlayingRemoteKeysDao = db.nowPlayingRemoteKeyDao()

    @Provides
    @Singleton
    fun provideUpcomingMoviesDao(db: AppDatabase): UpcomingMoviesDao = db.upcomingMoviesDao()

    @Provides
    @Singleton
    fun provideUpcomingRemoteKeyDao(db: AppDatabase): UpcomingRemoteKeysDao = db.upcomingRemoteKeyDao()
}

private const val DB_NAME = "TMDB.db"