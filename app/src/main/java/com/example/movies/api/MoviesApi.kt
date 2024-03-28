package com.example.movies.api

import com.example.movies.movie.detail.data.source.remote.model.dto.MovieDetailsDto
import com.example.movies.movie.list.data.source.remote.model.dto.MoviesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
    ): MoviesDto

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Long,
    ): Response<MovieDetailsDto>
}