package com.example.movies.movie.common.domain.repo

import androidx.paging.PagingData
import com.example.movies.movie.detail.domain.model.MovieDetails
import com.example.movies.movie.common.domain.model.Movie
import com.example.movies.util.Resource
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getPopularMovies(): Flow<PagingData<Movie>>
    fun getNowPlayingMovies(): Flow<PagingData<Movie>>
    fun getUpcomingMovies(): Flow<PagingData<Movie>>
    suspend fun getMovieDetails(id: Long): Resource<MovieDetails>
}