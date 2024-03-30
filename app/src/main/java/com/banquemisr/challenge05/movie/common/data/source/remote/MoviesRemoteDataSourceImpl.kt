package com.banquemisr.challenge05.movie.common.data.source.remote

import com.banquemisr.challenge05.movie.common.data.source.remote.api.MoviesApi
import com.banquemisr.challenge05.util.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRemoteDataSourceImpl(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val moviesApi: MoviesApi
) : MoviesRemoteDataSource {
    override suspend fun getMovieDetails(id: Long) = withContext(dispatcher) {
        return@withContext safeApiCall { moviesApi.getMovieDetails(id) }
    }
}