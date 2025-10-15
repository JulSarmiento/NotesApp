package com.julhdev.notes.data.repository

import com.julhdev.notes.data.local.Note
import com.julhdev.notes.data.local.NoteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


/**
 * Clase Repositorio para manejar las operaciones de datos relacionadas con las notas.
 * Proporciona métodos para agregar, actualizar, eliminar y recuperar notas desde la base de datos.
 * Utiliza inyección de dependencias para obtener una instancia de NoteDao.
 * @see NoteDao
 * @see Note
 * @usage Inyectar NoteRepository en ViewModels o casos de uso para acceder a las operaciones de notas.
 */
class NoteRepository @Inject constructor(
  private val noteDao: NoteDao
) {

  /**
   * Agrega una nueva nota a la base de datos.
   * @param note La nota a agregar.
   * @see Note
   * @usage noteRepository.addNote(note)
   */
  suspend fun addNote(note: Note){
    noteDao.insertNote(note)
  }

  /**
   * Actualiza una nota existente en la base de datos.
   * @param note La nota a actualizar.
   * @see Note
   * @usage noteRepository.updateNote(note)
   */
  suspend fun updateNote(note: Note){
    noteDao.updateNote(note)
  }

  /**
   * Elimina una nota de la base de datos.
   * @param note La nota a eliminar.
   * @see Note
   * @usage noteRepository.deleteNote(note)
   */
  suspend fun deleteNote(note: Note){
    noteDao.deleteNote(note)
  }

  /**
   * Recupera todas las notas de la base de datos como un flujo.
   * @return Un flujo que emite una lista de notas.
   * @see Note
   * @usage val notesFlow: Flow<List<Note>> = noteRepository.getNotes()
   */
  fun getNotes(): Flow<List<Note>> = noteDao.getNotes().flowOn(Dispatchers.IO).conflate()

  /**
   * Recupera una nota por su ID como un flujo.
   * @param id El ID de la nota a recuperar.
   * @return Un flujo que emite la nota correspondiente al ID, o null si no se encuentra.
   * @see Note
   * @usage val noteFlow: Flow<Note?> = noteRepository.getNoteById(id)
   */
  fun getNoteById(id: Int): Flow<Note?> = noteDao.getNoteById(id).flowOn(Dispatchers.IO).conflate()
}