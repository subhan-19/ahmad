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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import com.example.model.RankItem
import com.example.ui.components.CountryFlag
import com.example.ui.theme.LocalCricketColors

@Composable
fun RankingsScreen(
  rankingsData: Map<String, Map<String, List<RankItem>>>,
  modifier: Modifier = Modifier
) {
  val colors = LocalCricketColors.current
  var selectedFormat by remember { mutableStateOf("ODI") }
  var selectedType by remember { mutableStateOf("batting") }

  val formats = listOf("TEST", "ODI", "T20")
  val types = listOf("batting", "bowling")

  val currentList = rankingsData[selectedFormat]?.get(selectedType) ?: emptyList()

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(colors.paper)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Spacer(modifier = Modifier.height(10.dp))
      // Filter Format and Type Controls
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          formats.forEach { fmt ->
            val isSelected = selectedFormat == fmt
            Box(
              modifier = Modifier
                .testTag("rank_fmt_$fmt")
                .clip(RoundedCornerShape(6.dp))
                .background(if (isSelected) colors.ink else Color.Transparent)
                .border(
                  1.dp,
                  if (isSelected) colors.ink else colors.lineStrong,
                  RoundedCornerShape(6.dp)
                )
                .clickable { selectedFormat = fmt }
                .padding(horizontal = 14.dp, vertical = 7.dp)
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

          Spacer(modifier = Modifier.weight(1f))

          types.forEach { t ->
            val isSelected = selectedType == t
            Box(
              modifier = Modifier
                .testTag("rank_type_$t")
                .clip(RoundedCornerShape(6.dp))
                .background(if (isSelected) colors.ball else Color.Transparent)
                .border(
                  1.dp,
                  if (isSelected) colors.ball else colors.lineStrong,
                  RoundedCornerShape(6.dp)
                )
                .clickable { selectedType = t }
                .padding(horizontal = 12.dp, vertical = 7.dp)
            ) {
              Text(
                text = t.replaceFirstChar { it.uppercase() },
                color = if (isSelected) Color.White else colors.inkDim,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }
    }

    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, colors.line, RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(containerColor = colors.card),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(modifier = Modifier.fillMaxWidth()) {
          // Table Title
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .background(colors.line.copy(alpha = 0.35f))
              .padding(horizontal = 16.dp, vertical = 12.dp)
          ) {
            Text(
              text = "$selectedFormat ${if (selectedType == "batting") "Batting" else "Bowling"} Rankings",
              color = colors.ink,
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold
            )
          }

          HorizontalDivider(color = colors.line)

          // Table Rows
          currentList.forEachIndexed { index, item ->
            RankItemRow(item = item, isFirst = index == 0)
            if (index < currentList.lastIndex) {
              HorizontalDivider(color = colors.line.copy(alpha = 0.5f))
            }
          }
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(30.dp))
    }
  }
}

@Composable
fun RankItemRow(
  item: RankItem,
  isFirst: Boolean,
  modifier: Modifier = Modifier
) {
  val colors = LocalCricketColors.current

  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    // Rank Number
    Text(
      text = item.rank.toString(),
      color = if (item.rank == 1) colors.ball else colors.inkDim,
      fontSize = 16.sp,
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.SansSerif,
      modifier = Modifier.width(32.dp)
    )

    Spacer(modifier = Modifier.width(6.dp))

    // Player and Country
    Column(modifier = Modifier.weight(1f)) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        CountryFlag(country = item.country, size = 16.dp)
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = item.name,
          color = colors.ink,
          fontSize = 14.sp,
          fontWeight = FontWeight.SemiBold
        )
      }
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = item.country,
        color = colors.inkDim,
        fontSize = 11.sp
      )
    }

    // Rating
    Text(
      text = item.rating.toString(),
      color = colors.willow,
      fontSize = 14.sp,
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.Monospace
    )
  }
}
