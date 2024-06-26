package com.banquemisr.challenge05.movie.common.data.mapper

import com.banquemisr.challenge05.movie.common.data.source.remote.dto.MovieDto
import com.banquemisr.challenge05.movie.common.domain.model.Movie
import com.banquemisr.challenge05.movie.nowplaying.data.source.local.entity.NowPlayingMovieEntity
import com.banquemisr.challenge05.movie.popular.data.source.local.entity.PopularMovieEntity
import com.banquemisr.challenge05.movie.upcoming.data.source.local.entity.UpcomingMovieEntity

fun PopularMovieEntity.toMovie(): Movie = Movie(
    id = this.serverId,
    title = this.title,
    releaseDate = this.releaseDate,
    posterUrl = this.posterUrl
)

fun NowPlayingMovieEntity.toMovie(): Movie = Movie(
    id = this.serverId,
    title = this.title,
    releaseDate = this.releaseDate,
    posterUrl = this.posterUrl
)

fun UpcomingMovieEntity.toMovie(): Movie = Movie(
    id = this.serverId,
    title = this.title,
    releaseDate = this.releaseDate,
    posterUrl = this.posterUrl
)

fun List<MovieDto>.toPopularMovieEntities(): List<PopularMovieEntity> = map { it.toPopularMovieEntity() }

fun MovieDto.toPopularMovieEntity(): PopularMovieEntity = PopularMovieEntity(
    serverId = this.id,
    title = this.title,
    releaseDate = this.releaseDate,
    posterUrl = this.posterUrl
)

fun List<MovieDto>.toNowPlayingMovieEntities(): List<NowPlayingMovieEntity> = map { it.toNowPlayingMovieEntity() }

fun MovieDto.toNowPlayingMovieEntity(): NowPlayingMovieEntity = NowPlayingMovieEntity(
    serverId = this.id,
    title = this.title,
    releaseDate = this.releaseDate,
    posterUrl = this.posterUrl
)

fun List<MovieDto>.toUpcomingMovieEntities(): List<UpcomingMovieEntity> = map { it.toUpcomingMovieEntity() }

fun MovieDto.toUpcomingMovieEntity(): UpcomingMovieEntity = UpcomingMovieEntity(
    serverId = this.id,
    title = this.title,
    releaseDate = this.releaseDate,
    posterUrl = this.posterUrl
)