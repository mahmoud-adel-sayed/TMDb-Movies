package com.banquemisr.challenge05.movie.common.domain.model

data class Movie(
    val id: Long,
    val title: String,
    val releaseDate: String?,
    val posterUrl: String?
)