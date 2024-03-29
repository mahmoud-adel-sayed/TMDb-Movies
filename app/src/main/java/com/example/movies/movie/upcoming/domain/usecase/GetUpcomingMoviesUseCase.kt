package com.example.movies.movie.upcoming.domain.usecase

import androidx.paging.PagingData
import com.example.movies.movie.common.domain.model.Movie
import com.example.movies.movie.common.domain.repo.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(): Flow<PagingData<Movie>> = moviesRepository.getUpcomingMovies()
}