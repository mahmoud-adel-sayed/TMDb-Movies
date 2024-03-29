package com.example.movies.movie.nowplaying.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.movies.movie.common.ui.MovieCard
import com.example.movies.movie.common.ui.loading
import com.example.movies.movie.common.ui.error
import com.example.movies.movie.common.domain.model.Movie

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NowPlayingMoviesScreen(
    modifier: Modifier = Modifier,
    onMovieClick: (movieId: Long) -> Unit,
    nowPlayingMoviesViewModel: NowPlayingMoviesViewModel = hiltViewModel()
) {
    NowPlayingMoviesContent(
        modifier = modifier.semantics { testTagsAsResourceId = true },
        movies = nowPlayingMoviesViewModel.getNowPlayingMovies().collectAsLazyPagingItems(),
        onMovieClick = onMovieClick
    )
}

@Composable
private fun NowPlayingMoviesContent(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<Movie>,
    onMovieClick: (movieId: Long) -> Unit,
) {
    LazyColumn(modifier = modifier.padding(horizontal = 16.dp)) {
        itemsIndexed(
            items = movies
        ) { index: Int, movie: Movie? ->
            movie?.let {
                MovieCard(
                    index = index,
                    movie = it,
                    onMovieClick = onMovieClick
                )
            }
        }
        when (val state = movies.loadState.refresh) {
            is LoadState.NotLoading -> Unit
            is LoadState.Error -> error(
                modifier = Modifier.fillMaxWidth(),
                message = state.error.message ?: ""
            )
            is LoadState.Loading -> loading(modifier = Modifier.fillMaxSize())
        }
        when (movies.loadState.append) {
            is LoadState.NotLoading -> Unit
            is LoadState.Error -> Unit
            is LoadState.Loading -> loading(modifier = Modifier.fillMaxWidth())
        }
    }
}