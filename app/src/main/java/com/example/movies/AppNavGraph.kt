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
import com.example.movies.movie.common.ui.TabsScreen
import com.example.movies.movie.detail.ui.MovieDetailsScreen

/**
 * Destinations used in the App.
 */
object MainDestinations {
    const val TABS_ROUTE = "tabs"
    const val MOVIE_DETAILS_ROUTE = "movie/details"
}

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
) {
    val actions = remember(navController) { MainActions(navController) }
    NavHost(
        navController = navController,
        startDestination = MainDestinations.TABS_ROUTE,
    ) {
        composable(
            route = MainDestinations.TABS_ROUTE,
        ) { backStackEntry: NavBackStackEntry ->
            TabsScreen(
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
                movieId = movieId,
                onUpPressed = { actions.onUpPressed(it) }
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