package com.example.movies.movie.list.di

import com.example.movies.AppDatabase
import com.example.movies.api.MoviesApi
import com.example.movies.movie.list.data.repo.MoviesRepositoryImpl
import com.example.movies.movie.list.data.source.local.dao.MoviesDao
import com.example.movies.movie.list.data.source.remote.MoviesPageKeyedRemoteMediator
import com.example.movies.movie.list.domain.repo.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object MoviesModule {
    @Provides
    @ViewModelScoped
    fun provideMoviesPageKeyedRemoteMediator(
        moviesApi: MoviesApi,
        db: AppDatabase
    ): MoviesPageKeyedRemoteMediator = MoviesPageKeyedRemoteMediator(
        db = db,
        moviesApi = moviesApi
    )

    @Provides
    @ViewModelScoped
    fun provideMoviesRepository(
        moviesDao: MoviesDao,
        moviesPageKeyedRemoteMediator: MoviesPageKeyedRemoteMediator
    ): MoviesRepository = MoviesRepositoryImpl(moviesDao, moviesPageKeyedRemoteMediator)
}