package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BattingCardEntry
import com.example.model.BowlingCardEntry
import com.example.model.InningsCard
import com.example.model.LiveMatch
import com.example.model.MatchScorecard
import com.example.ui.components.CountryFlag
import com.example.ui.components.PlayerAvatarBadge
import com.example.ui.components.TeamCrestBadge
import com.example.ui.theme.LocalCricketColors

@Composable
fun ScorecardScreen(
  match: LiveMatch,
  scorecard: MatchScorecard?,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  val colors = LocalCricketColors.current
  var selectedInningsTab by remember { mutableIntStateOf(0) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(colors.paper)
  ) {
    // Top Bar (Google cricket style header)
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(colors.ink)
        .padding(horizontal = 12.dp, vertical = 10.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(
        onClick = onBack,
        modifier = Modifier.testTag("scorecard_back_button")
      ) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowBack,
          contentDescription = "Back",
          tint = colors.paper
        )
      }
      Spacer(modifier = Modifier.width(8.dp))
      Text(
        text = "${match.teamA.name} vs ${match.teamB.name}",
        color = colors.paper,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
      )
    }

    if (scorecard == null || scorecard.innings.isEmpty()) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(24.dp),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = "Full scorecard not available for this match yet.",
          color = colors.inkDim,
          fontSize = 14.sp
        )
      }
    } else {
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        item {
          Spacer(modifier = Modifier.height(10.dp))
          // Header Card with Teams and Result
          ScorecardHeaderCard(
            match = match,
            scorecard = scorecard
          )
        }

        // Innings Selector Tabs
        item {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .border(1.dp, colors.line, RoundedCornerShape(8.dp))
              .background(colors.card, RoundedCornerShape(8.dp))
          ) {
            scorecard.innings.forEachIndexed { index, inn ->
              val isSelected = selectedInningsTab == index
              Box(
                modifier = Modifier
                  .weight(1f)
                  .testTag("innings_tab_$index")
                  .clip(RoundedCornerShape(8.dp))
                  .background(if (isSelected) colors.line.copy(alpha = 0.5f) else Color.Transparent)
                  .clickable { selectedInningsTab = index }
                  .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.Center
                ) {
                  CountryFlag(country = inn.team, size = 18.dp)
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = inn.team,
                    color = if (isSelected) colors.ink else colors.inkDim,
                    fontSize = 13.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                  )
                }
              }
            }
          }
        }

        val currentInnings: InningsCard = scorecard.innings.getOrElse(selectedInningsTab) { scorecard.innings[0] }

        item {
          Text(
            text = "${currentInnings.team} — ${currentInnings.total}",
            color = colors.ink,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp)
          )
          HorizontalDivider(
            color = colors.ball,
            thickness = 2.dp,
            modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
          )
        }

        // Batting Table Header
        item {
          BattingTableHeader()
        }

        // Batting Rows
        items(currentInnings.batting) { b ->
          BattingRowItem(entry = b)
        }

        // Bowling Table Header
        item {
          Spacer(modifier = Modifier.height(14.dp))
          Text(
            text = "BOWLING",
            color = colors.ink,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            letterSpacing = 1.sp
          )
          BowlingTableHeader()
        }

        // Bowling Rows
        items(currentInnings.bowling) { bow ->
          BowlingRowItem(entry = bow)
        }

        item {
          Spacer(modifier = Modifier.height(40.dp))
        }
      }
    }
  }
}

