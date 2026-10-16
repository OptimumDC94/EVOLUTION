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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Description
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
fun ArticleSummarizerScreen(viewModel: EvolutionViewModel) {
  val articleInput by viewModel.articleInput.collectAsState()
  val summaryResult by viewModel.summaryResult.collectAsState()
  val isSummarizing by viewModel.isSummarizing.collectAsState()

  val context = LocalContext.current
  val scrollState = rememberScrollState()

  fun copyToClipboard(text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.setPrimaryClip(ClipData.newPlainText("Summary", text))
    Toast.makeText(context, "Summary copied to clipboard!", Toast.LENGTH_SHORT).show()
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
        text = "Article Summarizer 📰",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
      )
      Text(
        text = "Distills long web article text into three powerful bullet-point key takeaways instantly using Gemini AI.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }

    OutlinedTextField(
      value = articleInput,
      onValueChange = { viewModel.updateArticleInput(it) },
      modifier = Modifier
        .fillMaxWidth()
        .height(180.dp)
        .testTag("article_input_field"),
      placeholder = { Text("Paste long article text, news, or study notes here...") },
      shape = MaterialTheme.shapes.medium
    )

    Button(
      onClick = { viewModel.summarizeArticle(articleInput) },
      modifier = Modifier
        .fillMaxWidth()
        .testTag("summarize_button"),
      enabled = !isSummarizing && articleInput.isNotBlank(),
      colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
      if (isSummarizing) {
        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = MaterialTheme.colorScheme.onPrimary)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Summarizing into 3 Key Takeaways...")
      } else {
        Icon(Icons.Default.AutoAwesome, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Summarize Article")
      }
    }

    if (summaryResult.isNotBlank()) {
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              Icon(Icons.Default.Description, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
              Text(text = "Key Takeaways", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            }
            IconButton(onClick = { copyToClipboard(summaryResult) }) {
              Icon(Icons.Default.ContentCopy, contentDescription = "Copy summary")
            }
          }
          Text(text = summaryResult, style = MaterialTheme.typography.bodyLarge)
        }
      }
    }
  }
}
