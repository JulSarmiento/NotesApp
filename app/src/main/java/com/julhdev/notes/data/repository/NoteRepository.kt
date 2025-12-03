package com.julhdev.notes.data.repository

import com.google.firebase.auth.FirebaseUser
import com.julhdev.notes.data.firebase.NotesFirestoreDataSource
import com.julhdev.notes.data.model.NoteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Clase Repositorio para manejar las operaciones de datos relacionadas con las notas.
 * Proporciona métodos para agregar, actualizar, eliminar y recuperar notas desde la base de datos.
 * Utiliza inyección de dependencias para obtener una instancia de NoteDao.
 * @property noteStore El origen de datos de las notas.
 * @usage Inyectar NoteRepository en ViewModels o casos de uso para acceder a las operaciones de notas.
 */
class NoteRepository @Inject constructor(
  private val noteStore: NotesFirestoreDataSource
) {

  /**
   * Agrega una nueva nota a la base de datos.
   * @param noteStore La nota a agregar.
   * @usage noteRepository.addNote(note)
   */
  fun addNote(note: NoteModel, user: FirebaseUser?) {
    noteStore.saveNote(note, user)
  }

  /**
   * Actualiza una nota existente en la base de datos.
   * @param noteStore La nota a actualizar.
   * @usage noteRepository.updateNote(note)
   */
  fun updateNote(note: NoteModel, noteId: String, user: FirebaseUser?) {
    noteStore.updateNote(note, noteId, user)
  }

  /**
   * Elimina una nota de la base de datos.
   * @param noteStore La nota a eliminar.
   * @usage noteRepository.deleteNote(note)
   */
  fun deleteNote(noteId: String) {
    noteStore.deleteNote(noteId)
  }

  /**
   * Recupera todas las notas de la base de datos como un flujo.
   * @param user El usuario actual.
   * @return Un flujo que emite una lista de notas.
   * @usage val notesFlow: Flow<List<Note>> = noteRepository.getNotes()
   */
  fun getNotes(user: FirebaseUser?): Flow<List<NoteModel>> =
    noteStore.getNotes(user).flowOn(Dispatchers.IO).conflate()

  /**
   * Recupera una nota por su ID como un flujo.
   * @param id El ID de la nota a recuperar.
   * @return Un flujo que emite la nota correspondiente al ID, o null si no se encuentra.
   * @usage val noteFlow: Flow<Note?> = noteRepository.getNoteById(id)
   */
  fun getNoteById(id: String?): Flow<NoteModel?> =
    noteStore.getNoteById(id ?: "").flowOn(Dispatchers.IO).conflate()
}