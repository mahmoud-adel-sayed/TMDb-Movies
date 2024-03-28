package com.example.movies.movie.common.di

import com.example.movies.AppDatabase
import com.example.movies.movie.common.data.source.remote.api.MoviesApi
import com.example.movies.movie.common.data.repo.MoviesRepositoryImpl
import com.example.movies.movie.common.data.source.remote.MoviesRemoteDataSource
import com.example.movies.movie.common.data.source.remote.MoviesRemoteDataSourceImpl
import com.example.movies.movie.popular.data.source.local.dao.MoviesDao
import com.example.movies.movie.popular.data.source.remote.PopularMoviesPageKeyedRemoteMediator
import com.example.movies.movie.common.domain.repo.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
object MoviesModule {
    @Provides
    @ViewModelScoped
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @ViewModelScoped
    fun providePopularMoviesPageKeyedRemoteMediator(
        moviesApi: MoviesApi,
        db: AppDatabase
    ): PopularMoviesPageKeyedRemoteMediator = PopularMoviesPageKeyedRemoteMediator(
        db = db,
        moviesApi = moviesApi
    )

    @Provides
    @ViewModelScoped
    fun provideMoviesRemoteDataSource(
        moviesApi: MoviesApi,
        dispatcher: CoroutineDispatcher
    ): MoviesRemoteDataSource = MoviesRemoteDataSourceImpl(dispatcher, moviesApi)

    @Provides
    @ViewModelScoped
    fun provideMoviesRepository(
        moviesDao: MoviesDao,
        moviesPageKeyedRemoteMediator: PopularMoviesPageKeyedRemoteMediator,
        moviesRemoteDataSource: MoviesRemoteDataSource
    ): MoviesRepository = MoviesRepositoryImpl(
        moviesDao,
        moviesPageKeyedRemoteMediator,
        moviesRemoteDataSource
    )
}