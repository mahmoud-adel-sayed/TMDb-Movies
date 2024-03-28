package com.example.movies.movie.common.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.movies.movie.common.data.source.remote.MoviesRemoteDataSource
import com.example.movies.movie.common.domain.repo.MoviesRepository
import com.example.movies.movie.detail.data.mapper.toMovieDetails
import com.example.movies.movie.detail.domain.model.MovieDetails
import com.example.movies.movie.popular.data.mapper.toMovie
import com.example.movies.movie.popular.data.source.local.dao.MoviesDao
import com.example.movies.movie.popular.data.source.remote.PopularMoviesPageKeyedRemoteMediator
import com.example.movies.movie.popular.domain.model.Movie
import com.example.movies.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesRepositoryImpl(
    private val moviesDao: MoviesDao,
    private val moviesPageKeyedRemoteMediator: PopularMoviesPageKeyedRemoteMediator,
    private val moviesRemoteDataSource: MoviesRemoteDataSource
) : MoviesRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPopularMovies(): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = moviesPageKeyedRemoteMediator
        ) {
            moviesDao.getPagingMovies()
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