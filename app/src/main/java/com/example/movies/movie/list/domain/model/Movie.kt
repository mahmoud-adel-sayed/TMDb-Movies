package com.example.movies.movie.list.domain.model

data class Movie(
    val id: Long,
    val title: String,
    val releaseDate: String?,
    val posterUrl: String?
)