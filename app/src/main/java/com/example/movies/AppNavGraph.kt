@file:Suppress("unused")

package com.example.movies

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movies.movie.detail.ui.MovieDetailsScreen
import com.example.movies.movie.list.ui.MoviesScreen

/**
 * Destinations used in the App.
 */
private object MainDestinations {
    const val POPULAR_MOVIES_ROUTE = "movies/popular"
    const val MOVIE_DETAILS_ROUTE = "movie/details"
}

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    onBackPressed: () -> Unit
) {
    val actions = remember(navController) { MainActions(navController) }
    NavHost(
        navController = navController,
        startDestination = MainDestinations.POPULAR_MOVIES_ROUTE,
    ) {
        composable(
            route = MainDestinations.POPULAR_MOVIES_ROUTE,
        ) { backStackEntry: NavBackStackEntry ->
            MoviesScreen(
                onBackPressed = onBackPressed,
                onMovieClick = {
                    actions.onMovieClick(it, backStackEntry)
                }
            )
        }
        composable(
            route = "${MainDestinations.MOVIE_DETAILS_ROUTE}/{$KEY_MOVIE_ID}",
            arguments = listOf(
                navArgument(KEY_MOVIE_ID) {
                    type = NavType.LongType
                }
            )
        ) {
            val movieId = it.arguments?.getLong(KEY_MOVIE_ID) ?: -1
            MovieDetailsScreen(
                onUpPressed = { actions.onUpPressed(it) },
                movieId = movieId
            )
        }
    }
}

/**
 * Models the navigation actions in the app.
 */
class MainActions(navController: NavHostController) {
    val onUpPressed: (from: NavBackStackEntry) -> Unit = { from ->
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigateUp()
        }
    }

    val onMovieClick = { movieId: Long, from: NavBackStackEntry ->
        if (from.lifecycleIsResumed()) {
            navController.navigate("${MainDestinations.MOVIE_DETAILS_ROUTE}/$movieId")
        }
    }
}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
fun NavBackStackEntry.lifecycleIsResumed() = this.lifecycle.currentState == Lifecycle.State.RESUMED

private const val KEY_MOVIE_ID = "KEY_MOVIE_ID"