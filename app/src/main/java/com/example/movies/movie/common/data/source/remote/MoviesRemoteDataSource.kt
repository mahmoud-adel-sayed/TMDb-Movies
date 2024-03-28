package com.example.movies.movie.common.data.source.remote

import com.example.movies.movie.detail.data.source.remote.model.dto.MovieDetailsDto
import com.example.movies.util.Resource

interface MoviesRemoteDataSource {
    suspend fun getMovieDetails(id: Long): Resource<MovieDetailsDto>
}