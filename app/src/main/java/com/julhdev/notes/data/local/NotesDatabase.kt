package com.julhdev.notes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Clase abstracta que representa la base de datos Room para las entidades Note.
 * Proporciona acceso al NoteDao para realizar operaciones en la base de datos.
 * @see NoteDao
 * @usage val db = Room.databaseBuilder(context, NotesDatabase::class.java, "notes_database").build()
 */
@Database(
  entities = [Note::class],
  version = 1,
  exportSchema = false
)
abstract class NotesDatabase: RoomDatabase() {
  abstract fun noteDao(): NoteDao
}