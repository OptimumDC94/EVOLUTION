package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HubScreen(studentPoints: Int, studyStreak: Int) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
          text = "Idanre Student Hub 🏛️",
          style = MaterialTheme.typography.titleLarge,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = "Connecting students in Idanre, Ondo State to digital learning, literature, and culture.",
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }

    // Stats Card
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
          horizontalArrangement = Arrangement.SpaceAround,
          verticalAlignment = Alignment.CenterVertically
        ) {
          StatItem(icon = Icons.Default.Star, label = "Total Points", value = "$studentPoints")
          StatItem(icon = Icons.Default.LocalFireDepartment, label = "Day Streak", value = "$studyStreak")
          StatItem(icon = Icons.Default.EmojiEvents, label = "Rank", value = "Top 5%")
        }
      }
    }

    // Idanre Culture & Heritage Section
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(Icons.Default.Landscape, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Text(text = "Idanre Heritage & Poetry", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
          }
          Text(
            text = "Home of the majestic Idanre Hills (Oke Idanre) with 680 steps, ancient cultural heritage, and rich cocoa farms. EVOLUTION inspires our youth to write poetry, songs, and code for a brighter future!",
            style = MaterialTheme.typography.bodyMedium
          )
        }
      }
    }

    // Study & Writing Challenges
    item {
      Text(
        text = "Weekly Learning Challenges",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
      )
    }

    item {
      ChallengeItem(
        title = "Poetry Challenge: Hills of Idanre",
        reward = "+50 pts",
        completed = true,
        description = "Write a 3-stanza poem celebrating the ancient hills."
      )
    }

    item {
      ChallengeItem(
        title = "Coding Basics: What is Python?",
        reward = "+40 pts",
        completed = false,
        description = "Ask Tech AI 3 questions about programming."
      )
    }

    item {
      ChallengeItem(
        title = "Songwriting: Highlife Rhythm",
        reward = "+60 pts",
        completed = false,
        description = "Use the AI Songwriter tool to compose an uplifting track."
      )
    }
  }
}

@Composable
fun StatItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
    Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
    Text(text = value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
    Text(text = label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
  }
}

@Composable
fun ChallengeItem(title: String, reward: String, completed: Boolean, description: String) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.weight(1f)) {
        Text(text = title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
        Text(text = description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = reward, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
      }
      Checkbox(checked = completed, onCheckedChange = {})
    }
  }
}
