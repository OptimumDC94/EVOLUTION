package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class PeerTutor(
  val id: String = java.util.UUID.randomUUID().toString(),
  val name: String,
  val grade: String,
  val subject: String,
  val rating: Double,
  val availability: String,
  val email: String
)

@Composable
fun PeerTutorScreen() {
  var selectedSubject by remember { mutableStateOf("All") }
  val context = LocalContext.current

  val tutors = listOf(
    PeerTutor(name = "Tolu Adebayo", grade = "SS 3 Science", subject = "Mathematics & Physics", rating = 4.9, availability = "Mon & Wed After School (3 PM)", email = "tolu.tutor@example.com"),
    PeerTutor(name = "Aminat Bello", grade = "SS 3 Science", subject = "Biology & Chemistry", rating = 4.8, availability = "Tue & Thu (4 PM)", email = "aminat.tutor@example.com"),
    PeerTutor(name = "David Ojo", grade = "SS 2 Arts", subject = "Literature & English", rating = 4.7, availability = "Fridays (2 PM)", email = "david.tutor@example.com"),
    PeerTutor(name = "Blessing Olawale", grade = "SS 3 Science", subject = "Computer Science & Python", rating = 5.0, availability = "Saturdays (10 AM)", email = "blessing.tutor@example.com")
  )

  val filteredTutors = if (selectedSubject == "All") tutors else tutors.filter { it.subject.contains(selectedSubject, ignoreCase = true) }

  fun requestSession(tutor: PeerTutor) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
      data = Uri.parse("mailto:${tutor.email}")
      putExtra(Intent.EXTRA_SUBJECT, "Tutoring Session Request - ${tutor.subject}")
      putExtra(Intent.EXTRA_TEXT, "Hello ${tutor.name},\n\nI would like to request a peer tutoring session with you for ${tutor.subject} during your available time: ${tutor.availability}.\n\nThank you!")
    }
    try {
      context.startActivity(intent)
    } catch (e: Exception) {
      Toast.makeText(context, "Email client unavailable. Tutor contact: ${tutor.email}", Toast.LENGTH_LONG).show()
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
        text = "Peer Tutoring Matchmaker 🤝",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
      )
      Text(
        text = "Connects students seeking academic help with qualified peer tutors based on subject and availability.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }

    // Subject Filter Chips
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      listOf("All", "Math", "Science", "Computer").forEach { subj ->
        FilterChip(
          selected = selectedSubject == subj,
          onClick = { selectedSubject = subj },
          label = { Text(subj) },
          modifier = Modifier.testTag("chip_tutor_$subj")
        )
      }
    }

    LazyColumn(
      verticalArrangement = Arrangement.spacedBy(12.dp),
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
        .testTag("tutors_list")
    ) {
      items(filteredTutors) { tutor ->
        Card(
          modifier = Modifier.fillMaxWidth(),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Default.School, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Column {
                  Text(text = tutor.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                  Text(text = "${tutor.grade} • Rating: ⭐ ${tutor.rating}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
              }
              Badge { Text(tutor.subject) }
            }

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              Icon(Icons.Default.Schedule, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(16.dp))
              Text(text = "Available: ${tutor.availability}", style = MaterialTheme.typography.bodyMedium)
            }

            Button(
              onClick = { requestSession(tutor) },
              modifier = Modifier
                .align(Alignment.End)
                .testTag("request_session_${tutor.name.replace(" ", "_")}")
            ) {
              Icon(Icons.Default.People, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Request Tutoring Session")
            }
          }
        }
      }
    }
  }
}
