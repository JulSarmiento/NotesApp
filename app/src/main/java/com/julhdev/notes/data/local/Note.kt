package com.julhdev.notes.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class representando una nota en la base de datos.
 * @property id Identificador único de la nota (autogenerado).
 * @property title Título de la nota.
 * @property content Contenido de la nota.
 * @property timestamp Marca de tiempo de la creación o última modificación de la nota.
 * @usage val note = Note(title = "Mi Nota", content = "Contenido de la nota")
 */
@Entity(tableName = "notes")
data class Note (

  @PrimaryKey(autoGenerate = true)
  val id: Int = 0,

  val title: String,

  val content: String,

  val timestamp: Long = System.currentTimeMillis()
)
