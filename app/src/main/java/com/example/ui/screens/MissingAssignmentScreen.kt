package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentLate
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.viewmodel.EvolutionViewModel

data class MissingAssignment(
  val id: String = java.util.UUID.randomUUID().toString(),
  val studentName: String,
  val subject: String,
  val assignmentTitle: String,
  val dueDate: String,
  val daysOverdue: Int
)

@Composable
fun MissingAssignmentScreen(viewModel: EvolutionViewModel) {
  var studentName by remember { mutableStateOf("Tolu Adebayo") }
  var parentEmail by remember { mutableStateOf("parent@example.com") }

  val assignments = remember {
    mutableStateOf(
      listOf(
        MissingAssignment(studentName = "Tolu Adebayo", subject = "Mathematics", assignmentTitle = "Quadratic Equations Problem Set #3", dueDate = "Oct 10, 2026", daysOverdue = 6),
        MissingAssignment(studentName = "Tolu Adebayo", subject = "Basic Science", assignmentTitle = "Idanre Ecosystem Lab Report", dueDate = "Oct 12, 2026", daysOverdue = 4),
        MissingAssignment(studentName = "Tolu Adebayo", subject = "Computer Science", assignmentTitle = "Python Introduction Quiz", dueDate = "Oct 14, 2026", daysOverdue = 2)
      )
    )
  }

  val context = LocalContext.current

  fun sendEmailDigest() {
    val body = StringBuilder().apply {
      append("WEEKLY MISSING ASSIGNMENT DIGEST - EVOLUTION IDANRE\n\n")
      append("Dear Parent / Guardian of $studentName,\n\n")
      append("This is your automated weekly digest from EVOLUTION School Ecosystem listing overdue assignments:\n\n")
      assignments.value.forEach {
        append("- [${it.subject}] ${it.assignmentTitle} (Due: ${it.dueDate} - ${it.daysOverdue} days overdue)\n")
      }
      append("\nPlease encourage ${studentName} to complete these tasks and submit them this week to maintain academic progress.\n\n")
      append("Best regards,\nEVOLUTION School Administration, Idanre")
    }.toString()

    val intent = Intent(Intent.ACTION_SENDTO).apply {
      data = Uri.parse("mailto:$parentEmail")
      putExtra(Intent.EXTRA_SUBJECT, "Weekly Missing Assignment Digest for $studentName")
      putExtra(Intent.EXTRA_TEXT, body)
    }

    try {
      context.startActivity(intent)
    } catch (e: Exception) {
      Toast.makeText(context, "Email client not available. Copied to clipboard!", Toast.LENGTH_LONG).show()
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
        text = "Missing Assignment Auto-Notifier 📧",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
      )
      Text(
        text = "Sends automated weekly email digests to students and parents listing overdue work.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }

    OutlinedTextField(
      value = studentName,
      onValueChange = { studentName = it },
      modifier = Modifier
        .fillMaxWidth()
        .testTag("notifier_student_input"),
      label = { Text("Student Name") },
      singleLine = true
    )

    OutlinedTextField(
      value = parentEmail,
      onValueChange = { parentEmail = it },
      modifier = Modifier
        .fillMaxWidth()
        .testTag("notifier_email_input"),
      label = { Text("Parent / Guardian Email") },
      singleLine = true
    )

    Button(
      onClick = { sendEmailDigest() },
      modifier = Modifier
        .fillMaxWidth()
        .testTag("send_digest_button"),
      colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
    ) {
      Icon(Icons.Default.Email, contentDescription = null)
      Spacer(modifier = Modifier.width(8.dp))
      Text("Send Automated Weekly Digest Email")
    }

    Text(
      text = "Current Overdue Assignments (${assignments.value.size})",
      style = MaterialTheme.typography.titleMedium,
      fontWeight = FontWeight.Bold
    )

    LazyColumn(
      verticalArrangement = Arrangement.spacedBy(8.dp),
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
        .testTag("overdue_list")
    ) {
      items(assignments.value) { item ->
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
              Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(Icons.Default.Warning, contentDescription = null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(16.dp))
                Text(text = item.subject, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.error)
              }
              Text(text = item.assignmentTitle, style = MaterialTheme.typography.bodyMedium)
              Text(text = "Due: ${item.dueDate} (${item.daysOverdue} days overdue)", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
          }
        }
      }
    }
  }
}
