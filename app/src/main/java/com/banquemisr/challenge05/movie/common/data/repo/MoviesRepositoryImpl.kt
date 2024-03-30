package com.banquemisr.challenge05.movie.common.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.banquemisr.challenge05.movie.common.data.source.remote.MoviesRemoteDataSource
import com.banquemisr.challenge05.movie.common.domain.repo.MoviesRepository
import com.banquemisr.challenge05.movie.detail.data.mapper.toMovieDetails
import com.banquemisr.challenge05.movie.detail.domain.model.MovieDetails
import com.banquemisr.challenge05.movie.common.data.mapper.toMovie
import com.banquemisr.challenge05.movie.popular.data.source.local.dao.PopularMoviesDao
import com.banquemisr.challenge05.movie.popular.data.source.remote.PopularMoviesPageKeyedRemoteMediator
import com.banquemisr.challenge05.movie.common.domain.model.Movie
import com.banquemisr.challenge05.movie.nowplaying.data.source.local.dao.NowPlayingMoviesDao
import com.banquemisr.challenge05.movie.nowplaying.data.source.remote.NowPlayingMoviesPageKeyedRemoteMediator
import com.banquemisr.challenge05.movie.upcoming.data.source.local.dao.UpcomingMoviesDao
import com.banquemisr.challenge05.movie.upcoming.data.source.remote.UpcomingMoviesPageKeyedRemoteMediator
import com.banquemisr.challenge05.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesRepositoryImpl(
    private val popularMoviesDao: PopularMoviesDao,
    private val nowPlayingMoviesDao: NowPlayingMoviesDao,
    private val upcomingMoviesDao: UpcomingMoviesDao,
    private val popularMoviesPageKeyedRemoteMediator: PopularMoviesPageKeyedRemoteMediator,
    private val nowPlayingMoviesPageKeyedRemoteMediator: NowPlayingMoviesPageKeyedRemoteMediator,
    private val upcomingMoviesPageKeyedRemoteMediator: UpcomingMoviesPageKeyedRemoteMediator,
    private val moviesRemoteDataSource: MoviesRemoteDataSource
) : MoviesRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPopularMovies(): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = popularMoviesPageKeyedRemoteMediator
        ) {
            popularMoviesDao.getPagingMovies()
        }.flow.map {
            it.map { movieEntity ->
                movieEntity.toMovie()
            }
        }

    @OptIn(ExperimentalPagingApi::class)
    override fun getNowPlayingMovies(): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = nowPlayingMoviesPageKeyedRemoteMediator
        ) {
            nowPlayingMoviesDao.getPagingMovies()
        }.flow.map {
            it.map { movieEntity ->
                movieEntity.toMovie()
            }
        }

    @OptIn(ExperimentalPagingApi::class)
    override fun getUpcomingMovies(): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = upcomingMoviesPageKeyedRemoteMediator
        ) {
            upcomingMoviesDao.getPagingMovies()
        }.flow.map {
            it.map { movieEntity ->
                movieEntity.toMovie()
            }
        }

    override suspend fun getMovieDetails(id: Long): Resource<MovieDetails> =
        when (val resource = moviesRemoteDataSource.getMovieDetails(id)) {
            is Resource.Success -> Resource.Success(resource.data.toMovieDetails())
            is Resource.Failure -> Resource.Failure(
                code = resource.code,
                message = resource.message
            )
        }
}

private const val PAGE_SIZE = 20