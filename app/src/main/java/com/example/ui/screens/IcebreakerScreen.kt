package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cached
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.viewmodel.EvolutionViewModel

@Composable
fun IcebreakerScreen(viewModel: EvolutionViewModel) {
  val icebreaker by viewModel.currentIcebreaker.collectAsState()
  val isGeneratingIcebreaker by viewModel.isGeneratingIcebreaker.collectAsState()

  var selectedCategory by remember { mutableStateOf("Team Meetings") }
  val context = LocalContext.current
  val scrollState = rememberScrollState()

  val categories = listOf("Team Meetings", "Social Dinners", "First Dates", "Study Groups")

  fun copyText(text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.setPrimaryClip(ClipData.newPlainText("Icebreaker", text))
    Toast.makeText(context, "Icebreaker copied!", Toast.LENGTH_SHORT).show()
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .verticalScroll(scrollState)
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
      Text(
        text = "Icebreaker Generator 🧊",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
      )
      Text(
        text = "Generate thoughtful conversation starters for social dinners, dates, study groups, and team meetings.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }

    // Category Selector
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      categories.forEach { cat ->
        FilterChip(
          selected = selectedCategory == cat,
          onClick = { selectedCategory = cat },
          label = { Text(cat) },
          modifier = Modifier.testTag("chip_$cat")
        )
      }
    }

    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("icebreaker_card"),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
      Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          Icon(Icons.Default.QuestionAnswer, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
          Text(text = "Conversation Starter ($selectedCategory)", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
        }

        if (isGeneratingIcebreaker) {
          Box(modifier = Modifier.fillMaxWidth().height(100.dp), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
          }
        } else {
          Text(text = icebreaker, style = MaterialTheme.typography.bodyLarge)
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          OutlinedButton(
            onClick = { copyText(icebreaker) },
            modifier = Modifier.testTag("copy_icebreaker_button")
          ) {
            Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Copy")
          }

          Button(
            onClick = { viewModel.generateIcebreaker(selectedCategory) },
            modifier = Modifier.testTag("next_icebreaker_button"),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
          ) {
            Icon(Icons.Default.Cached, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Generate New")
          }
        }
      }
    }
  }
}
