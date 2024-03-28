package com.example.movies.movie.detail.domain.model

data class MovieDetails(
    val id: Long,
    val title: String,
    val releaseDate: String,
    val posterUrl: String,
    val overview: String,
    val rating: Double,
    val genres: List<Genre>
)

data class Genre(
    val id: Int,
    val name: String
)