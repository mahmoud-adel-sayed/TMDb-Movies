package com.example.movies.movie.detail.domain.usecase

import com.example.movies.movie.common.domain.repo.MoviesRepository
import com.example.movies.movie.detail.domain.model.MovieDetails
import com.example.movies.util.Resource
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(id: Long): Resource<MovieDetails> =
        moviesRepository.getMovieDetails(id)
}