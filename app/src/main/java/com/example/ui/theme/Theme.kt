package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = PrimaryContainerGreen,
    onPrimary = OnPrimaryContainerGreen,
    secondary = SecondaryContainerOrange,
    onSecondary = OnSecondaryContainerOrange,
    tertiary = TertiaryContainerCocoa,
    background = BackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark
  )

private val LightColorScheme =
  lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = OnPrimaryGreen,
    primaryContainer = PrimaryContainerGreen,
    onPrimaryContainer = OnPrimaryContainerGreen,
    secondary = SecondaryOrange,
    onSecondary = OnSecondaryOrange,
    secondaryContainer = SecondaryContainerOrange,
    onSecondaryContainer = OnSecondaryContainerOrange,
    tertiary = TertiaryCocoa,
    background = BackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = true,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
