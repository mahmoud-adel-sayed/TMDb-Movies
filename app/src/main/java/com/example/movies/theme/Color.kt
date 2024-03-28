@file:Suppress("unused")

package com.example.movies.theme

import androidx.compose.material.Colors
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.unit.Dp

val Gray200 = Color(0xfff4f4f4)
val Red700 = Color(0xffcd0000)
val Red = Color(0xffe60000)
val Red200 = Color(0xfff297a2)
val Red800 = Color(0xffd00036)
val Red900 = Color(0xFF990000)

val Colors.cardBackground: Color
    @Composable
    get() = if (isLight) Color.Black.copy(alpha = 0.05f) else Color.White.copy(alpha = 0.24f)

/**
 * Return the fully opaque color that results from compositing onSurface atop surface with the
 * given [alpha]. Useful for situations where semi-transparent colors are undesirable.
 */
@Composable
fun Colors.compositedOnSurface(alpha: Float): Color {
    return onSurface.copy(alpha = alpha).compositeOver(surface)
}

/**
 * Calculates the color of an elevated `surface` in dark mode. Returns `surface` in light mode.
 */
@Composable
fun Colors.elevatedSurface(elevation: Dp): Color {
    return LocalElevationOverlay.current?.apply(
        color = this.surface,
        elevation = elevation
    ) ?: this.surface
}