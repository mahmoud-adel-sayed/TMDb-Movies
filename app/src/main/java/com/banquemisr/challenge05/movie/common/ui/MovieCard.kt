package com.banquemisr.challenge05.movie.common.ui

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.banquemisr.challenge05.movie.common.domain.model.Movie
import com.banquemisr.challenge05.theme.AppTheme
import com.banquemisr.challenge05.util.NetworkImage

@Composable
fun MovieCard(
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