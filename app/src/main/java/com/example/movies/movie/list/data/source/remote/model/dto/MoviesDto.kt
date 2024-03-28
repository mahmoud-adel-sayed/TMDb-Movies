package com.example.movies.movie.list.data.source.remote.model.dto

import com.google.gson.annotations.SerializedName

data class MoviesDto(
    @SerializedName("page")
    val page: Int? = null,

    @SerializedName("total_pages")
    val totalPages: Int? = null,

    @SerializedName("total_results")
    val totalResults: Long? = null,

    @SerializedName("results")
    val results: List<MovieDto> = emptyList(),
)

data class MovieDto(
    @SerializedName("id")
    val id: Long,

    @SerializedName("title")
    val title: String,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("poster_path")
    val posterUrl: String?
)