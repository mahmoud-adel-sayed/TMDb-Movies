package com.example.movies.movie.list.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.movies.movie.list.data.mapper.toMovie
import com.example.movies.movie.list.data.source.local.dao.MoviesDao
import com.example.movies.movie.list.data.source.remote.MoviesPageKeyedRemoteMediator
import com.example.movies.movie.list.domain.model.Movie
import com.example.movies.movie.list.domain.repo.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesRepositoryImpl(
    private val moviesDao: MoviesDao,
    private val moviesPageKeyedRemoteMediator: MoviesPageKeyedRemoteMediator
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
}

private const val PAGE_SIZE = 20