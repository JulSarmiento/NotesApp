package com.julhdev.notes.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.julhdev.notes.components.IconButton
import com.julhdev.notes.components.MainTextArea
import com.julhdev.notes.components.MainTextField
import com.julhdev.notes.components.MainTitle
import com.julhdev.notes.components.SwitchButton
import com.julhdev.notes.viewmodel.NoteViewModel
import com.julhdev.notes.viewmodel.ThemeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddView(navController: NavController, noteViewModel: NoteViewModel, themeViewModel: ThemeViewModel) {
  var theme = themeViewModel.isDark.collectAsState().value

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
    }
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .padding(innerPadding)
    ) {
      AddViewContent(noteViewModel)
    }
  }
}


@Composable
fun AddViewContent(noteViewModel: NoteViewModel) {
  Column(
    modifier = Modifier
      .padding(16.dp)
  ) {
    Text(
      text = "Crea una nueva nota aquí",
    )
    Spacer(
      modifier = Modifier
        .padding(8.dp)
    )

    MainTextField(
      value = "alguito",
      label = "Titulo",
      onValueChange = { /* Lógica para manejar el cambio de texto */ }
    )

    MainTextArea(
      value = "Contenido de la nota...",
      label = "Nota",
      onValueChange = { /* Lógica para manejar el cambio de texto */ }
    )
  }
}