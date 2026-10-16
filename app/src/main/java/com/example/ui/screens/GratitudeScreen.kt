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
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Send
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
fun GratitudeScreen(viewModel: EvolutionViewModel) {
  val gratitudeMessage by viewModel.gratitudeMessage.collectAsState()
  val isGeneratingGratitude by viewModel.isGeneratingGratitude.collectAsState()

  var recipientName by remember { mutableStateOf("") }
  var occasion by remember { mutableStateOf("Colleague Appreciation") }

  val context = LocalContext.current
  val scrollState = rememberScrollState()

  val templates = listOf(
    "Thank you so much for your incredible support on our project. Your dedication truly inspires everyone around you!",
    "I just wanted to send a quick note to let you know how much I appreciate your help and positive energy today.",
    "Your guidance and mentorship mean the world to me. Thank you for always going above and beyond!",
    "Working alongside someone as talented and kind as you makes every challenge feel easy. Thank you!"
  )

  fun copyText(text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.setPrimaryClip(ClipData.newPlainText("Gratitude Note", text))
    Toast.makeText(context, "Note copied to clipboard!", Toast.LENGTH_SHORT).show()
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
        text = "Gratitude Note Sender 💌",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
      )
      Text(
        text = "Quick templates and AI-crafted appreciation messages for colleagues, teachers, and friends.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }

    OutlinedTextField(
      value = recipientName,
      onValueChange = { recipientName = it },
      modifier = Modifier
        .fillMaxWidth()
        .testTag("recipient_input"),
      placeholder = { Text("Recipient Name (e.g. Tunde, Sarah)...") },
      singleLine = true
    )

    Button(
      onClick = { viewModel.generateGratitudeNote(recipientName, occasion) },
      modifier = Modifier
        .fillMaxWidth()
        .testTag("generate_gratitude_button"),
      enabled = !isGeneratingGratitude
    ) {
      if (isGeneratingGratitude) {
        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = MaterialTheme.colorScheme.onPrimary)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Crafting AI Gratitude Note...")
      } else {
        Icon(Icons.Default.Favorite, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Generate Custom AI Note")
      }
    }

    if (gratitudeMessage.isNotBlank()) {
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Text(text = "Your Note:", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
          Text(text = gratitudeMessage, style = MaterialTheme.typography.bodyLarge)
          Button(
            onClick = { copyText(gratitudeMessage) },
            modifier = Modifier.align(Alignment.End)
          ) {
            Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Copy Note")
          }
        }
      }
    }

    Text(
      text = "Quick Templates",
      style = MaterialTheme.typography.titleMedium,
      fontWeight = FontWeight.Bold
    )

    templates.forEach { template ->
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Text(text = template, style = MaterialTheme.typography.bodyMedium)
          OutlinedButton(
            onClick = { copyText(template) },
            modifier = Modifier.align(Alignment.End)
          ) {
            Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Use Template")
          }
        }
      }
    }
  }
}
