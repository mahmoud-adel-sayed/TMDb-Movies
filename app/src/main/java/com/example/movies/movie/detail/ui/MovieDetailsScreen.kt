package com.example.movies.movie.detail.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.movies.R
import com.example.movies.common.model.UiState
import com.example.movies.common.ui.TopAppBar
import com.example.movies.movie.detail.domain.model.Genre
import com.example.movies.movie.detail.domain.model.MovieDetails
import com.example.movies.theme.AppTheme
import com.example.movies.util.NetworkImage

@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalComposeUiApi::class)
@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    movieId: Long,
    onUpPressed: () -> Unit,
    movieDetailsViewModel: MovieDetailsViewModel = hiltViewModel()
) {
    val movieDetailsUiState by movieDetailsViewModel.movieDetails.collectAsStateWithLifecycle()
    LaunchedEffect(true) {
        movieDetailsViewModel.getMovieDetails(movieId)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(id = R.string.movie_details),
                onUpPressed = onUpPressed
            )
        }
    ) { innerPaddingModifier ->
        MovieDetailsContent(
            modifier = modifier
                .padding(innerPaddingModifier)
                .semantics { testTagsAsResourceId = true },
            movieDetailsUiState = movieDetailsUiState
        )
    }
}

@Composable
private fun MovieDetailsContent(
    modifier: Modifier = Modifier,
    movieDetailsUiState: UiState<MovieDetails>,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = modifier.verticalScroll(rememberScrollState())) {
            when (movieDetailsUiState) {
                is UiState.Loading -> Loading()
                is UiState.Success -> MovieDetails(movieDetails = movieDetailsUiState.body)
                is UiState.Error -> Error(message = movieDetailsUiState.errorMessage)
            }
        }
    }
}

@Composable
private fun Loading() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun Error(message: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = message,
            modifier = Modifier.align(Alignment.Center),
            color = AppTheme.colors.error
        )
    }
}

@Composable
private fun MovieDetails(
    movieDetails: MovieDetails
) {
    Column {
        NetworkImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp),
            url = IMAGE_BASE_URL + movieDetails.posterUrl,
            contentScale = ContentScale.FillBounds,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = movieDetails.title,
            style = AppTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = movieDetails.releaseDate,
            style = AppTheme.typography.body1,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = movieDetails.overview,
            style = AppTheme.typography.body1,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Text(
                text = stringResource(R.string.label_rating) + " ",
                style = AppTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(start = 16.dp)
            )
            Text(
                text = String.format("%.1f", movieDetails.rating),
                style = AppTheme.typography.body1
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.label_genres),
            style = AppTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(start = 16.dp)
        )
        movieDetails.genres.forEach { genre ->
            Text(
                text = genre.name,
                style = AppTheme.typography.body1,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(name = "MovieDetailsPreview")
@Preview(name = "MovieDetailsPreview - Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MovieDetailsPreview() {
    AppTheme {
        MovieDetails(
            movieDetails = MovieDetails(
                id = 1,
                title = "Movie title",
                releaseDate = "2023-01-01",
                posterUrl = "image.jpg",
                overview = "An exploratory dive into the deepest depths of the ocean of a daring " +
                        "research team spirals into chaos when a malevolent mining operation " +
                        "threatens their mission and forces them into a high-stakes battle for survival.",
                rating = 7.7,
                genres = listOf(
                    Genre(id = 10, name = "Action"),
                    Genre(id = 10, name = "Drama"),
                    Genre(id = 10, name = "Adventure")
                )
            )
        )
    }
}


private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w780"