package climbing.ba.nocomment.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

//TODO: enable dark theme

private val DarkColorPalette = darkColors(
    primary = Color(0xFFA8A8A8),
    primaryVariant = Color(0x99C7C7C7),
    secondary = Color(0xFF000000),
    background = Color.White,
    surface = Color(0x99C7C7C7),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

private val LightColorPalette = lightColors(
    primary = Color.DarkGray,          // default Material purple
    primaryVariant = Color.DarkGray,
    secondary = Color(0xFF03DAC6),        // default accent
    secondaryVariant = Color(0xFF018786),
    background = Color(0xFFFFFFFF),       // white background
    surface = Color(0xFFFFFFFF),          // white surface for cards, dropdowns
    onPrimary = Color.White,              // text/icons on primary
    onSecondary = Color.Black,            // text/icons on secondary
    onBackground = Color.Black,           // text/icons on background
    onSurface = Color.Black                // text/icons on surface (dropdown, cards)
)


@Composable
fun NoCommentTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        LightColorPalette
    } else {
        LightColorPalette
    }

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Black,
            darkIcons = false
        )
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}