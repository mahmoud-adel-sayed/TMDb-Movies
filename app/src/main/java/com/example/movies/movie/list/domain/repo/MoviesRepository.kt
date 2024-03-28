package com.example.movies.movie.list.domain.repo

import androidx.paging.PagingData
import com.example.movies.movie.list.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getPopularMovies(): Flow<PagingData<Movie>>
}