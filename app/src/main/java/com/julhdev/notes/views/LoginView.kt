package com.julhdev.notes.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.julhdev.notes.components.TopBar
import com.julhdev.notes.viewmodel.ThemeViewModel

@Composable
fun LoginView(
  navController: NavController,
  themeViewModel: ThemeViewModel
) {
  Scaffold(
    topBar = {
      TopBar(
        navController,
        themeViewModel
      )
    }
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .padding(innerPadding)
    ) {
      Text(
        text = "Login View"
      )
    }
  }
}