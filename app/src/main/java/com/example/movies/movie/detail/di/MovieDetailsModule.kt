package com.example.movies.movie.detail.di

import com.example.movies.api.MoviesApi
import com.example.movies.movie.detail.data.repo.MovieDetailsRepositoryImpl
import com.example.movies.movie.detail.data.source.remote.MovieDetailsRemoteDataSource
import com.example.movies.movie.detail.data.source.remote.MovieDetailsRemoteDataSourceImpl
import com.example.movies.movie.detail.domain.repo.MovieDetailsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
object MovieDetailsModule {
    @Provides
    @ViewModelScoped
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @ViewModelScoped
    fun provideMovieDetailsRemoteDataSource(
        moviesApi: MoviesApi,
        dispatcher: CoroutineDispatcher
    ): MovieDetailsRemoteDataSource = MovieDetailsRemoteDataSourceImpl(dispatcher, moviesApi)

    @Provides
    @ViewModelScoped
    fun provideMovieDetailsRepository(
        movieDetailsRemoteDataSource: MovieDetailsRemoteDataSource
    ): MovieDetailsRepository = MovieDetailsRepositoryImpl(movieDetailsRemoteDataSource)
}