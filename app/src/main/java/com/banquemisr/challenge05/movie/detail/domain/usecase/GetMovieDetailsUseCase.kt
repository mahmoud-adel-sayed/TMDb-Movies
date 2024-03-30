package com.banquemisr.challenge05.movie.detail.domain.usecase

import com.banquemisr.challenge05.movie.common.domain.repo.MoviesRepository
import com.banquemisr.challenge05.movie.detail.domain.model.MovieDetails
import com.banquemisr.challenge05.util.Resource
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(id: Long): Resource<MovieDetails> =
        moviesRepository.getMovieDetails(id)
}