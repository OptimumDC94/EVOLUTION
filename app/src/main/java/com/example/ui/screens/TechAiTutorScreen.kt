package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.viewmodel.EvolutionViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TechAiTutorScreen(viewModel: EvolutionViewModel) {
  val chatMessages by viewModel.chatMessages.collectAsState()
  val isAiThinking by viewModel.isAiThinking.collectAsState()

  var inputMessage by remember { mutableStateOf("") }
  val listState = rememberLazyListState()
  val coroutineScope = rememberCoroutineScope()
  val keyboardController = LocalSoftwareKeyboardController.current

  LaunchedEffect(chatMessages.size) {
    if (chatMessages.isNotEmpty()) {
      listState.animateScrollToItem(chatMessages.size - 1)
    }
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Header
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
      Text(
        text = "Tech AI Tutor 🤖",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
      )
      Text(
        text = "Ask questions about coding, AI, algorithms, and computers in simple English. Your personal software tutor in Idanre!",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }

    // Chat Message List
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
    ) {
      LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize().testTag("chat_messages_list"),
        contentPadding = PaddingValues(vertical = 8.dp)
      ) {
        items(chatMessages) { msg ->
          ChatBubble(message = msg.text, isUser = msg.isUser)
        }
        if (isAiThinking) {
          item {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.Start
            ) {
              Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.medium
              ) {
                Row(
                  modifier = Modifier.padding(12.dp),
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                  CircularProgressIndicator(modifier = Modifier.size(16.dp))
                  Text(text = "Tech AI is thinking...", style = MaterialTheme.typography.bodyMedium)
                }
              }
            }
          }
        }
      }
    }

    // Input Bar
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      OutlinedTextField(
        value = inputMessage,
        onValueChange = { inputMessage = it },
        modifier = Modifier
          .weight(1f)
          .testTag("chat_input_field"),
        placeholder = { Text("Ask a coding question...") },
        maxLines = 3,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
        keyboardActions = KeyboardActions(
          onSend = {
            if (inputMessage.isNotBlank()) {
              viewModel.sendTutorMessage(inputMessage)
              inputMessage = ""
              keyboardController?.hide()
            }
          }
        ),
        shape = MaterialTheme.shapes.medium
      )

      FloatingActionButton(
        onClick = {
          if (inputMessage.isNotBlank()) {
            viewModel.sendTutorMessage(inputMessage)
            inputMessage = ""
            keyboardController?.hide()
          }
        },
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier.testTag("send_chat_button")
      ) {
        Icon(Icons.Default.Send, contentDescription = "Send message")
      }
    }
  }
}

@Composable
fun ChatBubble(message: String, isUser: Boolean) {
  val backgroundColor = if (isUser) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
  val textColor = if (isUser) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
  val alignment = if (isUser) Arrangement.End else Arrangement.Start

  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = alignment
  ) {
    Surface(
      color = backgroundColor,
      shape = MaterialTheme.shapes.medium,
      modifier = Modifier.widthIn(max = 300.dp)
    ) {
      Text(
        text = message,
        color = textColor,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(12.dp)
      )
    }
  }
}
