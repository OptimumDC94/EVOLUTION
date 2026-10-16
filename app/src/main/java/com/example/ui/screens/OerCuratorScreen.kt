package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.viewmodel.EvolutionViewModel

data class OerResource(
  val title: String,
  val topic: String,
  val readingLevel: String,
  val type: String,
  val url: String,
  val description: String
)

@Composable
fun OerCuratorScreen(viewModel: EvolutionViewModel) {
  var searchQuery by remember { mutableStateOf("") }
  var selectedLevel by remember { mutableStateOf("All") }
  var selectedType by remember { mutableStateOf("All") }

  val context = LocalContext.current

  val resources = listOf(
    OerResource("OpenStax Biology 2e", "Biology", "Senior Secondary", "Textbook", "https://openstax.org/details/books/biology-2e", "Comprehensive peer-reviewed biology textbook with diagrams and review questions."),
    OerResource("CK-12 Algebra & Geometry", "Mathematics", "Junior Secondary", "Textbook & Worksheets", "https://www.ck12.org/", "Interactive math lessons, practice quizzes, and graded worksheets."),
    OerResource("Python for Everybody", "Computer Science", "Senior Secondary", "Interactive Course", "https://www.py4e.com/", "Learn programming from scratch with video lectures and exercises tailored for beginners."),
    OerResource("Ondo State Agricultural Science", "Agriculture", "Junior Secondary", "Worksheet", "https://openlibrary.org/", "Practical guide to crop farming, livestock care, and sustainable farming in Ondo State."),
    OerResource("World Literature Classics", "Literature", "Senior Secondary", "Reading Material", "https://www.gutenberg.org/", "Collection of timeless plays, poems, and short stories for developing reading comprehension.")
  )

  val filteredResources = resources.filter { res ->
    val matchesQuery = res.title.contains(searchQuery, ignoreCase = true) || res.topic.contains(searchQuery, ignoreCase = true) || res.description.contains(searchQuery, ignoreCase = true)
    val matchesLevel = selectedLevel == "All" || res.readingLevel == selectedLevel
    val matchesType = selectedType == "All" || res.type.contains(selectedType, ignoreCase = true)
    matchesQuery && matchesLevel && matchesType
  }

  fun openUrl(url: String) {
    try {
      val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
      context.startActivity(intent)
    } catch (e: Exception) {
      Toast.makeText(context, "Could not open link", Toast.LENGTH_SHORT).show()
    }
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
      Text(
        text = "OER Curator 📚",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
      )
      Text(
        text = "Search and filter open-access textbooks, worksheets, and media by topic and reading level.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }

    OutlinedTextField(
      value = searchQuery,
      onValueChange = { searchQuery = it },
      modifier = Modifier
        .fillMaxWidth()
        .testTag("oer_search_input"),
      placeholder = { Text("Search topic or title (e.g. Math, Python, Biology)...") },
      leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
      singleLine = true,
      shape = MaterialTheme.shapes.medium
    )

    // Filter Chips for Level
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      listOf("All", "Junior Secondary", "Senior Secondary").forEach { level ->
        FilterChip(
          selected = selectedLevel == level,
          onClick = { selectedLevel = level },
          label = { Text(level) },
          modifier = Modifier.testTag("chip_level_$level")
        )
      }
    }

    // Results List
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
    ) {
      if (filteredResources.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
          Text(text = "No open educational resources found matching your search.", style = MaterialTheme.typography.bodyMedium)
        }
      } else {
        LazyColumn(
          verticalArrangement = Arrangement.spacedBy(12.dp),
          modifier = Modifier.fillMaxSize().testTag("oer_list")
        ) {
          items(filteredResources) { res ->
            Card(
              modifier = Modifier
                .fillMaxWidth()
                .clickable { openUrl(res.url) },
              colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
              Column(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.Book, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Text(text = res.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                  }
                  Badge { Text(res.topic) }
                }

                Text(text = res.description, style = MaterialTheme.typography.bodyMedium)

                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(
                    text = "Level: ${res.readingLevel} • Type: ${res.type}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                  )
                  TextButton(onClick = { openUrl(res.url) }) {
                    Icon(Icons.Default.OpenInBrowser, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Access Resource")
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
