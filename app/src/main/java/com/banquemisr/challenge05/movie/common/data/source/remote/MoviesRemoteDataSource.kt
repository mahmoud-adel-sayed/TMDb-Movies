package com.banquemisr.challenge05.movie.common.data.source.remote

import com.banquemisr.challenge05.movie.detail.data.source.remote.model.dto.MovieDetailsDto
import com.banquemisr.challenge05.util.Resource

interface MoviesRemoteDataSource {
    suspend fun getMovieDetails(id: Long): Resource<MovieDetailsDto>
}