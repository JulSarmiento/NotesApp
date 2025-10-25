package com.julhdev.notes.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.julhdev.notes.components.FloatingButton
import com.julhdev.notes.components.IconButton
import com.julhdev.notes.components.MainTitle
import com.julhdev.notes.components.SwitchButton
import com.julhdev.notes.navigation.Routes
import com.julhdev.notes.viewmodel.NoteViewModel
import com.julhdev.notes.viewmodel.ThemeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditView(
  noteId: Int,
  navController: NavController,
  noteViewModel: NoteViewModel,
  themeViewModel: ThemeViewModel,
) {

  val theme = themeViewModel.isDark.collectAsState().value

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          MainTitle(
            text = "Dashi's Notes",
            color = MaterialTheme.colorScheme.onPrimary
          )
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.primary,
        ),
        navigationIcon = {
          IconButton(
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            description = "Back",
            onClick = {
              navController.popBackStack()
            }
          )
        },
        actions = {
          SwitchButton(
            isDark = theme,
            onToggle = {
              CoroutineScope(Dispatchers.Main).launch {
                themeViewModel.saveIsDark(!theme)
              }
            }
          )
        }
      )
    },
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .padding(innerPadding)
    ) {
      Text(
        text = "Edit View - Note ID: $noteId",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
          .padding(16.dp)
      )
    }
  }
}