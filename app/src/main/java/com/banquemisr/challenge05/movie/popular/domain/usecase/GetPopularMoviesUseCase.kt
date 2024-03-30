package com.banquemisr.challenge05.movie.popular.domain.usecase

import androidx.paging.PagingData
import com.banquemisr.challenge05.movie.common.domain.model.Movie
import com.banquemisr.challenge05.movie.common.domain.repo.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(): Flow<PagingData<Movie>> = moviesRepository.getPopularMovies()
}