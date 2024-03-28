package com.example.movies

import androidx.compose.runtime.Composable
import com.example.movies.theme.AppTheme

@Composable
fun MoviesApp(
    onBackPressed: () -> Unit
) {
    AppTheme {
        NavGraph(
            onBackPressed = onBackPressed
        )
    }
}