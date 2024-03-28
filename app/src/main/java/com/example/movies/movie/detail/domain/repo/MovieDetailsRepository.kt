package com.example.movies.movie.detail.domain.repo

import com.example.movies.movie.detail.domain.model.MovieDetails
import com.example.movies.util.Resource

interface MovieDetailsRepository {
    suspend fun getMovieDetails(id: Long): Resource<MovieDetails>
}