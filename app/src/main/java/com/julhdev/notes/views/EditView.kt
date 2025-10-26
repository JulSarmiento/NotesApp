package com.julhdev.notes.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.julhdev.notes.components.IconButton
import com.julhdev.notes.components.MainBtn
import com.julhdev.notes.components.MainDialog
import com.julhdev.notes.components.MainTextArea
import com.julhdev.notes.components.MainTextField
import com.julhdev.notes.components.MainTitle
import com.julhdev.notes.components.SubTitle
import com.julhdev.notes.components.SwitchButton
import com.julhdev.notes.viewmodel.FormEvent
import com.julhdev.notes.viewmodel.FormViewModel
import com.julhdev.notes.viewmodel.ThemeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Vista de edición de notas.
 * Muestra un formulario para editar una nota existente.
 * @param noteId El ID de la nota a editar.
 * @param navController El NavController para la navegación entre vistas.
 * @param formViewModel El ViewModel que maneja el estado del formulario.
 * @param themeViewModel El ViewModel que maneja el tema de la aplicación.
 * @usage Incluir EditView en la navegación para permitir la edición de notas existentes.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditView(
  noteId: Int,
  navController: NavController,
  formViewModel: FormViewModel,
  themeViewModel: ThemeViewModel,
) {

  var showDialog by remember { mutableStateOf(false) }
  val snackBarHostState = remember { SnackbarHostState() }
  val theme = themeViewModel.isDark.collectAsState().value

  LaunchedEffect(Unit) {
    formViewModel.events.collect { event ->
      when (event) {
        is FormEvent.SubmitSuccess -> {
          showDialog = true
        }
        is FormEvent.ShowMessage -> {
          snackBarHostState.showSnackbar(event.msg)
        }
      }
    }
  }

  Scaffold(
    snackbarHost = {
      SnackbarHost(snackBarHostState)
    },
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

      EditViewContent(noteId, formViewModel)

      if (showDialog) {
        MainDialog(
          title = "Nota Actualizada",
          content = "La nota se ha actualizado correctamente.",
          onDismiss = { showDialog = false },
          onConfirm = {
            showDialog = false
            navController.popBackStack()
          },
        )
      }
    }
  }
}


/**
 * Contenido de la vista de edición de notas.
 * Muestra el formulario para editar una nota existente.
 * @param noteId El ID de la nota a editar.
 * @param formViewModel El ViewModel que maneja el estado del formulario.
 * @usage Incluir EditViewContent en la vista de edición para mostrar el formulario de edición de notas.
 */
@Composable
fun EditViewContent(
  noteId: Int,
  formViewModel: FormViewModel,
) {
  val state by formViewModel.uiState.collectAsState()

  LaunchedEffect(
    Unit
  ) {
    formViewModel.loadNote(noteId)
  }

  SubTitle(
    text = "Editar la nota: $noteId",
    modifier = Modifier
      .padding(16.dp)
  )
  Spacer(
    modifier = Modifier
      .padding(8.dp)
  )
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .padding(16.dp)
  ) {
    MainTextField(
      value = state.title,
      label = "Titulo",
      onValueChange = { formViewModel.onTitleChange(it) },
      isError = state.title.length > 80 || state.titleError?.isNotBlank() ?: false,
    )
    MainTextArea(
      value = state.content,
      label = "Nota",
      onValueChange = { formViewModel.onContentChange(it) },
      isError = state.contentError?.isNotBlank() ?: false,
    )
    Spacer(
      modifier = Modifier
        .padding(8.dp)
    )
    MainBtn(
      text = "Actualizar Nota",
      onClick = { formViewModel.submit() },
    )
  }
}