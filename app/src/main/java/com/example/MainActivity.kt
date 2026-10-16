package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.EvolutionViewModel

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
  object Home : Screen("home", "Home", Icons.Default.Home)
  object RhymeFinder : Screen("rhymes", "Rhymes", Icons.Default.Search)
  object PeerTutor : Screen("peer_tutor", "Tutors", Icons.Default.People)
  object Summarizer : Screen("summarizer", "Summary", Icons.Default.Description)
  object OerCurator : Screen("oer", "OER", Icons.Default.MenuBook)
  object SubBuilder : Screen("sub_builder", "Sub Folder", Icons.Default.FolderSpecial)
  object MissingNotifier : Screen("missing_notifier", "Notifier", Icons.Default.NotificationImportant)
  object Snippet : Screen("snippet", "Snippet", Icons.Default.Code)
  object Gratitude : Screen("gratitude", "Gratitude", Icons.Default.Favorite)
  object Icebreaker : Screen("icebreaker", "Icebreaker", Icons.Default.QuestionAnswer)
  object Prolonger : Screen("prolonger", "Prolonger", Icons.Default.Shuffle)
  object TechAi : Screen("tech_ai", "AI Tutor", Icons.Default.SmartToy)
  object Hub : Screen("hub", "Hub", Icons.Default.School)
}

class MainActivity : ComponentActivity() {
  private val viewModel: EvolutionViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val studentPoints by viewModel.studentPoints.collectAsState()
        val studyStreak by viewModel.studyStreak.collectAsState()

        val bottomNavItems = listOf(
          Screen.Home,
          Screen.RhymeFinder,
          Screen.PeerTutor,
          Screen.OerCurator,
          Screen.TechAi
        )

        Scaffold(
          modifier = Modifier.fillMaxSize(),
          bottomBar = {
            NavigationBar {
              bottomNavItems.forEach { screen ->
                NavigationBarItem(
                  icon = { Icon(screen.icon, contentDescription = screen.title) },
                  label = { Text(screen.title) },
                  selected = currentRoute == screen.route,
                  onClick = {
                    if (currentRoute != screen.route) {
                      navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                          saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                      }
                    }
                  },
                  modifier = Modifier.testTag("nav_${screen.route}")
                )
              }
            }
          }
        ) { innerPadding ->
          NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
          ) {
            composable(Screen.Home.route) {
              HomeScreen(
                onNavigateToRhymeFinder = { navController.navigate(Screen.RhymeFinder.route) },
                onNavigateToPeerTutor = { navController.navigate(Screen.PeerTutor.route) },
                onNavigateToSummarizer = { navController.navigate(Screen.Summarizer.route) },
                onNavigateToOer = { navController.navigate(Screen.OerCurator.route) },
                onNavigateToSubBuilder = { navController.navigate(Screen.SubBuilder.route) },
                onNavigateToMissingNotifier = { navController.navigate(Screen.MissingNotifier.route) },
                onNavigateToSnippet = { navController.navigate(Screen.Snippet.route) },
                onNavigateToGratitude = { navController.navigate(Screen.Gratitude.route) },
                onNavigateToIcebreaker = { navController.navigate(Screen.Icebreaker.route) },
                onNavigateToProlonger = { navController.navigate(Screen.Prolonger.route) },
                onNavigateToTechAi = { navController.navigate(Screen.TechAi.route) },
                onNavigateToHub = { navController.navigate(Screen.Hub.route) },
                studentPoints = studentPoints,
                studyStreak = studyStreak
              )
            }
            composable(Screen.RhymeFinder.route) {
              RhymeFinderScreen(viewModel = viewModel)
            }
            composable(Screen.PeerTutor.route) {
              PeerTutorScreen()
            }
            composable(Screen.Summarizer.route) {
              ArticleSummarizerScreen(viewModel = viewModel)
            }
            composable(Screen.OerCurator.route) {
              OerCuratorScreen(viewModel = viewModel)
            }
            composable(Screen.SubBuilder.route) {
              SubFolderBuilderScreen()
            }
            composable(Screen.MissingNotifier.route) {
              MissingAssignmentScreen(viewModel = viewModel)
            }
            composable(Screen.Snippet.route) {
              SnippetScreen(viewModel = viewModel)
            }
            composable(Screen.Gratitude.route) {
              GratitudeScreen(viewModel = viewModel)
            }
            composable(Screen.Icebreaker.route) {
              IcebreakerScreen(viewModel = viewModel)
            }
            composable(Screen.Prolonger.route) {
              ProlongerScreen(viewModel = viewModel)
            }
            composable(Screen.TechAi.route) {
              TechAiTutorScreen(viewModel = viewModel)
            }
            composable(Screen.Hub.route) {
              HubScreen(studentPoints = studentPoints, studyStreak = studyStreak)
            }
          }
        }
      }
    }
  }
}
