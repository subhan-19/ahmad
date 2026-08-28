package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class CricketColors(
  val paper: Color,
  val card: Color,
  val ink: Color,
  val inkDim: Color,
  val ball: Color,
  val willow: Color,
  val pitch: Color,
  val line: Color,
  val lineStrong: Color,
  val isDark: Boolean
)

val LocalCricketColors = staticCompositionLocalOf {
  CricketColors(
    paper = LightPaper,
    card = LightCard,
    ink = LightInk,
    inkDim = LightInkDim,
    ball = LightBall,
    willow = LightWillow,
    pitch = LightPitch,
    line = LightLine,
    lineStrong = LightLineStrong,
    isDark = false
  )
}

private val DarkCricketColors = CricketColors(
  paper = DarkPaper,
  card = DarkCard,
  ink = DarkInk,
  inkDim = DarkInkDim,
  ball = DarkBall,
  willow = DarkWillow,
  pitch = DarkPitch,
  line = DarkLine,
  lineStrong = DarkLineStrong,
  isDark = true
)

private val LightCricketColors = CricketColors(
  paper = LightPaper,
  card = LightCard,
  ink = LightInk,
  inkDim = LightInkDim,
  ball = LightBall,
  willow = LightWillow,
  pitch = LightPitch,
  line = LightLine,
  lineStrong = LightLineStrong,
  isDark = false
)

private val DarkColorScheme = darkColorScheme(
  primary = DarkBall,
  onPrimary = Color.White,
  secondary = DarkWillow,
  onSecondary = Color.Black,
  tertiary = DarkPitch,
  background = DarkPaper,
  onBackground = DarkInk,
  surface = DarkCard,
  onSurface = DarkInk,
  surfaceVariant = DarkLine,
  onSurfaceVariant = DarkInkDim,
  outline = DarkLineStrong
)

private val LightColorScheme = lightColorScheme(
  primary = LightBall,
  onPrimary = Color.White,
  secondary = LightWillow,
  onSecondary = Color.White,
  tertiary = LightPitch,
  background = LightPaper,
  onBackground = LightInk,
  surface = LightCard,
  onSurface = LightInk,
  surfaceVariant = LightLine,
  onSurfaceVariant = LightInkDim,
  outline = LightLineStrong
)

@Composable
fun AHXCricketTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
  val cricketColors = if (darkTheme) DarkCricketColors else LightCricketColors

  CompositionLocalProvider(LocalCricketColors provides cricketColors) {
    MaterialTheme(
      colorScheme = colorScheme,
      typography = Typography,
      content = content
    )
  }
}

