package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CountryFlag(
  country: String,
  modifier: Modifier = Modifier,
  size: Dp = 20.dp
) {
  Box(
    modifier = modifier
      .size(size)
      .clip(CircleShape)
      .border(1.dp, Color(0x22000000), CircleShape)
  ) {
    Canvas(modifier = Modifier.matchParentSize()) {
      val w = this.size.width
      val h = this.size.height

      when (country.trim()) {
        "Pakistan", "PAK" -> {
          drawRect(color = Color(0xFF01411C), size = this.size)
          drawRect(color = Color.White, size = Size(w * 0.25f, h))
          drawCircle(color = Color.White, radius = w * 0.28f, center = Offset(w * 0.65f, h * 0.5f))
          drawCircle(color = Color(0xFF01411C), radius = w * 0.22f, center = Offset(w * 0.70f, h * 0.5f))
        }
        "India", "IND" -> {
          drawRect(color = Color(0xFFFF9933), size = Size(w, h * 0.333f))
          drawRect(color = Color.White, topLeft = Offset(0f, h * 0.333f), size = Size(w, h * 0.334f))
          drawRect(color = Color(0xFF138808), topLeft = Offset(0f, h * 0.667f), size = Size(w, h * 0.333f))
          val center = Offset(w * 0.5f, h * 0.5f)
          val r = h * 0.14f
          drawCircle(color = Color(0xFF000088), radius = r, center = center, style = Stroke(width = 1.5f))
          for (i in 0 until 12) {
            val angle = (i * 30.0 * Math.PI / 180.0)
            drawLine(
              color = Color(0xFF000088),
              start = center,
              end = Offset((center.x + r * cos(angle)).toFloat(), (center.y + r * sin(angle)).toFloat()),
              strokeWidth = 1f
            )
          }
        }
        "Australia", "AUS" -> {
          drawRect(color = Color(0xFF00247D), size = this.size)
          drawRect(color = Color.White, size = Size(w * 0.45f, h * 0.45f))
          drawRect(color = Color(0xFFCF142B), topLeft = Offset(w * 0.18f, 0f), size = Size(w * 0.10f, h * 0.45f))
          drawRect(color = Color(0xFFCF142B), topLeft = Offset(0f, h * 0.18f), size = Size(w * 0.45f, h * 0.10f))
          drawCircle(color = Color.White, radius = w * 0.06f, center = Offset(w * 0.75f, h * 0.35f))
          drawCircle(color = Color.White, radius = w * 0.06f, center = Offset(w * 0.8f, h * 0.65f))
          drawCircle(color = Color.White, radius = w * 0.06f, center = Offset(w * 0.65f, h * 0.8f))
        }
        "New Zealand", "NZ" -> {
          drawRect(color = Color(0xFF111111), size = this.size)
          val fernPath = Path().apply {
            moveTo(w * 0.2f, h * 0.8f)
            quadraticTo(w * 0.5f, h * 0.4f, w * 0.8f, h * 0.2f)
            quadraticTo(w * 0.6f, h * 0.5f, w * 0.4f, h * 0.85f)
            close()
          }
          drawPath(path = fernPath, color = Color.White)
          drawCircle(color = Color(0xFFCC1122), radius = w * 0.06f, center = Offset(w * 0.75f, h * 0.4f))
          drawCircle(color = Color(0xFFCC1122), radius = w * 0.05f, center = Offset(w * 0.6f, h * 0.7f))
        }
        "England", "ENG" -> {
          drawRect(color = Color.White, size = this.size)
          drawRect(color = Color(0xFFCE1124), topLeft = Offset(w * 0.4f, 0f), size = Size(w * 0.2f, h))
          drawRect(color = Color(0xFFCE1124), topLeft = Offset(0f, h * 0.4f), size = Size(w, h * 0.2f))
        }
        "South Africa", "SA" -> {
          drawRect(color = Color(0xFFDE3831), size = Size(w, h * 0.5f))
          drawRect(color = Color(0xFF002395), topLeft = Offset(0f, h * 0.5f), size = Size(w, h * 0.5f))
          val yPath = Path().apply {
            moveTo(0f, 0f)
            lineTo(w * 0.45f, h * 0.5f)
            lineTo(0f, h)
            close()
          }
          drawPath(path = yPath, color = Color(0xFFFFB612))
          val blackPath = Path().apply {
            moveTo(0f, h * 0.15f)
            lineTo(w * 0.35f, h * 0.5f)
            lineTo(0f, h * 0.85f)
            close()
          }
          drawPath(path = blackPath, color = Color.Black)
          drawRect(color = Color(0xFF007A4D), topLeft = Offset(w * 0.35f, h * 0.38f), size = Size(w * 0.65f, h * 0.24f))
        }
        "Bangladesh", "BAN" -> {
          drawRect(color = Color(0xFF006A4E), size = this.size)
          drawCircle(color = Color(0xFFF42A41), radius = w * 0.28f, center = Offset(w * 0.45f, h * 0.5f))
        }
        "Sri Lanka", "SL" -> {
          drawRect(color = Color(0xFFFFB612), size = this.size)
          drawRect(color = Color(0xFF00534E), topLeft = Offset(w * 0.05f, h * 0.05f), size = Size(w * 0.15f, h * 0.9f))
          drawRect(color = Color(0xFFEA7328), topLeft = Offset(w * 0.22f, h * 0.05f), size = Size(w * 0.15f, h * 0.9f))
          drawRect(color = Color(0xFF8D153A), topLeft = Offset(w * 0.4f, h * 0.05f), size = Size(w * 0.55f, h * 0.9f))
        }
        "Afghanistan", "AFG" -> {
          drawRect(color = Color.Black, size = Size(w * 0.333f, h))
          drawRect(color = Color(0xFFD32011), topLeft = Offset(w * 0.333f, 0f), size = Size(w * 0.334f, h))
          drawRect(color = Color(0xFF007A36), topLeft = Offset(w * 0.667f, 0f), size = Size(w * 0.333f, h))
        }
        "West Indies", "WI" -> {
          drawRect(color = Color(0xFF7A1E30), size = this.size)
          drawCircle(color = Color(0xFFFFD100), radius = w * 0.24f, center = Offset(w * 0.5f, h * 0.45f))
          val palmPath = Path().apply {
            moveTo(w * 0.25f, h * 0.85f)
            lineTo(w * 0.5f, h * 0.55f)
            lineTo(w * 0.75f, h * 0.85f)
            close()
          }
          drawPath(palmPath, color = Color(0xFF1B5E20))
        }
        "Ireland", "IRE" -> {
          drawRect(color = Color(0xFF169B62), size = Size(w * 0.333f, h))
          drawRect(color = Color.White, topLeft = Offset(w * 0.333f, 0f), size = Size(w * 0.334f, h))
          drawRect(color = Color(0xFFFF883E), topLeft = Offset(w * 0.667f, 0f), size = Size(w * 0.333f, h))
        }
        else -> {
          drawRect(color = Color(0xFF556B2F), size = this.size)
          drawCircle(color = Color.White, radius = w * 0.25f, center = Offset(w * 0.5f, h * 0.5f))
        }
      }
    }
  }
}

@Composable
fun TeamCrestBadge(
  country: String,
  size: Dp = 54.dp
) {
  Box(
    modifier = Modifier
      .size(size)
      .clip(CircleShape)
      .background(Color.White)
      .border(1.dp, Color(0x18000000), CircleShape),
    contentAlignment = Alignment.Center
  ) {
    CountryFlag(country = country, size = size * 0.82f)
  }
}

@Composable
fun PlayerAvatarBadge(
  name: String,
  size: Dp = 40.dp,
  backgroundColor: Color,
  textColor: Color
) {
  val initials = name.split(" ")
    .filter { it.isNotBlank() }
    .mapNotNull { it.firstOrNull()?.uppercaseChar() }
    .take(2)
    .joinToString("")

  Box(
    modifier = Modifier
      .size(size)
      .clip(CircleShape)
      .background(backgroundColor)
      .border(1.dp, Color(0x22000000), CircleShape),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = initials,
      color = textColor,
      fontSize = (size.value * 0.36f).sp,
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.Monospace
    )
  }
}
