package com.example.movies.movie.detail.data.source.remote

import com.example.movies.movie.detail.data.source.remote.model.dto.MovieDetailsDto
import com.example.movies.util.Resource

interface MovieDetailsRemoteDataSource {
    suspend fun getMovieDetails(id: Long): Resource<MovieDetailsDto>
}