package com.example.movies.movie.common.di

import com.example.movies.AppDatabase
import com.example.movies.movie.common.data.source.remote.api.MoviesApi
import com.example.movies.movie.common.data.repo.MoviesRepositoryImpl
import com.example.movies.movie.common.data.source.remote.MoviesRemoteDataSource
import com.example.movies.movie.common.data.source.remote.MoviesRemoteDataSourceImpl
import com.example.movies.movie.popular.data.source.local.dao.PopularMoviesDao
import com.example.movies.movie.popular.data.source.remote.PopularMoviesPageKeyedRemoteMediator
import com.example.movies.movie.common.domain.repo.MoviesRepository
import com.example.movies.movie.nowplaying.data.source.local.dao.NowPlayingMoviesDao
import com.example.movies.movie.nowplaying.data.source.remote.NowPlayingMoviesPageKeyedRemoteMediator
import com.example.movies.movie.upcoming.data.source.local.dao.UpcomingMoviesDao
import com.example.movies.movie.upcoming.data.source.remote.UpcomingMoviesPageKeyedRemoteMediator
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
    fun provideNowPlayingMoviesPageKeyedRemoteMediator(
        moviesApi: MoviesApi,
        db: AppDatabase
    ): NowPlayingMoviesPageKeyedRemoteMediator = NowPlayingMoviesPageKeyedRemoteMediator(
        db = db,
        moviesApi = moviesApi
    )

    @Provides
    @ViewModelScoped
    fun provideUpcomingMoviesPageKeyedRemoteMediator(
        moviesApi: MoviesApi,
        db: AppDatabase
    ): UpcomingMoviesPageKeyedRemoteMediator = UpcomingMoviesPageKeyedRemoteMediator(
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
        popularMoviesDao: PopularMoviesDao,
        nowPlayingMoviesDao: NowPlayingMoviesDao,
        upcomingMoviesDao: UpcomingMoviesDao,
        popularPageKeyedRemoteMediator: PopularMoviesPageKeyedRemoteMediator,
        nowPlayingPageKeyedRemoteMediator: NowPlayingMoviesPageKeyedRemoteMediator,
        upcomingPageKeyedRemoteMediator: UpcomingMoviesPageKeyedRemoteMediator,
        moviesRemoteDataSource: MoviesRemoteDataSource
    ): MoviesRepository = MoviesRepositoryImpl(
        popularMoviesDao,
        nowPlayingMoviesDao,
        upcomingMoviesDao,
        popularPageKeyedRemoteMediator,
        nowPlayingPageKeyedRemoteMediator,
        upcomingPageKeyedRemoteMediator,
        moviesRemoteDataSource
    )
}