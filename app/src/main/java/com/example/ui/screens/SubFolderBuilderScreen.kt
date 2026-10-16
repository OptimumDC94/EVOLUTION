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
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.FolderSpecial
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SubFolderBuilderScreen() {
  var className by remember { mutableStateOf("SS 1A Science - Idanre Grammar School") }
  var teacherName by remember { mutableStateOf("Mr. Adebayo") }
  var generatedPacket by remember {
    mutableStateOf(
      """
      === EMERGENCY SUBSTITUTE TEACHER PACKET ===
      Class: SS 1A Science
      Teacher: Mr. Adebayo
      
      1. DAILY SCHEDULE:
      - 08:00 AM - 08:30 AM: Morning Assembly & Attendance
      - 08:30 AM - 09:30 AM: Mathematics (Algebra & Quadratics)
      - 09:30 AM - 10:30 AM: Basic Science & Biology
      - 10:30 AM - 11:00 AM: Morning Break / Recess
      - 11:00 AM - 12:00 PM: English Language & Comprehension
      - 12:00 PM - 01:00 PM: Computer Science / Coding Introduction
      
      2. SEATING CHART & LAYOUT:
      - Classroom has 6 rows of 5 desks facing the chalkboard.
      - Front row reserved for students needing visual/hearing support.
      - Class monitor: Chioma Okoro (Desk 3, Row 1).
      
      3. EMERGENCY & ATTENDANCE PROTOCOLS:
      - Take attendance promptly at 08:15 AM and submit sheet to the Principal's office.
      - In case of fire or emergency drill, lead students orderly to the school assembly ground via the main staircase.
      
      4. BACKUP ACTIVITIES (No-prep needed):
      - Activity 1 (Math): Solve 10 quadratic word problems on page 42 of the textbook.
      - Activity 2 (Writing): Write a 1-page essay titled 'My Vision for Technology in Idanre'.
      - Activity 3 (Science): Discuss the life cycle of cocoa plants and local ecosystem preservation.
      """.trimIndent()
    )
  }

  val context = LocalContext.current
  val scrollState = rememberScrollState()

  fun copyPacket() {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.setPrimaryClip(ClipData.newPlainText("Sub Packet", generatedPacket))
    Toast.makeText(context, "Emergency packet copied to clipboard!", Toast.LENGTH_SHORT).show()
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
        text = "Substitute Teacher Folder Builder 📁",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
      )
      Text(
        text = "Generates a complete one-page emergency packet including seating charts, schedules, and backup activities.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }

    OutlinedTextField(
      value = className,
      onValueChange = { className = it },
      modifier = Modifier
        .fillMaxWidth()
        .testTag("sub_class_input"),
      label = { Text("Class Name & School") },
      singleLine = true
    )

    OutlinedTextField(
      value = teacherName,
      onValueChange = { teacherName = it },
      modifier = Modifier
        .fillMaxWidth()
        .testTag("sub_teacher_input"),
      label = { Text("Teacher Name") },
      singleLine = true
    )

    Button(
      onClick = {
        generatedPacket = """
        === EMERGENCY SUBSTITUTE TEACHER PACKET ===
        Class: $className
        Teacher: $teacherName
        
        1. DAILY SCHEDULE:
        - 08:00 AM - 08:30 AM: Morning Assembly & Attendance
        - 08:30 AM - 09:30 AM: Core Subject Lesson & Exercises
        - 09:30 AM - 10:30 AM: Practice Worksheets & Group Discussion
        - 10:30 AM - 11:00 AM: Morning Break / Recess
        - 11:00 AM - 12:00 PM: Reading & Literacy Session
        - 12:00 PM - 01:00 PM: STEM / Computer Science Lab
        
        2. SEATING CHART & LAYOUT:
        - Standard 6x5 grid layout. Front desks assigned for focused learners.
        - Class Monitor is available at Desk 1, Row 1 to assist with materials.
        
        3. EMERGENCY PROTOCOLS:
        - Attendance report due in administration office by 09:00 AM.
        - Emergency assembly point: Central School Field.
        
        4. BACKUP ACTIVITIES:
        - Activity 1: Silent reading and summary writing (Chapters 3-4).
        - Activity 2: Collaborative math puzzle solving.
        - Activity 3: Open-ended creative journaling.
        """.trimIndent()
        Toast.makeText(context, "Packet updated successfully!", Toast.LENGTH_SHORT).show()
      },
      modifier = Modifier
        .fillMaxWidth()
        .testTag("generate_sub_packet_button"),
      colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
      Icon(Icons.Default.FolderSpecial, contentDescription = null)
      Spacer(modifier = Modifier.width(8.dp))
      Text("Generate Emergency Packet")
    }

    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("sub_packet_card"),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
      Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(Icons.Default.Assignment, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Text(text = "One-Page Emergency Packet", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
          }
          IconButton(onClick = { copyPacket() }) {
            Icon(Icons.Default.ContentCopy, contentDescription = "Copy packet")
          }
        }

        Text(text = generatedPacket, style = MaterialTheme.typography.bodyMedium)

        Button(
          onClick = { copyPacket() },
          modifier = Modifier.align(Alignment.End)
        ) {
          Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Copy Emergency Packet")
        }
      }
    }
  }
}
