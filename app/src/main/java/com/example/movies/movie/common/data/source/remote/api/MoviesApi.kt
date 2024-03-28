package com.example.movies.movie.common.data.source.remote.api

import com.example.movies.movie.detail.data.source.remote.model.dto.MovieDetailsDto
import com.example.movies.movie.common.data.source.remote.dto.MoviesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
    ): MoviesDto

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
    ): MoviesDto

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
    ): MoviesDto

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Long,
    ): Response<MovieDetailsDto>
}