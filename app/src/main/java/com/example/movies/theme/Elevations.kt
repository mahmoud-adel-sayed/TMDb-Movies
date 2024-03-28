package com.example.movies.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp

/**
 * Elevation values that can vary by theme.
 */
@Immutable
data class Elevations(val card: Dp)

internal val LocalElevations = staticCompositionLocalOf<Elevations> {
    error("No LocalElevations specified")
}