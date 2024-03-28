package com.example.movies.movie.list.ui

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.movies.R
import com.example.movies.common.ui.TopAppBar
import com.example.movies.movie.list.domain.model.Movie
import com.example.movies.theme.AppTheme
import com.example.movies.util.NetworkImage

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    onMovieClick: (movieId: Long) -> Unit,
    moviesViewModel: MoviesViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(id = R.string.popular_movies),
                onUpPressed = onBackPressed
            )
        }
    ) { innerPaddingModifier ->
        MoviesContent(
            modifier = modifier
                .padding(innerPaddingModifier)
                .semantics { testTagsAsResourceId = true },
            movies = moviesViewModel.getPopularMovies().collectAsLazyPagingItems(),
            onMovieClick = onMovieClick
        )
    }
}

@Composable
private fun MoviesContent(
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
            is LoadState.Error -> error(message = state.error.message ?: "")
            is LoadState.Loading -> loading(modifier = Modifier.fillMaxSize())
        }
        when (movies.loadState.append) {
            is LoadState.NotLoading -> Unit
            is LoadState.Error -> Unit
            is LoadState.Loading -> loading(modifier = Modifier.fillMaxWidth())
        }
    }
}

private fun LazyListScope.error(
    message: String
) {
    item {
        Text(
            text = message,
            style = AppTheme.typography.h6,
            color = AppTheme.colors.error
        )
    }
}

private fun LazyListScope.loading(
    modifier: Modifier = Modifier
) {
    item {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun MovieCard(
    index: Int,
    movie: Movie,
    onMovieClick: (movieId: Long) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 150.dp)
            .padding(top = 16.dp)
            .clickable(onClick = {
                onMovieClick(movie.id)
            })
            .testTag("${MOVIE_ITEM_ID}_${index}_card"),
        contentColor = AppTheme.colors.onSurface
    ) {
        Row {
            NetworkImage(
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp),
                url = IMAGE_BASE_URL + movie.posterUrl,
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
            Column {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = movie.title,
                    style = AppTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 16.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(16.dp))
                movie.releaseDate?.let {
                    Text(
                        text = it,
                        style = AppTheme.typography.body1,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview(name = "MovieCardPreview")
@Preview(name = "MovieCardPreview - Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MovieCardPreview() {
    AppTheme {
        MovieCard(
            index = 0,
            movie = Movie(
                id = 1,
                title = "movie title",
                releaseDate = "2023-01-01",
                posterUrl = "image.jpg"
            ),
            onMovieClick = { }
        )
    }
}

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w342"
private const val MOVIE_ITEM_ID = "movie_item"