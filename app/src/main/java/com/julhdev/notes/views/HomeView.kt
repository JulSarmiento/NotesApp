package com.julhdev.notes.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeView() {
  Scaffold { innerPadding ->
    Text(
      modifier = Modifier.padding(innerPadding),
      text = "home View")
  }
}