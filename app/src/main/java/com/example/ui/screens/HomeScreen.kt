package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
  onNavigateToRhymeFinder: () -> Unit,
  onNavigateToPeerTutor: () -> Unit,
  onNavigateToSummarizer: () -> Unit,
  onNavigateToOer: () -> Unit,
  onNavigateToSubBuilder: () -> Unit,
  onNavigateToMissingNotifier: () -> Unit,
  onNavigateToSnippet: () -> Unit,
  onNavigateToGratitude: () -> Unit,
  onNavigateToIcebreaker: () -> Unit,
  onNavigateToProlonger: () -> Unit,
  onNavigateToTechAi: () -> Unit,
  onNavigateToHub: () -> Unit,
  studentPoints: Int,
  studyStreak: Int
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Header Banner
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.primaryContainer,
          contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
      ) {
        Column(
          modifier = Modifier.padding(20.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "EVOLUTION 🇳🇬",
              style = MaterialTheme.typography.titleLarge,
              fontWeight = FontWeight.Bold
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
              BadgeItem(icon = Icons.Default.LocalFireDepartment, label = "$studyStreak Days")
              BadgeItem(icon = Icons.Default.Star, label = "$studentPoints pts")
            }
          }
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Welcome to Idanre's Digital Ecosystem & School Hub!",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
          )
          Text(
            text = "Access peer tutors, missing assignment digests, OER textbooks, substitute packets, rhymes, and AI coding tutorials.",
            style = MaterialTheme.typography.bodyMedium
          )
        }
      }
    }

    // Quick Action Cards Grid
    item {
      Text(
        text = "All Tools & Features",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 4.dp)
      )
    }

    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        FeatureCard(
          title = "Peer Tutors",
          description = "Connect with qualified student peer tutors.",
          icon = Icons.Default.People,
          onClick = onNavigateToPeerTutor,
          modifier = Modifier.weight(1f),
          testTag = "card_peer_tutor"
        )
        FeatureCard(
          title = "Missing Notifier",
          description = "Automated weekly email digests for overdue work.",
          icon = Icons.Default.NotificationImportant,
          onClick = onNavigateToMissingNotifier,
          modifier = Modifier.weight(1f),
          testTag = "card_missing_notifier"
        )
      }
    }

    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        FeatureCard(
          title = "OER Curator",
          description = "Find open-access textbooks & worksheets.",
          icon = Icons.Default.MenuBook,
          onClick = onNavigateToOer,
          modifier = Modifier.weight(1f),
          testTag = "card_oer"
        )
        FeatureCard(
          title = "Sub Folder",
          description = "Emergency sub packets & seating charts.",
          icon = Icons.Default.FolderSpecial,
          onClick = onNavigateToSubBuilder,
          modifier = Modifier.weight(1f),
          testTag = "card_sub_builder"
        )
      }
    }

    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        FeatureCard(
          title = "Rhyme Finder",
          description = "Instant rhymes & synonyms for writers.",
          icon = Icons.Default.Search,
          onClick = onNavigateToRhymeFinder,
          modifier = Modifier.weight(1f),
          testTag = "card_rhyme_finder"
        )
        FeatureCard(
          title = "Article Summary",
          description = "Distill text into 3 key takeaways.",
          icon = Icons.Default.Description,
          onClick = onNavigateToSummarizer,
          modifier = Modifier.weight(1f),
          testTag = "card_summarizer"
        )
      }
    }

    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        FeatureCard(
          title = "Code Snippet",
          description = "Daily programming tricks & commands.",
          icon = Icons.Default.Code,
          onClick = onNavigateToSnippet,
          modifier = Modifier.weight(1f),
          testTag = "card_snippet"
        )
        FeatureCard(
          title = "Gratitude Notes",
          description = "Quick appreciation messages.",
          icon = Icons.Default.Favorite,
          onClick = onNavigateToGratitude,
          modifier = Modifier.weight(1f),
          testTag = "card_gratitude"
        )
      }
    }

    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        FeatureCard(
          title = "Icebreakers",
          description = "Conversation starters for dinners & dates.",
          icon = Icons.Default.QuestionAnswer,
          onClick = onNavigateToIcebreaker,
          modifier = Modifier.weight(1f),
          testTag = "card_icebreaker"
        )
        FeatureCard(
          title = "Prolonger",
          description = "Endless concept & question marathon.",
          icon = Icons.Default.Shuffle,
          onClick = onNavigateToProlonger,
          modifier = Modifier.weight(1f),
          testTag = "card_prolonger"
        )
      }
    }

    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        FeatureCard(
          title = "Tech AI Tutor",
          description = "Learn coding & AI in simple English.",
          icon = Icons.Default.SmartToy,
          onClick = onNavigateToTechAi,
          modifier = Modifier.weight(1f),
          testTag = "card_tech_ai"
        )
        FeatureCard(
          title = "Student Hub",
          description = "Study notes & Idanre community.",
          icon = Icons.Default.School,
          onClick = onNavigateToHub,
          modifier = Modifier.weight(1f),
          testTag = "card_student_hub"
        )
      }
    }
  }
}

@Composable
fun BadgeItem(icon: ImageVector, label: String) {
  Surface(
    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
    shape = MaterialTheme.shapes.small
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.primary)
      Text(text = label, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
    }
  }
}

@Composable
fun FeatureCard(
  title: String,
  description: String,
  icon: ImageVector,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  testTag: String
) {
  Card(
    modifier = modifier
      .height(150.dp)
      .clickable(onClick = onClick)
      .testTag(testTag),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(14.dp),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(28.dp)
      )
      Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(text = title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
        Text(text = description, style = MaterialTheme.typography.bodySmall, maxLines = 2)
      }
    }
  }
}