@Composable
fun ScorecardHeaderCard(
  match: LiveMatch,
  scorecard: MatchScorecard,
  modifier: Modifier = Modifier
) {
  val colors = LocalCricketColors.current

  Card(
    modifier = modifier
      .fillMaxWidth()
      .border(1.dp, colors.line, RoundedCornerShape(12.dp)),
    colors = CardDefaults.cardColors(containerColor = colors.card),
    shape = RoundedCornerShape(12.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = "${match.series.uppercase()} · ${match.venue}",
        color = colors.inkDim,
        fontSize = 11.sp,
        fontFamily = FontFamily.Monospace,
        letterSpacing = 1.sp
      )

      Spacer(modifier = Modifier.height(18.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
      ) {
        val innA = scorecard.innings.getOrNull(0)
        val innB = scorecard.innings.getOrNull(1)

        if (innA != null) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
          ) {
            TeamCrestBadge(country = innA.team, size = 52.dp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = innA.total,
              color = colors.ink,
              fontSize = 17.sp,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = innA.team,
              color = colors.inkDim,
              fontSize = 13.sp,
              fontWeight = FontWeight.Medium
            )
          }
        }

        Text(
          text = "VS",
          color = colors.lineStrong,
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Monospace
        )

        if (innB != null) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
          ) {
            TeamCrestBadge(country = innB.team, size = 52.dp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = innB.total,
              color = colors.ink,
              fontSize = 17.sp,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = innB.team,
              color = colors.inkDim,
              fontSize = 13.sp,
              fontWeight = FontWeight.Medium
            )
          }
        }
      }

      if (scorecard.result.isNotBlank()) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
          text = scorecard.result,
          color = colors.ball,
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold
        )
      }
    }
  }
}

@Composable
fun BattingTableHeader() {
  val colors = LocalCricketColors.current
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .background(colors.card)
      .border(1.dp, colors.line, RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
      .padding(horizontal = 8.dp, vertical = 6.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = "BATTING",
      color = colors.inkDim,
      fontSize = 10.sp,
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.Monospace,
      modifier = Modifier.weight(2.4f)
    )
    Text(
      text = "R",
      color = colors.inkDim,
      fontSize = 10.sp,
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.Monospace,
      textAlign = TextAlign.End,
      modifier = Modifier.weight(0.7f)
    )
    Text(
      text = "B",
      color = colors.inkDim,
      fontSize = 10.sp,
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.Monospace,
      textAlign = TextAlign.End,
      modifier = Modifier.weight(0.7f)
    )
    Text(
      text = "4s",
      color = colors.inkDim,
      fontSize = 10.sp,
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.Monospace,
      textAlign = TextAlign.End,
      modifier = Modifier.weight(0.6f)
    )
    Text(
      text = "6s",
      color = colors.inkDim,
      fontSize = 10.sp,
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.Monospace,
      textAlign = TextAlign.End,
      modifier = Modifier.weight(0.6f)
    )
    Text(
      text = "S/R",
      color = colors.inkDim,
      fontSize = 10.sp,
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.Monospace,
      textAlign = TextAlign.End,
      modifier = Modifier.weight(1.0f)
    )
  }
}

@Composable
fun BattingRowItem(entry: BattingCardEntry) {
  val colors = LocalCricketColors.current
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .background(colors.card)
      .border(1.dp, colors.line)
      .padding(horizontal = 8.dp, vertical = 10.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Row(
      modifier = Modifier.weight(2.4f),
      verticalAlignment = Alignment.CenterVertically
    ) {
      PlayerAvatarBadge(
        name = entry.name,
        size = 30.dp,
        backgroundColor = colors.line,
        textColor = colors.willow
      )
      Spacer(modifier = Modifier.width(8.dp))
      Column {
        Text(
          text = entry.name,
          color = colors.ink,
          fontSize = 13.sp,
          fontWeight = FontWeight.SemiBold
        )
        Text(
          text = entry.info,
          color = colors.inkDim,
          fontSize = 10.sp
        )
      }
    }

    Text(
      text = entry.runs.toString(),
      color = colors.ink,
      fontSize = 13.sp,
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.Monospace,
      textAlign = TextAlign.End,
      modifier = Modifier.weight(0.7f)
    )
    Text(
      text = entry.balls.toString(),
      color = colors.inkDim,
      fontSize = 12.sp,
      fontFamily = FontFamily.Monospace,
      textAlign = TextAlign.End,
      modifier = Modifier.weight(0.7f)
    )
    Text(
      text = entry.fours.toString(),
      color = colors.inkDim,
      fontSize = 12.sp,
      fontFamily = FontFamily.Monospace,
      textAlign = TextAlign.End,
      modifier = Modifier.weight(0.6f)
    )
    Text(
      text = entry.sixes.toString(),
      color = colors.inkDim,
      fontSize = 12.sp,
      fontFamily = FontFamily.Monospace,
      textAlign = TextAlign.End,
      modifier = Modifier.weight(0.6f)
    )
    Text(
      text = entry.sr,
      color = colors.inkDim,
      fontSize = 11.sp,
      fontFamily = FontFamily.Monospace,
      textAlign = TextAlign.End,
      modifier = Modifier.weight(1.0f)
    )
  }
}

