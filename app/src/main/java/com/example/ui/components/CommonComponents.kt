package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.LocalCricketColors

@Composable
fun SeamDivider(modifier: Modifier = Modifier) {
  val colors = LocalCricketColors.current
  Box(
    modifier = modifier
      .fillMaxWidth()
      .height(12.dp)
      .padding(vertical = 4.dp)
  ) {
    Canvas(modifier = Modifier.fillMaxWidth().height(10.dp)) {
      val midY = size.height / 2
      // Solid base seam line
      drawLine(
        color = colors.ball.copy(alpha = 0.85f),
        start = Offset(0f, midY),
        end = Offset(size.width, midY),
        strokeWidth = 2f
      )
      // Dashed stitch pattern
      drawLine(
        color = colors.ball,
        start = Offset(0f, midY - 3f),
        end = Offset(size.width, midY - 3f),
        strokeWidth = 1.5f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f)
      )
      drawLine(
        color = colors.ball,
        start = Offset(0f, midY + 3f),
        end = Offset(size.width, midY + 3f),
        strokeWidth = 1.5f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 3f)
      )
    }
  }
}

@Composable
fun LiveBadge(modifier: Modifier = Modifier) {
  val colors = LocalCricketColors.current
  val infiniteTransition = rememberInfiniteTransition(label = "pulse_trans")
  val alpha by infiniteTransition.animateFloat(
    initialValue = 1f,
    targetValue = 0.3f,
    animationSpec = infiniteRepeatable(
      animation = tween(800, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulse_alpha"
  )

  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Box(
      modifier = Modifier
        .size(8.dp)
        .alpha(alpha)
        .clip(CircleShape)
        .background(colors.ball)
    )
    Spacer(modifier = Modifier.width(6.dp))
    Text(
      text = "LIVE",
      color = colors.ball,
      fontSize = 11.sp,
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.Monospace,
      letterSpacing = 1.5.sp
    )
  }
}

@Composable
fun FormatBadge(
  format: String,
  modifier: Modifier = Modifier
) {
  val colors = LocalCricketColors.current
  Box(
    modifier = modifier
      .border(1.dp, colors.lineStrong, RoundedCornerShape(4.dp))
      .background(colors.card)
      .padding(horizontal = 7.dp, vertical = 2.dp)
  ) {
    Text(
      text = format,
      color = colors.inkDim,
      fontSize = 10.sp,
      fontWeight = FontWeight.SemiBold,
      fontFamily = FontFamily.Monospace,
      letterSpacing = 1.sp
    )
  }
}

@Composable
fun BallDot(
  value: String,
  modifier: Modifier = Modifier
) {
  val colors = LocalCricketColors.current
  val isWicket = value.equals("W", ignoreCase = true)
  val isBoundary = value == "4" || value == "6"

  val bg = when {
    isWicket -> colors.ball
    isBoundary -> colors.pitch
    else -> colors.paper
  }

  val textColor = when {
    isWicket || isBoundary -> Color.White
    else -> colors.inkDim
  }

  val borderColor = when {
    isWicket -> colors.ball
    isBoundary -> colors.pitch
    else -> colors.lineStrong
  }

  Box(
    modifier = modifier
      .size(24.dp)
      .clip(CircleShape)
      .background(bg)
      .border(1.dp, borderColor, CircleShape),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = value,
      color = textColor,
      fontSize = 11.sp,
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.Monospace
    )
  }
}
