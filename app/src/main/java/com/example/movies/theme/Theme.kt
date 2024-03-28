package com.example.movies.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val LightThemeColors = lightColors(
    primary = Red700,
    primaryVariant = Color.White,
    onPrimary = Color.White,
    secondary = Red,
    secondaryVariant = Red,
    onSecondary = Color.White,
    background = Gray200,
    error = Red800
)

@SuppressLint("ConflictingOnColor")
private val DarkThemeColors = darkColors(
    primary = Red200,
    primaryVariant = Red,
    onPrimary = Color.Black,
    secondary = Red200,
    secondaryVariant = Red200,
    onSecondary = Color.Black,
    error = Red200
)

private val LightElevations = Elevations(card = 0.dp)
private val DarkElevations = Elevations(card = 1.dp)

private val LightImages = Images(logo = 0)
private val DarkImages = Images(logo = 0)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkThemeColors else LightThemeColors
    val elevations = if (darkTheme) DarkElevations else LightElevations
    val images = if (darkTheme) DarkImages else LightImages
    CompositionLocalProvider(
        LocalElevations provides elevations,
        LocalImages provides images
    ) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

/**
 * Alternate to [MaterialTheme] allowing us to add our own theme systems (e.g. [Elevations]) or to
 * extend [MaterialTheme]'s types e.g. return our own [Colors] extension
 */
@Suppress("unused")
object AppTheme {
    /**
     * Proxy to [MaterialTheme]
     */
    val colors: Colors
        @Composable
        get() = MaterialTheme.colors

    /**
     * Proxy to [MaterialTheme]
     */
    val typography: Typography
        @Composable
        get() = MaterialTheme.typography

    /**
     * Proxy to [MaterialTheme]
     */
    val shapes: Shapes
        @Composable
        get() = MaterialTheme.shapes

    /**
     * Retrieves the current [Elevations] at the call site's position in the hierarchy.
     */
    val elevations: Elevations
        @Composable
        get() = LocalElevations.current

    /**
     * Retrieves the current [Images] at the call site's position in the hierarchy.
     */
    val images: Images
        @Composable
        get() = LocalImages.current
}