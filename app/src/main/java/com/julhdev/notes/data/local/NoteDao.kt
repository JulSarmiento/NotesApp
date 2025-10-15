package com.julhdev.notes.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) para realizar operaciones CRUD en las entidades Note en la base de datos.
 * Proporciona métodos para insertar, actualizar, eliminar y consultar registros de notas.
 * Utiliza Kotlin Coroutines Flow para el manejo asíncrono de datos.
 * @see Flow
 * @usage val notes: Flow<List<Note>> = noteDao.getNotes()
 */
@Dao
interface NoteDao {

  @Query("SELECT * FROM notes ORDER BY timestamp DESC")
  fun getNotes(): Flow<List<Note>>

  @Query("SELECT * FROM notes WHERE id = :id")
  fun getNoteById(id: Int): Flow<Note?>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertNote(note: Note)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun updateNote(note: Note)

  @Delete
  suspend fun deleteNote(note: Note)

}