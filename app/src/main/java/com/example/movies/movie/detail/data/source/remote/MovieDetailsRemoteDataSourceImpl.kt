package com.example.movies.movie.detail.data.source.remote

import com.example.movies.api.MoviesApi
import com.example.movies.util.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDetailsRemoteDataSourceImpl(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val moviesApi: MoviesApi
) : MovieDetailsRemoteDataSource {
    override suspend fun getMovieDetails(id: Long) = withContext(dispatcher) {
        return@withContext safeApiCall { moviesApi.getMovieDetails(id) }
    }
}