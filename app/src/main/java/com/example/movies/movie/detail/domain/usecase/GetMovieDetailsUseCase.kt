package com.example.movies.movie.detail.domain.usecase

import com.example.movies.movie.detail.domain.model.MovieDetails
import com.example.movies.movie.detail.domain.repo.MovieDetailsRepository
import com.example.movies.util.Resource
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
) {
    suspend operator fun invoke(id: Long): Resource<MovieDetails> =
        movieDetailsRepository.getMovieDetails(id)
}