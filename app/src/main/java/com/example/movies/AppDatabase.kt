package com.example.movies

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movies.movie.list.data.source.local.dao.MoviesDao
import com.example.movies.movie.list.data.source.local.dao.RemoteKeysDao
import com.example.movies.movie.list.data.source.local.entity.MovieEntity
import com.example.movies.movie.list.data.source.local.entity.RemoteKeyEntity

@Database(
    entities = [MovieEntity::class, RemoteKeyEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun remoteKeyDao(): RemoteKeysDao
}