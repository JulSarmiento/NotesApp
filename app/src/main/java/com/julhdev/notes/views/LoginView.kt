package com.julhdev.notes.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.julhdev.notes.components.MainTextField
import com.julhdev.notes.components.TopBar
import com.julhdev.notes.viewmodel.ThemeViewModel

@Composable
fun LoginView(
  navController: NavController,
  themeViewModel: ThemeViewModel
) {

  val focus1 = remember { FocusRequester() }
  val focus2 = remember { FocusRequester() }

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
        .padding(innerPadding),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
    ) {
      Box(
        modifier = Modifier
          .background(
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = MaterialTheme.shapes.medium
          )
          .padding(15.dp)
      ){
      }
    }
  }
}