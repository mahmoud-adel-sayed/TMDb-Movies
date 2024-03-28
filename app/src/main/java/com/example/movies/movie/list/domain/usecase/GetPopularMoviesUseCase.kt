package com.example.movies.movie.list.domain.usecase

import androidx.paging.PagingData
import com.example.movies.movie.list.domain.model.Movie
import com.example.movies.movie.list.domain.repo.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(): Flow<PagingData<Movie>> = moviesRepository.getPopularMovies()
}