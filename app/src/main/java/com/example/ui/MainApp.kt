package com.example.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SportsCricket
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.SportsCricket
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CricketRepository
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.PlayersScreen
import com.example.ui.screens.RankingsScreen
import com.example.ui.screens.ScorecardScreen
import com.example.ui.screens.SquadsScreen
import com.example.ui.theme.LocalCricketColors
import kotlinx.coroutines.launch

sealed class AppNavTab(
  val route: String,
  val title: String,
  val selectedIcon: ImageVector,
  val unselectedIcon: ImageVector
) {
  object Home : AppNavTab("home", "Live & Fixtures", Icons.Filled.SportsCricket, Icons.Outlined.SportsCricket)
  object Players : AppNavTab("players", "Player Stats", Icons.Filled.Person, Icons.Outlined.Person)
  object Rankings : AppNavTab("rankings", "Rankings", Icons.Filled.EmojiEvents, Icons.Outlined.EmojiEvents)
  object Squads : AppNavTab("squads", "Squads", Icons.Filled.Groups, Icons.Outlined.Groups)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(
  isDarkTheme: Boolean,
  onToggleTheme: () -> Unit
) {
  val colors = LocalCricketColors.current
  var currentTab by remember { mutableStateOf<AppNavTab>(AppNavTab.Home) }
  var selectedMatchId by remember { mutableStateOf<Int?>(null) }
  var refreshCount by remember { mutableIntStateOf(0) }
  val snackbarHostState = remember { SnackbarHostState() }
  val scope = rememberCoroutineScope()

  val tabs = listOf(
    AppNavTab.Home,
    AppNavTab.Players,
    AppNavTab.Rankings,
    AppNavTab.Squads
  )

  Scaffold(
    modifier = Modifier
      .fillMaxSize()
      .background(colors.paper),
    snackbarHost = { SnackbarHost(snackbarHostState) },
    topBar = {
      TopAppBar(
        modifier = Modifier.statusBarsPadding(),
        title = {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.testTag("app_branding_title")
          ) {
            Text(
              text = "AHX",
              color = colors.ink,
              fontSize = 24.sp,
              fontWeight = FontWeight.Black,
              letterSpacing = 1.sp,
              fontFamily = FontFamily.SansSerif
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "CRICKET",
              color = colors.ball,
              fontSize = 24.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.sp,
              fontFamily = FontFamily.SansSerif
            )
          }
        },
        actions = {
          // Live Refresh Button
          IconButton(
            onClick = {
              refreshCount++
              scope.launch {
                snackbarHostState.showSnackbar("Live scores updated")
              }
            },
            modifier = Modifier.testTag("refresh_button")
          ) {
            Icon(
              imageVector = Icons.Default.Refresh,
              contentDescription = "Refresh Scores",
              tint = colors.inkDim
            )
          }

          // Theme Toggle Button
          IconButton(
            onClick = onToggleTheme,
            modifier = Modifier
              .testTag("theme_toggle_button")
              .clip(CircleShape)
          ) {
            Icon(
              imageVector = if (isDarkTheme) Icons.Filled.LightMode else Icons.Filled.DarkMode,
              contentDescription = "Toggle Theme",
              tint = colors.ink
            )
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = colors.card,
          titleContentColor = colors.ink
        )
      )
    },
    bottomBar = {
      Column(modifier = Modifier.navigationBarsPadding()) {
        HorizontalDivider(color = colors.line)
        NavigationBar(
          containerColor = colors.card,
          contentColor = colors.ink
        ) {
          tabs.forEach { tab ->
            val isSelected = currentTab == tab && selectedMatchId == null
            NavigationBarItem(
              modifier = Modifier.testTag("nav_tab_${tab.route}"),
              selected = isSelected,
              onClick = {
                selectedMatchId = null
                currentTab = tab
              },
              icon = {
                Icon(
                  imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                  contentDescription = tab.title,
                  modifier = Modifier.size(22.dp)
                )
              },
              label = {
                Text(
                  text = tab.title,
                  fontSize = 11.sp,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                  maxLines = 1
                )
              },
              colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colors.ball,
                selectedTextColor = colors.ball,
                indicatorColor = colors.ball.copy(alpha = 0.12f),
                unselectedIconColor = colors.inkDim,
                unselectedTextColor = colors.inkDim
              )
            )
          }
        }
      }
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      if (selectedMatchId != null) {
        val selectedMatch = CricketRepository.LIVE_MATCHES.find { it.id == selectedMatchId }
        val scorecard = CricketRepository.SCORECARDS[selectedMatchId]

        if (selectedMatch != null) {
          ScorecardScreen(
            match = selectedMatch,
            scorecard = scorecard,
            onBack = { selectedMatchId = null }
          )
        }
      } else {
        AnimatedContent(
          targetState = currentTab,
          transitionSpec = { fadeIn() togetherWith fadeOut() },
          label = "screen_transition"
        ) { targetTab ->
          when (targetTab) {
            AppNavTab.Home -> {
              HomeScreen(
                matches = CricketRepository.LIVE_MATCHES,
                upcomingMatches = CricketRepository.UPCOMING_MATCHES,
                onMatchClick = { matchId ->
                  selectedMatchId = matchId
                }
              )
            }
            AppNavTab.Players -> {
              PlayersScreen(players = CricketRepository.PLAYERS)
            }
            AppNavTab.Rankings -> {
              RankingsScreen(rankingsData = CricketRepository.RANKINGS)
            }
            AppNavTab.Squads -> {
              SquadsScreen(squads = CricketRepository.SQUADS)
            }
          }
        }
      }
    }
  }
}
