package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.viewmodel.EvolutionViewModel
import com.example.viewmodel.SearchTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RhymeFinderScreen(viewModel: EvolutionViewModel) {
  val searchQuery by viewModel.searchQuery.collectAsState()
  val selectedTab by viewModel.selectedTab.collectAsState()
  val searchResults by viewModel.searchResults.collectAsState()
  val aiSongwritingResult by viewModel.aiSongwritingResult.collectAsState()
  val isLoading by viewModel.isLoading.collectAsState()
  val errorMessage by viewModel.errorMessage.collectAsState()

  val context = LocalContext.current
  val keyboardController = LocalSoftwareKeyboardController.current

  fun copyToClipboard(text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Copied Word", text)
    clipboard.setPrimaryClip(clip)
    Toast.makeText(context, "Copied: $text", Toast.LENGTH_SHORT).show()
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Title & Description
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
      Text(
        text = "Rhyme & Synonym Studio ✍️",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
      )
      Text(
        text = "Instant rhymes, synonyms, and AI lyrics for writers, poets, and copywriters in Idanre & beyond.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }

    // Single Search Field
    OutlinedTextField(
      value = searchQuery,
      onValueChange = { viewModel.updateSearchQuery(it) },
      modifier = Modifier
        .fillMaxWidth()
        .testTag("search_word_input"),
      placeholder = { Text("Enter a word (e.g. fire, hill, gold)...") },
      singleLine = true,
      leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
      trailingIcon = {
        if (searchQuery.isNotEmpty()) {
          IconButton(onClick = { viewModel.performSearch(searchQuery) }, modifier = Modifier.testTag("search_submit_button")) {
            Icon(Icons.Default.ArrowForward, contentDescription = "Search")
          }
        }
      },
      keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
      keyboardActions = KeyboardActions(
        onSearch = {
          keyboardController?.hide()
          viewModel.performSearch(searchQuery)
        }
      ),
      shape = MaterialTheme.shapes.medium
    )

    // Filter Tabs
    ScrollableTabRow(
      selectedTabIndex = selectedTab.ordinal,
      edgePadding = 0.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      Tab(
        selected = selectedTab == SearchTab.RHYMES,
        onClick = { viewModel.selectTab(SearchTab.RHYMES) },
        text = { Text("Rhymes") },
        modifier = TabTagModifier("tab_rhymes")
      )
      Tab(
        selected = selectedTab == SearchTab.NEAR_RHYMES,
        onClick = { viewModel.selectTab(SearchTab.NEAR_RHYMES) },
        text = { Text("Near Rhymes") },
        modifier = TabTagModifier("tab_near_rhymes")
      )
      Tab(
        selected = selectedTab == SearchTab.SYNONYMS,
        onClick = { viewModel.selectTab(SearchTab.SYNONYMS) },
        text = { Text("Synonyms") },
        modifier = TabTagModifier("tab_synonyms")
      )
      Tab(
        selected = selectedTab == SearchTab.ANTONYMS,
        onClick = { viewModel.selectTab(SearchTab.ANTONYMS) },
        text = { Text("Antonyms") },
        modifier = TabTagModifier("tab_antonyms")
      )
      Tab(
        selected = selectedTab == SearchTab.AI_SONGWRITER,
        onClick = { viewModel.selectTab(SearchTab.AI_SONGWRITER) },
        text = { Text("AI Songwriter") },
        modifier = TabTagModifier("tab_ai_songwriter")
      )
    }

    // Error Message
    errorMessage?.let { err ->
      Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(text = err, color = MaterialTheme.colorScheme.onErrorContainer, modifier = Modifier.padding(12.dp))
      }
    }

    // Content Area
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
    ) {
      if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center).testTag("loading_spinner"))
      } else if (selectedTab == SearchTab.AI_SONGWRITER) {
        // AI Songwriter View
        Card(
          modifier = Modifier.fillMaxWidth(),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
          Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
              Text(text = "AI Songwriter & Copywriting Ideas", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            }
            if (aiSongwritingResult.isBlank()) {
              Text(
                text = "Type a word above and select 'AI Songwriter' to generate professional lyrics and hooks!",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            } else {
              Text(text = aiSongwritingResult, style = MaterialTheme.typography.bodyLarge)
              Button(
                onClick = { copyToClipboard(aiSongwritingResult) },
                modifier = Modifier.align(Alignment.End)
              ) {
                Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Copy Lyrics")
              }
            }
          }
        }
      } else {
        // Datamuse Word List
        if (searchResults.isEmpty()) {
          Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
              text = if (searchQuery.isBlank()) "Type a word above to search instantly." else "No results found. Try another word.",
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        } else {
          LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize().testTag("results_list")
          ) {
            items(searchResults) { item ->
              Card(
                modifier = Modifier
                  .fillMaxWidth()
                  .clickable { copyToClipboard(item.word) },
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
              ) {
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                      text = item.word,
                      style = MaterialTheme.typography.titleMedium,
                      fontWeight = FontWeight.Bold,
                      color = MaterialTheme.colorScheme.primary
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                      item.numSyllables?.let { syllables ->
                        Text(
                          text = "$syllables syllables",
                          style = MaterialTheme.typography.bodySmall,
                          color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                      }
                      Text(
                        text = "Score: ${item.score}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                      )
                    }
                  }
                  IconButton(onClick = { copyToClipboard(item.word) }) {
                    Icon(Icons.Default.ContentCopy, contentDescription = "Copy word", tint = MaterialTheme.colorScheme.secondary)
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

@Composable
fun TabTagModifier(tag: String): Modifier {
  return Modifier.testTag(tag)
}
