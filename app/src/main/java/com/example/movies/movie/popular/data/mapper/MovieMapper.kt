package com.example.movies.movie.popular.data.mapper

import com.example.movies.movie.popular.data.source.local.entity.MovieEntity
import com.example.movies.movie.common.data.source.remote.dto.MovieDto
import com.example.movies.movie.popular.domain.model.Movie

fun MovieEntity.toMovie(): Movie = Movie(
    id = this.serverId,
    title = this.title,
    releaseDate = this.releaseDate,
    posterUrl = this.posterUrl
)

fun List<MovieDto>.toMovieEntities(): List<MovieEntity> = map { it.toMovieEntity() }

fun MovieDto.toMovieEntity(): MovieEntity = MovieEntity(
    serverId = this.id,
    title = this.title,
    releaseDate = this.releaseDate,
    posterUrl = this.posterUrl
)