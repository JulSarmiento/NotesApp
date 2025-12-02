package com.julhdev.notes.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.julhdev.notes.R
import com.julhdev.notes.components.FloatingButton
import com.julhdev.notes.components.MainTitle
import com.julhdev.notes.components.NoteCard
import com.julhdev.notes.components.PngImage
import com.julhdev.notes.components.SubTitle
import com.julhdev.notes.components.TopBar
import com.julhdev.notes.data.local.Note
import com.julhdev.notes.data.model.NoteModel
import com.julhdev.notes.navigation.Routes
import com.julhdev.notes.viewmodel.AuthViewModel
import com.julhdev.notes.viewmodel.NoteViewModel
import com.julhdev.notes.viewmodel.ThemeViewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

/**
 * HomeView Composable
 * @param navController de tipo NavController que representa el controlador de navegación de la aplicación.
 * @param noteViewModel de tipo NoteViewModel que representa el ViewModel de notas.
 * @param themeViewModel de tipo ThemeViewModel que representa el ViewModel de temas
 * @param authViewModel de tipo AuthViewModel que representa el ViewModel de autenticación
 * @return componente que representa la vista principal de la aplicación de notas.
 * @usage HomeView( navController = navController, noteViewModel = noteViewModel, themeViewModel = themeViewModel, authViewModel = authViewModel)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
  navController: NavController,
  noteViewModel: NoteViewModel,
  themeViewModel: ThemeViewModel,
  authViewModel: AuthViewModel
) {

  LaunchedEffect(Unit) {
    noteViewModel.loadNotes(authViewModel.currentUser())
  }


  Scaffold(
    topBar = {
      TopBar(
        navController = navController,
        themeViewModel = themeViewModel,
        showBackBtn = false,
        showLogoutBtn = true,
        authViewModel = authViewModel
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
      HomeViewContent(noteViewModel, navController)
    }
  }
}

/**
 * HomeViewContent Composable
 * @return un componente que muestra el contenido principal de la vista de inicio.
 * @usage HomeViewContent(noteViewModel = noteViewModel)
 */
@Composable
fun HomeViewContent(noteViewModel: NoteViewModel, navController: NavController) {
  val notes by noteViewModel.notes.collectAsState()
  val onDeleteNote: (Note) -> Unit = { note ->
    noteViewModel.deleteNote(note)
  }
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
      color = MaterialTheme.colorScheme.secondary,
    )
    Column(
      modifier = Modifier
        .fillMaxSize()
    ) {
      if (notes.isEmpty()) {
        HomeEmptyContent()
      } else {
        HomeNotesContent(notes, onDeleteNote, navController = navController)
      }
    }
  }
}

/**
 * HomeNotesContent Composable
 * @param notes de tipo List<Note> que representa la lista de notas disponibles
 * @param onDeleteNote de tipo (Note) -> Unit que representa la función a ejecutar al eliminar una nota
 * @return un componente que muestra una lista de notas disponibles.
 * @usage HomeNotesContent( notes = notes, onDeleteNote = { note -> noteViewModel.deleteNote(note) } )
 */
@Composable
fun HomeNotesContent(notes: List<NoteModel>, onDeleteNote: (Note) -> Unit, navController: NavController) {
  Spacer(
    modifier = Modifier
      .height(10.dp)
  )
  LazyColumn(
    modifier = Modifier
      .padding(all = 10.dp)
  ) {
    items(notes) {
     val delete = SwipeAction(
       icon = rememberVectorPainter(
         image = Icons.Default.Delete,
       ),
       background = MaterialTheme.colorScheme.primary,
       onSwipe = {
//          onDeleteNote(it)
       }
     )
      SwipeableActionsBox(
        endActions = listOf(delete),
        swipeThreshold = 120.dp
      ) {
        NoteCard(
          title = it.title,
          content = it.content,
          time = it.timestamp,
          onClick = {
//            navController.navigate("${Routes.EDIT}/${it.id}")
          }
        )
      }
      Spacer(
        modifier = Modifier
          .height(10.dp)
      )
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
    modifier = Modifier
      .fillMaxSize()
      .padding(top = 20.dp)
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