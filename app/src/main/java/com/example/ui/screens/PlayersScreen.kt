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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Player
import com.example.model.PlayerStats
import com.example.ui.components.CountryFlag
import com.example.ui.components.PlayerAvatarBadge
import com.example.ui.theme.LocalCricketColors

@Composable
fun PlayersScreen(
  players: List<Player>,
  modifier: Modifier = Modifier
) {
  val colors = LocalCricketColors.current
  var searchQuery by remember { mutableStateOf("") }
  var selectedPlayerId by remember { mutableIntStateOf(players.firstOrNull()?.id ?: 1) }
  val focusManager = LocalFocusManager.current

  val filteredPlayers = players.filter {
    it.name.contains(searchQuery, ignoreCase = true) ||
      it.country.contains(searchQuery, ignoreCase = true) ||
      it.role.contains(searchQuery, ignoreCase = true)
  }

  val selectedPlayer = players.find { it.id == selectedPlayerId } ?: players.firstOrNull()

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(colors.paper)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Spacer(modifier = Modifier.height(10.dp))
      // Search Bar
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = {
          Text(
            text = "Search player, country, or role...",
            color = colors.inkDim,
            fontSize = 14.sp
          )
        },
        leadingIcon = {
          Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            tint = colors.inkDim
          )
        },
        trailingIcon = {
          if (searchQuery.isNotEmpty()) {
            IconButton(onClick = { searchQuery = "" }) {
              Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Clear",
                tint = colors.inkDim
              )
            }
          }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("player_search_input"),
        colors = OutlinedTextFieldDefaults.colors(
          focusedContainerColor = colors.card,
          unfocusedContainerColor = colors.card,
          focusedBorderColor = colors.willow,
          unfocusedBorderColor = colors.line,
          focusedTextColor = colors.ink,
          unfocusedTextColor = colors.ink
        ),
        shape = RoundedCornerShape(8.dp)
      )
    }

    // Selected Player Detail Card at the top
    if (selectedPlayer != null) {
      item {
        PlayerDetailCard(player = selectedPlayer)
      }
    }

    item {
      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = "ALL PLAYERS (${filteredPlayers.size})",
        color = colors.inkDim,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace,
        letterSpacing = 1.sp
      )
    }

    // Player List Items
    items(filteredPlayers, key = { it.id }) { player ->
      val isSelected = player.id == selectedPlayerId
      PlayerListItem(
        player = player,
        isSelected = isSelected,
        onClick = { selectedPlayerId = player.id }
      )
    }

    item {
      Spacer(modifier = Modifier.height(30.dp))
    }
  }
}

@Composable
fun PlayerListItem(
  player: Player,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val colors = LocalCricketColors.current

  Row(
    modifier = modifier
      .fillMaxWidth()
      .testTag("player_item_${player.id}")
      .clip(RoundedCornerShape(8.dp))
      .background(if (isSelected) colors.card else colors.card.copy(alpha = 0.8f))
      .border(
        width = if (isSelected) 1.5.dp else 1.dp,
        color = if (isSelected) colors.willow else colors.line,
        shape = RoundedCornerShape(8.dp)
      )
      .clickable { onClick() }
      .padding(horizontal = 14.dp, vertical = 12.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      PlayerAvatarBadge(
        name = player.name,
        size = 36.dp,
        backgroundColor = if (isSelected) colors.willow.copy(alpha = 0.2f) else colors.line,
        textColor = if (isSelected) colors.willow else colors.ink
      )
      Spacer(modifier = Modifier.width(12.dp))
      Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
          CountryFlag(country = player.country, size = 14.dp)
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = player.name,
            color = colors.ink,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
          )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = "${player.country} · ${player.role}",
          color = colors.inkDim,
          fontSize = 11.sp
        )
      }
    }

    Icon(
      imageVector = Icons.Default.ChevronRight,
      contentDescription = "Select",
      tint = if (isSelected) colors.willow else colors.inkDim,
      modifier = Modifier.size(18.dp)
    )
  }
}

@Composable
fun PlayerDetailCard(
  player: Player,
  modifier: Modifier = Modifier
) {
  val colors = LocalCricketColors.current
  val isBowler = player.odi.wkts != null

  Card(
    modifier = modifier
      .fillMaxWidth()
      .border(1.dp, colors.line, RoundedCornerShape(12.dp)),
    colors = CardDefaults.cardColors(containerColor = colors.card),
    shape = RoundedCornerShape(12.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp)
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            CountryFlag(country = player.country, size = 24.dp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = player.name,
              color = colors.ink,
              fontSize = 22.sp,
              fontWeight = FontWeight.Bold
            )
          }
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "${player.country.uppercase()} · ${player.role.uppercase()}",
            color = colors.ball,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            letterSpacing = 1.sp
          )
        }
        PlayerAvatarBadge(
          name = player.name,
          size = 48.dp,
          backgroundColor = colors.line,
          textColor = colors.willow
        )
      }

      Spacer(modifier = Modifier.height(16.dp))
      HorizontalDivider(color = colors.line)
      Spacer(modifier = Modifier.height(10.dp))

      // Career Stats Table
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "FORMAT",
          color = colors.inkDim,
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Monospace,
          modifier = Modifier.weight(1f)
        )
        Text(
          text = if (isBowler) "WKTS" else "RUNS",
          color = colors.inkDim,
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Monospace,
          textAlign = TextAlign.End,
          modifier = Modifier.weight(1f)
        )
        Text(
          text = "AVG",
          color = colors.inkDim,
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Monospace,
          textAlign = TextAlign.End,
          modifier = Modifier.weight(1f)
        )
        Text(
          text = if (isBowler) "ECON" else "S/R",
          color = colors.inkDim,
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Monospace,
          textAlign = TextAlign.End,
          modifier = Modifier.weight(1f)
        )
      }

      val formatsList = listOf(
        Triple("Test", player.test, isBowler),
        Triple("ODI", player.odi, isBowler),
        Triple("T20", player.t20, isBowler)
      )

      formatsList.forEach { (fmtName, stats, bowler) ->
        StatRowItem(formatName = fmtName, stats = stats, isBowler = bowler)
      }
    }
  }
}

@Composable
fun StatRowItem(
  formatName: String,
  stats: PlayerStats,
  isBowler: Boolean
) {
  val colors = LocalCricketColors.current

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 8.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = formatName,
      color = colors.ink,
      fontSize = 14.sp,
      fontWeight = FontWeight.Medium,
      modifier = Modifier.weight(1f)
    )
    Text(
      text = if (isBowler) (stats.wkts?.toString() ?: "-") else (stats.runs?.toString() ?: "-"),
      color = colors.ball,
      fontSize = 14.sp,
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.Monospace,
      textAlign = TextAlign.End,
      modifier = Modifier.weight(1f)
    )
    Text(
      text = stats.avg,
      color = colors.inkDim,
      fontSize = 13.sp,
      fontFamily = FontFamily.Monospace,
      textAlign = TextAlign.End,
      modifier = Modifier.weight(1f)
    )
    Text(
      text = if (isBowler) (stats.econ ?: "-") else (stats.sr ?: "-"),
      color = colors.inkDim,
      fontSize = 13.sp,
      fontFamily = FontFamily.Monospace,
      textAlign = TextAlign.End,
      modifier = Modifier.weight(1f)
    )
  }
}
