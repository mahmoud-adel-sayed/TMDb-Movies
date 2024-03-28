package com.example.movies.common.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movies.R
import com.example.movies.theme.AppTheme
import com.example.movies.util.mirroringBackIcon

/**
 * The top app bar displays title and back navigation icon.
 *
 * The title is __Centered__ by default but you can override this behaviour by setting
 * [isTitleCentered] flag to false.
 *
 * @param modifier The [Modifier] to be applied to this TopAppBar.
 * @param title The title to be displayed.
 * @param onUpPressed The function that will be invoked when the back icon is pressed.
 * @param isTitleCentered The flag that will determine the alignment of the title.
 * @param navigationIcon The navigation icon.
 */
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onUpPressed: () -> Unit,
    isTitleCentered: Boolean = true,
    navigationIcon: @Composable () -> Unit = {
        Icon(
            imageVector = mirroringBackIcon(),
            contentDescription = null
        )
    }
) {
    if (isTitleCentered) {
        CenteredTitleTopAppBar(
            modifier,
            title,
            onUpPressed,
            navigationIcon
        )
    } else {
        EdgeAlignedTitleTopAppBar(
            modifier,
            title,
            onUpPressed,
            navigationIcon
        )
    }
}

@Composable
private fun EdgeAlignedTitleTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onUpPressed: () -> Unit,
    navigationIcon: @Composable () -> Unit
) {
    TopAppBar(
        elevation = 0.dp,
        modifier = modifier.height(80.dp),
        backgroundColor = AppTheme.colors.surface,
        contentColor = AppTheme.colors.onSurface,
        title = {
            Text(
                text = title,
                style = AppTheme.typography.h6
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onUpPressed,
                content = navigationIcon
            )
        }
    )
}

@Composable
private fun CenteredTitleTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onUpPressed: () -> Unit,
    navigationIcon: @Composable () -> Unit
) {
    TopAppBar(
        elevation = 0.dp,
        modifier = modifier.height(80.dp),
        backgroundColor = AppTheme.colors.surface,
        contentColor = AppTheme.colors.onSurface
    ) {
        Box {
            NavigationIcon(
                onUpPressed,
                navigationIcon
            )
            CenteredTitle(title)
        }
    }
}

@Composable
private fun NavigationIcon(
    onUpPressed: () -> Unit,
    icon: @Composable () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.high,
        ) {
            IconButton(
                onClick = onUpPressed,
                content = icon
            )
        }
    }
}

@Composable
private fun CenteredTitle(title: String) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProvideTextStyle(value = AppTheme.typography.h6) {
            CompositionLocalProvider(
                LocalContentAlpha provides ContentAlpha.high,
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    text = title,
                    style = AppTheme.typography.h6
                )
            }
        }
    }
}

@Preview(name = "CenteredTitleTopAppBar")
@Preview(name = "CenteredTitleTopAppBar - Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "CenteredTitleTopAppBar - ar", locale = "ar")
@Preview(name = "CenteredTitleTopAppBar - ar - Dark", locale = "ar", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CenteredTitleTopAppBarPreview() {
    AppTheme {
        TopAppBar(
            title = stringResource(R.string.popular_movies),
            onUpPressed = { }
        )
    }
}

@Preview(name = "EdgeAlignedTitleTopAppBar")
@Preview(name = "EdgeAlignedTitleTopAppBar - Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "EdgeAlignedTitleTopAppBar - ar", locale = "ar")
@Preview(name = "EdgeAlignedTitleTopAppBar - ar - Dark", locale = "ar", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun EdgeAlignedTitleTopAppBar() {
    AppTheme {
        TopAppBar(
            title = stringResource(R.string.popular_movies),
            onUpPressed = { },
            isTitleCentered = false
        )
    }
}