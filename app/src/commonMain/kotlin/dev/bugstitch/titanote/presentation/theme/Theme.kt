package dev.bugstitch.titanote.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = ZenColors.WhiteSmoke,
    secondary = ZenColors.SeaFoamGreen,
    background = ZenColors.Night,
    surface = ZenColors.EerieBlack,
)

private val LightColorScheme = lightColorScheme(
    primary = ZenColors.Night,
    secondary = ZenColors.SeaFoamGreen,
    background = ZenColors.WhiteSmoke,
    surface = ZenColors.AntiFlashWhite
    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun TitanoteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}