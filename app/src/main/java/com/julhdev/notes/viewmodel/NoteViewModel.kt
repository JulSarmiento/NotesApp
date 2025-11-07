package com.julhdev.notes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julhdev.notes.data.local.Note
import com.julhdev.notes.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * NoteViewModel es un ViewModel que gestiona el estado y las operaciones relacionadas con las entidades Note.
 * Interactúa con el NoteRepository para realizar operaciones CRUD y expone un StateFlow de la lista de Notes.
 * @property repository El NoteRepository utilizado para las operaciones de datos.
 * @see NoteRepository
 * @see Note
 * @usage Inyectar NoteViewModel en componentes de UI para observar y manipular datos de Note.
 */
@HiltViewModel
class NoteViewModel @Inject constructor(
  private val repository: NoteRepository
) : ViewModel() {

  private val _notes = MutableStateFlow<List<Note>>(emptyList())
  val notes = _notes.asStateFlow()

  init {
    viewModelScope.launch(Dispatchers.IO) {
      repository.getNotes().collect { item ->
        _notes.value = item
      }
    }
  }

  /**
   * Elimina una entidad Note del repositorio.
   * @param note La entidad Note que se va a eliminar.
   * @see Note
   * @usage Llamar a deleteNote(note) para eliminar una Note de la fuente de datos.
   */
  fun deleteNote(note: Note) {
    viewModelScope.launch(Dispatchers.IO) {
      repository.deleteNote(note)
    }
  }
}