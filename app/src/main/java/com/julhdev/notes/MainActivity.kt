package com.julhdev.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.julhdev.notes.navigation.NavManager
import com.julhdev.notes.ui.theme.NotesTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      NotesTheme {
        NavManager()
      }
    }
  }
}

