package com.example.movies.movie.detail.data.mapper

import com.example.movies.movie.detail.data.source.remote.model.dto.MovieDetailsDto
import com.example.movies.movie.detail.domain.model.Genre
import com.example.movies.movie.detail.domain.model.MovieDetails

fun MovieDetailsDto.toMovieDetails(): MovieDetails = MovieDetails(
    id = this.id,
    title = this.title,
    releaseDate = this.releaseDate,
    posterUrl = this.posterUrl,
    overview = this.overview,
    rating = this.rating,
    genres = this.genres.map { Genre(id = it.id, name = it.name) }
)