package com.julhdev.notes.di

import android.content.Context
import androidx.room.Room
import com.julhdev.notes.data.local.NoteDao
import com.julhdev.notes.data.local.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * AppModule es un módulo de Dagger Hilt que proporciona dependencias a nivel de aplicación.
 * Incluye provisiones para NotesDatabase y NoteDao.
 * Estas dependencias están en el ámbito de singleton para asegurar una única instancia durante el ciclo de vida de la aplicación.
 * @see NotesDatabase
 * @see NoteDao
 * @usage Inyecta NoteDao en repositorios o view models para acceder a las operaciones de la base de datos.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @Singleton
  @Provides
  fun providesNoteDao(notesDatabase: NotesDatabase): NoteDao {
    return notesDatabase.noteDao()
  }

  @Singleton
  @Provides
  fun providesNotesDatabase(@ApplicationContext context: Context): NotesDatabase {
    return Room.databaseBuilder(
      context,
      NotesDatabase::class.java,
      "notes_database"
    ).fallbackToDestructiveMigration(false).build()
  }
}