package com.julhdev.notes.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.julhdev.notes.R
import com.julhdev.notes.components.FloatingButton
import com.julhdev.notes.components.MainTitle
import com.julhdev.notes.components.PngImage
import com.julhdev.notes.components.SubTitle
import com.julhdev.notes.components.SwitchButton
import com.julhdev.notes.navigation.Routes
import com.julhdev.notes.viewmodel.NoteViewModel
import com.julhdev.notes.viewmodel.ThemeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * HomeView Composable
 * @return componente que representa la vista principal de la aplicación de notas.
 * @usage HomeView()
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController, noteViewModel: NoteViewModel, themeViewModel: ThemeViewModel) {

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

    floatingActionButton = {
      FloatingButton(
        onClick = {
          navController.navigate(Routes.ADD)
        }
      )
    }
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .padding(innerPadding)
    ) {
      HomeViewContent(noteViewModel)
    }
  }
}


@Composable
fun HomeViewContent(noteViewModel: NoteViewModel) {
  val notes by noteViewModel.notes.collectAsState()

  Column(
    modifier = Modifier
      .padding(16.dp)
      .fillMaxSize()
  ) {
    Spacer(
      modifier = Modifier
        .height(20.dp)
    )
    MainTitle(
      text = "Tus Notas",
      color = MaterialTheme.colorScheme.onBackground,
    )

    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
      modifier = Modifier
        .fillMaxSize()
    ) {
      if (notes.isEmpty()) {
        HomeEmptyContent()
      } else {
        Box {
          Text(text = "Aquí se mostrarán las notas guardadas.")
        }
      }
    }
  }
}

/**
 * HomeEmptyContent Composable
 * @return un componente que muestra un mensaje y una imagen cuando no hay notas disponibles.
 * @usage HomeEmptyContent()
 */
@Composable
fun HomeEmptyContent() {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    modifier = Modifier
      .alpha(0.7f)
      .fillMaxSize()
  ) {
    PngImage(
      image = R.drawable.empty_note,
      description = "No Notes Image",
    )
    SubTitle(
      text = "Aún no hay notas disponibles, pero no te preocupes: ¡puedes agregar una nueva!",
      textAlign = TextAlign.Center
    )
  }
}