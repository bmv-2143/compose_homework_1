package com.example.cupcake.ui.theme

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme: ColorScheme = darkColorScheme(
    primary = Pink400,
    onPrimary = Black,
    secondary = Purple400,
    onSecondary = Black,
    primaryContainer = Pink950,     // colorPrimaryVariant
    secondaryContainer = Purple400, // colorSecondaryVariant
    surface = DarkDefaultBackground,
    background = DarkDefaultBackground,
)

private val LightColorScheme = lightColorScheme(
    primary = Pink600,
    onPrimary = White,
    secondary = Purple400,
    onSecondary = Black,
    primaryContainer = Pink950,      // colorPrimaryVariant
    secondaryContainer = Purple700,  // colorSecondaryVariant
)

val ColorScheme.topAppBarContainer: Color
    @Composable
    get() = if (isSystemInDarkTheme()) DarkDefaultBackground
    else Pink600

val ColorScheme.onTopAppBarContainer: Color
    get() = Color.White


@Composable
fun CupcakeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    SetStatusAndNavigationBarsAppearance(colorScheme, darkTheme)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}

@Composable
private fun SetStatusAndNavigationBarsAppearance(colorScheme: ColorScheme, darkTheme: Boolean) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val activity = view.context as Activity
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setNavigationBarAppearance(activity, colorScheme, view, darkTheme)
                setStatusBarAppearance(activity, colorScheme.primaryContainer, view)
            }
        }
    }
}

private fun setNavigationBarAppearance(
    activity: Activity,
    colorScheme: ColorScheme,
    view: View,
    darkTheme: Boolean
) {
    activity.window.navigationBarColor =
        colorScheme.primary.copy(alpha = 0.2f).compositeOver(colorScheme.surface.copy()).toArgb()
    WindowCompat.getInsetsController(activity.window, view)
        .isAppearanceLightNavigationBars = !darkTheme
}

private fun setStatusBarAppearance(activity: Activity, color: Color, view: View) {
    activity.window.statusBarColor = color.toArgb()
    WindowCompat.getInsetsController(activity.window, view)
        .isAppearanceLightStatusBars = false
}

object WhiteTintRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor(): Color = Color.White

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleTheme.defaultRippleAlpha(
        Color.Black,
        lightTheme = !isSystemInDarkTheme()
    )
}