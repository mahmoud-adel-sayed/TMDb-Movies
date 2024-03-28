package com.example.movies.movie.detail.data.repo

import com.example.movies.movie.detail.data.mapper.toMovieDetails
import com.example.movies.movie.detail.data.source.remote.MovieDetailsRemoteDataSource
import com.example.movies.movie.detail.domain.model.MovieDetails
import com.example.movies.movie.detail.domain.repo.MovieDetailsRepository
import com.example.movies.util.Resource

class MovieDetailsRepositoryImpl(
    private val movieDetailsRemoteDataSource: MovieDetailsRemoteDataSource
) : MovieDetailsRepository {
    override suspend fun getMovieDetails(id: Long): Resource<MovieDetails> =
        when (val resource = movieDetailsRemoteDataSource.getMovieDetails(id)) {
            is Resource.Success -> Resource.Success(resource.data.toMovieDetails())
            is Resource.Failure -> Resource.Failure(code = resource.code, message = resource.message)
        }
}