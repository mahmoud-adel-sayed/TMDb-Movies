package com.example.movies.movie.common.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.movies.R
import com.example.movies.movie.nowplaying.ui.NowPlayingMoviesScreen
import com.example.movies.movie.popular.ui.PopularMoviesScreen
import com.example.movies.movie.upcoming.ui.UpcomingMoviesScreen

enum class MainTabs(@StringRes val title: Int) {
    POPULAR(R.string.popular),
    NOW_PLAYING(R.string.now_playing),
    UPCOMING(R.string.upcoming)
}

@Composable
fun TabsScreen(
    onMovieClick: (movieId: Long) -> Unit
) {
    var tabIndex by remember { mutableStateOf(0) }
    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex) {
            MainTabs.values().forEachIndexed { index, tab ->
                Tab(
                    text = { Text(stringResource(id = tab.title)) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }
        when (tabIndex) {
            0 -> PopularMoviesScreen(onMovieClick = onMovieClick)
            1 -> NowPlayingMoviesScreen(onMovieClick = onMovieClick)
            2 -> UpcomingMoviesScreen(onMovieClick = onMovieClick)
        }
    }
}