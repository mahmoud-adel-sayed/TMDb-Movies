package com.banquemisr.challenge05.movie.common.domain.repo

import androidx.paging.PagingData
import com.banquemisr.challenge05.movie.detail.domain.model.MovieDetails
import com.banquemisr.challenge05.movie.common.domain.model.Movie
import com.banquemisr.challenge05.util.Resource
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getPopularMovies(): Flow<PagingData<Movie>>
    fun getNowPlayingMovies(): Flow<PagingData<Movie>>
    fun getUpcomingMovies(): Flow<PagingData<Movie>>
    suspend fun getMovieDetails(id: Long): Resource<MovieDetails>
}