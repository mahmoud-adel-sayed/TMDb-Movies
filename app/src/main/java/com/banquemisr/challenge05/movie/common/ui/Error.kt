package com.banquemisr.challenge05.movie.common.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.banquemisr.challenge05.theme.AppTheme

fun LazyListScope.error(
    modifier: Modifier = Modifier,
    message: String
) {
    item {
        Box(modifier = modifier) {
            Text(
                text = message,
                style = AppTheme.typography.h6,
                color = AppTheme.colors.error,
                textAlign = TextAlign.Center
            )
        }
    }
}