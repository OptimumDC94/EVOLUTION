package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Loop
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.viewmodel.EvolutionViewModel

@Composable
fun ProlongerScreen(viewModel: EvolutionViewModel) {
  val currentTopic by viewModel.prolongerTopic.collectAsState()
  val prolongerOutput by viewModel.prolongerOutput.collectAsState()
  val isProlonging by viewModel.isProlonging.collectAsState()

  var inputTopic by remember { mutableStateOf("") }
  val scrollState = rememberScrollState()
  val keyboardController = LocalSoftwareKeyboardController.current

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
        text = "Conversation Prolonger 🌀",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
      )
      Text(
        text = "Never let conversation get boring! Swirl effortlessly from concept to concept in an unending marathon of engaging ideas.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }

    OutlinedTextField(
      value = inputTopic,
      onValueChange = { inputTopic = it },
      modifier = Modifier
        .fillMaxWidth()
        .testTag("prolonger_input"),
      placeholder = { Text("Enter current topic (e.g. Artificial Intelligence, Music, Space)...") },
      singleLine = true
    )

    Button(
      onClick = {
        keyboardController?.hide()
        viewModel.swirlConversation(inputTopic.ifBlank { currentTopic })
      },
      modifier = Modifier
        .fillMaxWidth()
        .testTag("swirl_button"),
      enabled = !isProlonging,
      colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
    ) {
      if (isProlonging) {
        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = MaterialTheme.colorScheme.onTertiary)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Swirling to next fascinating concept...")
      } else {
        Icon(Icons.Default.Shuffle, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Swirl to Next Concept & Question")
      }
    }

    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("prolonger_card"),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
      Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          Icon(Icons.Default.Loop, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary)
          Text(text = "Current Focus: $currentTopic", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
        }

        Text(text = prolongerOutput, style = MaterialTheme.typography.bodyLarge)

        Button(
          onClick = { viewModel.swirlConversation(currentTopic) },
          modifier = Modifier
            .align(Alignment.End)
            .testTag("next_swirl_button"),
          colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
        ) {
          Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Keep Swirling!")
        }
      }
    }
  }
}
