package com.julhdev.notes.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.julhdev.notes.data.model.FormState
import com.julhdev.notes.data.model.NoteModel
import com.julhdev.notes.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * FormEvent es una clase sellada que representa los diferentes eventos que pueden ocurrir en el formulario de notas.
 * Incluye eventos para el éxito en el envío del formulario y para mostrar mensajes al usuario.
 * @see FormViewModel
 * @usage Utilizar FormEvent para comunicar eventos desde FormViewModel a la UI.
 */
sealed class FormEvent {
  object SubmitSuccess : FormEvent()

  /**
   * Evento para mostrar un mensaje al usuario.
   * @property msg El mensaje a mostrar.
   */
  data class ShowMessage(val msg: String) : FormEvent()
}

/**
 * FormViewModel es un ViewModel que gestiona el estado y las operaciones relacionadas con el formulario de notas.
 * Interactúa con el NoteRepository para realizar operaciones CRUD y maneja la validación y el envío del formulario.
 * @property repository El NoteRepository utilizado para las operaciones de datos.
 * @see NoteRepository
 * @see FormState
 * @usage Inyectar FormViewModel en componentes de UI para observar y manipular el estado del formulario de notas.
 */
@HiltViewModel
class FormViewModel @Inject constructor(
  private val repository: NoteRepository,
  private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

  private val _uiState = MutableStateFlow(FormState())
  val uiState: StateFlow<FormState> = _uiState
  private val _events = Channel<FormEvent>(Channel.BUFFERED)
  val events = _events.receiveAsFlow()

  init {
    val draftTitle = savedStateHandle.get<String>("draftTitle") ?: ""
    val draftContent = savedStateHandle.get<String>("draftContent") ?: ""
    val editId = savedStateHandle.get<Int>("editId")

    if (editId == null && (draftTitle.isNotEmpty() || draftContent.isNotEmpty())) {
      _uiState.value = uiState.value.copy(
        title = draftTitle,
        content = draftContent
      )
    }
  }

  /**
   * Resetea el formulario a su estado inicial.
   * Limpia los datos guardados en SavedStateHandle y actualiza el estado del formulario.
   * @usage Llamar a resetForm() para limpiar el formulario después de un envío exitoso o al cancelar la edición.
   */
  fun resetForm() {
    savedStateHandle.remove<String>("draftTitle")
    savedStateHandle.remove<String>("draftContent")
    savedStateHandle.remove<Int>("editId")
    _uiState.value = FormState()
  }

  /**
   * Carga una nota por su ID y actualiza el estado del formulario.
   * Si la nota ya está cargada y el título no está vacío, no realiza ninguna acción.
   * @param noteId El ID de la nota a cargar.
   * @usage Llamar a loadNote(noteId) para cargar los datos de una nota específica en el formulario.
   */
  fun loadNote(noteId: String) {
    if (_uiState.value.noteId == noteId && _uiState.value.title.isNotBlank()) return
    savedStateHandle["editId"] = noteId

    viewModelScope.launch {
      repository.getNoteById(noteId).firstOrNull()?.let { note ->
        _uiState.update {
          it.copy(
            noteId = note.uid,
            title = note.title,
            content = note.content,
            timeStamp = note.timestamp.toLong(),
            titleError = null,
            contentError = null
          )
        }
      }
    }
  }

  /**
   * Valida el título de la nota de forma síncrona.
   * @param title El título a validar.
   * @return Un mensaje de error si el título es inválido, o null si es válido.
   * @usage Llamar a validateTitleSync(title) para validar el título antes de actualizar el estado del formulario.
   */
  private fun validateTitleSync(title: String): String? =
    when {
      title.isBlank() -> "El título no puede estar vacío."
      title.length > 80 -> "El título es demasiado largo."
      else -> null
    }

  /**
   * Valida el contenido de la nota de forma síncrona.
   * @param content El contenido a validar.
   * @return Un mensaje de error si el contenido es inválido, o null si es válido.
   * @usage Llamar a validateContentSync(content) para validar el contenido antes de actualizar el estado del formulario.
   */
  private fun validateContentSync(content: String): String? =
    when {
      content.isBlank() -> "La nota no puede estar vacía."
      else -> null
    }

  /**
   * Maneja el cambio en el título de la nota.
   * Actualiza el estado del formulario y guarda el título en SavedStateHandle.
   * @param new El nuevo título ingresado por el usuario.
   * @usage Llamar a onTitleChange(new) cuando el usuario modifique el título en la UI.
   */
  fun onTitleChange(new: String) {
    _uiState.update {
      it.copy(title = new, titleError = null)
    }
    savedStateHandle["draftTitle"] = new
  }

  /**
   * Maneja el cambio en el contenido de la nota.
   * Actualiza el estado del formulario y guarda el contenido en SavedStateHandle.
   * @param new El nuevo contenido ingresado por el usuario.
   * @usage Llamar a onContentChange(new) cuando el usuario modifique el contenido en la UI.
   */
  fun onContentChange(new: String) {
    _uiState.update {
      it.copy(
        content = new,
        contentError = null,
      )
    }
    savedStateHandle["draftContent"] = new
  }

  /**
   * Envía el formulario de la nota.
   * Realiza la validación final y guarda o actualiza la nota en el repositorio.
   * Maneja el estado de envío y envía eventos de éxito o error.
   * @usage Llamar a submit() cuando el usuario presione el botón de guardar en la UI.
   */
  fun submit(user: FirebaseUser?) = viewModelScope.launch {
    val current = _uiState.value

    val contentError = validateContentSync(current.content.trim())
    val titleError = validateTitleSync(current.title)

    if( titleError != null || contentError != null) {
      _uiState.update {
        it.copy(
          titleError = titleError,
          contentError = contentError
        )
      }
      _events.send(FormEvent.ShowMessage(titleError ?: contentError ?: "Errores en el formulario"))
      return@launch
    }

    if (!current.isValid || current.isSubmitting) return@launch
    _uiState.update { it.copy(isSubmitting = true) }
    try {
      val note = NoteModel(
        userId = user?.uid,
        title = current.title.trim(),
        content = current.content,
        timestamp = System.currentTimeMillis().toString()
      )

      withContext(Dispatchers.IO) {
        if (current.noteId != null) {
//          repository.updateNote(note, user)
          Log.d("Updating note:", "$note")
        } else {
          repository.addNote(note, user)
          Log.d("Adding note:", "$note")
        }
      }
      resetForm()
      _events.send(FormEvent.SubmitSuccess)
    } catch (e: Exception) {
      _events.send(FormEvent.ShowMessage("Error al guardar la nota: ${e.localizedMessage}"))
      _uiState.update { it.copy(isSubmitting = false) }
    }
  }
}