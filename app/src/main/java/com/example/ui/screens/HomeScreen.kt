package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.LiveMatch
import com.example.model.UpcomingMatch
import com.example.ui.components.BallDot
import com.example.ui.components.CountryFlag
import com.example.ui.components.FormatBadge
import com.example.ui.components.LiveBadge
import com.example.ui.components.SeamDivider
import com.example.ui.theme.LocalCricketColors

@Composable
fun HomeScreen(
  matches: List<LiveMatch>,
  upcomingMatches: List<UpcomingMatch>,
  onMatchClick: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  val colors = LocalCricketColors.current
  var selectedFormat by remember { mutableStateOf("ALL") }
  val formats = listOf("ALL", "TEST", "ODI", "T20")

  val filteredMatches = matches.filter {
    selectedFormat == "ALL" || it.format.equals(selectedFormat, ignoreCase = true)
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(colors.paper)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Spacer(modifier = Modifier.height(6.dp))
      SeamDivider()
    }

    // Format Filter Row
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        formats.forEach { fmt ->
          val isSelected = selectedFormat == fmt
          Box(
            modifier = Modifier
              .testTag("filter_format_$fmt")
              .clip(RoundedCornerShape(6.dp))
              .background(if (isSelected) colors.ink else Color.Transparent)
              .border(
                1.dp,
                if (isSelected) colors.ink else colors.lineStrong,
                RoundedCornerShape(6.dp)
              )
              .clickable { selectedFormat = fmt }
              .padding(horizontal = 16.dp, vertical = 7.dp)
          ) {
            Text(
              text = fmt,
              color = if (isSelected) colors.paper else colors.inkDim,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace,
              letterSpacing = 1.sp
            )
          }
        }
      }
    }

    // Live Matches List
    if (filteredMatches.isEmpty()) {
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "No live matches in $selectedFormat format right now.",
            color = colors.inkDim,
            fontSize = 14.sp
          )
        }
      }
    } else {
      items(filteredMatches, key = { it.id }) { match ->
        LiveMatchCard(
          match = match,
          onClick = { onMatchClick(match.id) }
        )
      }
    }

    // Upcoming Section Header
    item {
      Spacer(modifier = Modifier.height(10.dp))
      Text(
        text = "UPCOMING FIXTURES",
        color = colors.ink,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.SansSerif,
        letterSpacing = 1.sp
      )
      HorizontalDivider(
        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
        color = colors.line
      )
    }

    // Upcoming Fixture Rows
    items(upcomingMatches, key = { it.id }) { upcoming ->
      UpcomingMatchRow(match = upcoming)
    }

    item {
      Spacer(modifier = Modifier.height(30.dp))
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 24.dp),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = "All copyrights reserved to Ahx Cricket Amad056",
          color = colors.inkDim,
          fontSize = 11.sp,
          fontFamily = FontFamily.Monospace
        )
      }
    }
  }
}