@Composable
fun BowlingTableHeader() {
  val colors = LocalCricketColors.current
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .background(colors.card)
      .border(1.dp, colors.line, RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
      .padding(horizontal = 8.dp, vertical = 6.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = "BOWLER",
      color = colors.inkDim,
      fontSize = 10.sp,
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.Monospace,
      modifier = Modifier.weight(2.4f)
    )
    Text(
      text = "O",
      color = colors.inkDim,
      fontSize = 10.sp,
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.Monospace,
      textAlign = TextAlign.End,
      modifier = Modifier.weight(0.7f)
    )
    Text(
      text = "M",
      color = colors.inkDim,
      fontSize = 10.sp,
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.Monospace,
      textAlign = TextAlign.End,
      modifier = Modifier.weight(0.6f)
    )
    Text(
      text = "R",
      color = colors.inkDim,
      fontSize = 10.sp,
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.Monospace,
      textAlign = TextAlign.End,
      modifier = Modifier.weight(0.7f)
    )
    Text(
      text = "W",
      color = colors.ball,
      fontSize = 10.sp,
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.Monospace,
      textAlign = TextAlign.End,
      modifier = Modifier.weight(0.6f)
    )
    Text(
      text = "ECON",
      color = colors.inkDim,
      fontSize = 10.sp,
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.Monospace,
      textAlign = TextAlign.End,
      modifier = Modifier.weight(1.0f)
    )
  }
}

@Composable
fun BowlingRowItem(entry: BowlingCardEntry) {
  val colors = LocalCricketColors.current
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .background(colors.card)
      .border(1.dp, colors.line)
      .padding(horizontal = 8.dp, vertical = 10.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Row(
      modifier = Modifier.weight(2.4f),
      verticalAlignment = Alignment.CenterVertically
    ) {
      PlayerAvatarBadge(
        name = entry.name,
        size = 30.dp,
        backgroundColor = colors.line,
        textColor = colors.willow
      )
      Spacer(modifier = Modifier.width(8.dp))
      Text(
        text = entry.name,
        color = colors.ink,
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold
      )
    }

    Text(
      text = entry.overs.toString(),
      color = colors.inkDim,
      fontSize = 12.sp,
      fontFamily = FontFamily.Monospace,
      textAlign = TextAlign.End,
      modifier = Modifier.weight(0.7f)
    )
    Text(
      text = entry.maidens.toString(),
      color = colors.inkDim,
      fontSize = 12.sp,
      fontFamily = FontFamily.Monospace,
      textAlign = TextAlign.End,
      modifier = Modifier.weight(0.6f)
    )
    Text(
      text = entry.runs.toString(),
      color = colors.inkDim,
      fontSize = 12.sp,
      fontFamily = FontFamily.Monospace,
      textAlign = TextAlign.End,
      modifier = Modifier.weight(0.7f)
    )
    Text(
      text = entry.wickets.toString(),
      color = colors.ball,
      fontSize = 13.sp,
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.Monospace,
      textAlign = TextAlign.End,
      modifier = Modifier.weight(0.6f)
    )
    Text(
      text = entry.econ,
      color = colors.inkDim,
      fontSize = 11.sp,
      fontFamily = FontFamily.Monospace,
      textAlign = TextAlign.End,
      modifier = Modifier.weight(1.0f)
    )
  }
}
