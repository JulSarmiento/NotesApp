package com.julhdev.notes.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.julhdev.notes.data.model.FormState
import com.julhdev.notes.viewmodel.FormEvent
import com.julhdev.notes.viewmodel.FormViewModel
import com.julhdev.notes.viewmodel.ThemeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * AddView Composable
 * @param navController de tipo NavController que representa el controlador de navegación
 * @param themeViewModel de tipo ThemeViewModel que representa el ViewModel del tema
 * @param formViewModel de tipo FormViewModel que representa el ViewModel del formulario
 * @usage AddView(navController = navController, themeViewModel = themeViewModel, formViewModel = formViewModel)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddView(
  navController: NavController,
  themeViewModel: ThemeViewModel,
  formViewModel: FormViewModel
) {
  var showDialog by remember { mutableStateOf(false ) }
  val theme = themeViewModel.isDark.collectAsState().value
  val state by formViewModel.uiState.collectAsState()
  val scaffoldState = remember { SnackbarHostState() }

  LaunchedEffect(Unit) {
    formViewModel.events.collect { event ->
      when (event) {
        is FormEvent.SubmitSuccess -> {
          showDialog = true
        }
        is FormEvent.ShowMessage -> {
          scaffoldState.showSnackbar(event.msg)
        }
      }
    }
  }

  Scaffold(
    snackbarHost = {
      SnackbarHost(scaffoldState)
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
    }
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .padding(innerPadding)
    ) {
      AddViewContent(
        state = state,
        onTitleChange = formViewModel::onTitleChange,
        onContentChange = formViewModel::onContentChange,
        onSubmit = { formViewModel.submit() },
      )

      if(showDialog){
        MainDialog(
          title = "Nota guardada",
          content = "La nota se ha guardado correctamente.",
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
 * AddViewContent Composable
 * @param state de tipo FormState que representa el estado del formulario
 * @param onTitleChange de tipo (String) -> Unit que representa la acción a realizar al cambiar el título
 * @param onContentChange de tipo (String) -> Unit que representa la acción a realizar al cambiar el contenido
 * @param onSubmit de tipo () -> Unit que representa la acción a realizar al enviar el formulario
 * @usage AddViewContent(state = formState, onTitleChange = { /* acción */ }, onContentChange = { /* acción */ }, onSubmit = { /* acción */ })
 */
@Composable
fun AddViewContent(
  state: FormState,
  onTitleChange: (String) -> Unit,
  onContentChange: (String) -> Unit,
  onSubmit: () -> Unit,
) {
  SubTitle(
    text = "Crea una nueva nota aquí:",
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
      onValueChange = onTitleChange
    )
    MainTextArea(
      value = state.content,
      label = "Nota",
      onValueChange = onContentChange
    )
    Spacer(
      modifier = Modifier
        .padding(8.dp)
    )
    MainBtn(
      text = "Guardar Nota",
      enabled = state.title.isNotBlank() && state.content.isNotBlank(),
      onClick = { onSubmit() },
    )
  }
}
