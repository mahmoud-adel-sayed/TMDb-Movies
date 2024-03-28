package com.example.movies.movie.common.domain.repo

import androidx.paging.PagingData
import com.example.movies.movie.detail.domain.model.MovieDetails
import com.example.movies.movie.popular.domain.model.Movie
import com.example.movies.util.Resource
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getPopularMovies(): Flow<PagingData<Movie>>
    suspend fun getMovieDetails(id: Long): Resource<MovieDetails>
}