@Composable
fun LiveMatchCard(
  match: LiveMatch,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val colors = LocalCricketColors.current

  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("match_card_${match.id}")
      .border(1.dp, colors.line, RoundedCornerShape(10.dp))
      .clickable { onClick() },
    colors = CardDefaults.cardColors(containerColor = colors.card),
    shape = RoundedCornerShape(10.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      // Header: Format, Series & Live tag
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          FormatBadge(format = match.format)
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = match.series,
            color = colors.inkDim,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
          )
        }
        LiveBadge()
      }

      Spacer(modifier = Modifier.height(10.dp))
      HorizontalDivider(color = colors.line.copy(alpha = 0.6f))
      Spacer(modifier = Modifier.height(8.dp))

      // Team A Score
      TeamScoreLine(
        teamName = match.teamA.name,
        score = match.teamA.score,
        overs = match.teamA.overs,
        rr = match.teamA.rr
      )

      HorizontalDivider(
        color = colors.line.copy(alpha = 0.5f),
        modifier = Modifier.padding(vertical = 4.dp)
      )

      // Team B Score
      TeamScoreLine(
        teamName = match.teamB.name,
        score = match.teamB.score,
        overs = match.teamB.overs,
        rr = match.teamB.rr
      )

      Spacer(modifier = Modifier.height(10.dp))
      HorizontalDivider(color = colors.line)
      Spacer(modifier = Modifier.height(10.dp))

      // Batsmen and Bowler in Crease
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Column {
          match.batsmen.forEach { b ->
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = b.name,
                color = colors.ink,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "${b.runs}(${b.balls})",
                color = colors.inkDim,
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace
              )
            }
          }
        }
        Column(horizontalAlignment = Alignment.End) {
          Text(
            text = match.bowler.name,
            color = colors.ink,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
          )
          Text(
            text = "${match.bowler.overs}-0-${match.bowler.runs}-${match.bowler.wkts}",
            color = colors.inkDim,
            fontSize = 12.sp,
            fontFamily = FontFamily.Monospace
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // THIS OVER Balls
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        Text(
          text = "THIS OVER",
          color = colors.inkDim,
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Monospace,
          letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.width(4.dp))
        match.thisOver.forEach { ball ->
          BallDot(value = ball)
        }
      }

      Spacer(modifier = Modifier.height(12.dp))
      HorizontalDivider(color = colors.line)
      Spacer(modifier = Modifier.height(8.dp))

      // Summary
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = match.summary,
            color = colors.willow,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
          )
          if (!match.teamB.reqRr.isNullOrBlank()) {
            Text(
              text = "req. RR ${match.teamB.reqRr}",
              color = colors.inkDim,
              fontSize = 11.sp,
              fontFamily = FontFamily.Monospace
            )
          }
        }
        Icon(
          imageVector = Icons.Default.ChevronRight,
          contentDescription = "View scorecard",
          tint = colors.willow,
          modifier = Modifier.size(20.dp)
        )
      }
    }
  }
}

@Composable
fun TeamScoreLine(
  teamName: String,
  score: String,
  overs: String,
  rr: String,
  modifier: Modifier = Modifier
) {
  val colors = LocalCricketColors.current

  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.weight(1f)
    ) {
      CountryFlag(country = teamName, size = 20.dp)
      Spacer(modifier = Modifier.width(8.dp))
      Text(
        text = teamName,
        color = colors.ink,
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold
      )
    }

    Row(
      verticalAlignment = Alignment.Bottom,
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Text(
        text = score,
        color = colors.ink,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.SansSerif
      )
      if (overs.isNotBlank()) {
        Text(
          text = overs,
          color = colors.inkDim,
          fontSize = 12.sp,
          fontFamily = FontFamily.Monospace,
          modifier = Modifier.padding(bottom = 3.dp)
        )
      }
      if (rr.isNotBlank()) {
        Text(
          text = "RR $rr",
          color = colors.pitch,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Monospace,
          modifier = Modifier.padding(bottom = 3.dp)
        )
      }
    }
  }
}

@Composable
fun UpcomingMatchRow(
  match: UpcomingMatch,
  modifier: Modifier = Modifier
) {
  val colors = LocalCricketColors.current

  Row(
    modifier = modifier
      .fillMaxWidth()
      .border(1.dp, colors.line, RoundedCornerShape(8.dp))
      .background(colors.card, RoundedCornerShape(8.dp))
      .padding(horizontal = 14.dp, vertical = 12.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        FormatBadge(format = match.format)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = match.series,
          color = colors.inkDim,
          fontSize = 11.sp
        )
      }
      Spacer(modifier = Modifier.height(6.dp))
      Row(verticalAlignment = Alignment.CenterVertically) {
        CountryFlag(country = match.teamA, size = 16.dp)
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = match.teamA,
          color = colors.ink,
          fontSize = 14.sp,
          fontWeight = FontWeight.Medium
        )
        Text(
          text = " vs ",
          color = colors.inkDim,
          fontSize = 12.sp,
          modifier = Modifier.padding(horizontal = 4.dp)
        )
        CountryFlag(country = match.teamB, size = 16.dp)
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = match.teamB,
          color = colors.ink,
          fontSize = 14.sp,
          fontWeight = FontWeight.Medium
        )
      }
    }

    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      Icon(
        imageVector = Icons.Outlined.Schedule,
        contentDescription = "Match Time",
        tint = colors.inkDim,
        modifier = Modifier.size(14.dp)
      )
      Text(
        text = match.time,
        color = colors.inkDim,
        fontSize = 11.sp,
        fontFamily = FontFamily.Monospace
      )
    }
  }
}
