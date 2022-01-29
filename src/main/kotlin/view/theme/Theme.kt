package view.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import view.theme.Typography

private val DarkColorPalette = darkColors(
    primary = Purple500,
    primaryVariant = Purple200,
    secondary = Teal200,
    background = Black,
    onPrimary = White,
    onBackground = White,
    onSurface = Gray,
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple200,
    secondary = Teal200,
    background = White,
    onPrimary = White,
    onBackground = Gray,
    onSurface = Black,

    /* Other default colors to override
    surface = Color.White,
    onSecondary = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}