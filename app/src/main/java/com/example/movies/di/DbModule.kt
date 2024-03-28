package com.example.movies.di

import android.content.Context
import androidx.room.Room
import com.example.movies.AppDatabase
import com.example.movies.movie.list.data.source.local.dao.MoviesDao
import com.example.movies.movie.list.data.source.local.dao.RemoteKeysDao
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
    fun provideMoviesDao(db: AppDatabase): MoviesDao = db.moviesDao()

    @Provides
    @Singleton
    fun provideRemoteKeyDao(db: AppDatabase): RemoteKeysDao = db.remoteKeyDao()
}

private const val DB_NAME = "TMDB.db"