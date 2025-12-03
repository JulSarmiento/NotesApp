package com.julhdev.notes.data.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.julhdev.notes.data.model.NoteModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesFirestoreDataSource @Inject constructor(
  store: FirebaseFirestore
) {
  private val notesCollection = store.collection("Notes")
  /**
   * Guarda la nota en la base de datos de Firestore
   * @param note Nota a guardar
   * @param currentUser Usuario actual
   * @return Task<Void?>
   * @usage saveNote(note, currentUser)
   */
  fun saveNote(note: NoteModel, currentUser: FirebaseUser?) {
    val id = currentUser?.uid ?: return
    val noteMap = NoteModel(
      userId = id,
      title = note.title,
      content = note.content,
      timestamp = note.timestamp
    ).toMap()
    notesCollection.add(noteMap)
  }

  /**
   * Actualiza la nota en la base de datos de Firestore
   * @param note Nota a actualizar
   * @param noteId Id de la nota a actualizar
   * @param currentUser Usuario actual
   * @return Task<Void?>
   * @usage updateNote(note, noteId, currentUser)
   */
  fun updateNote(note: NoteModel, noteId: String, currentUser: FirebaseUser?) {
    val id = currentUser?.uid ?: return
    val noteMap = NoteModel(
      userId = id,
      title = note.title,
      content = note.content,
      timestamp = note.timestamp
    ).toMap()
    notesCollection.document(noteId).set(noteMap, SetOptions.merge())
  }

  /**
   * Obtiene las notas de la base de datos de Firestore
   * @param user Usuario actual
   * @return Flow<UserModel?>
   * @usage getUser(currentUser)
   */
  fun getNotes(user: FirebaseUser?): Flow<List<NoteModel>> = callbackFlow {
    if (user == null) {
      trySend(emptyList())
      close()
      return@callbackFlow
    }

    val listener = notesCollection
      .whereEqualTo("userId", user.uid)
      .addSnapshotListener { snapshot, error ->
        if (error != null) {
          close(error)
          return@addSnapshotListener
        }

        val notes = snapshot!!.documents.map { document ->
          NoteModel(
            uid = document.id,
            userId = document.getString("userId"),
            title = document.getString("title") ?: "",
            content = document.getString("content") ?: "",
            timestamp = document.getString("timestamp") ?: ""
          )
        }

        trySend(notes)
      }
    awaitClose { listener.remove() }
  }

  /**
   * Obtiene la nota de la base de datos de Firestore
   * @param noteId Id de la nota a obtener
   * @return Flow<NoteModel?>
   * @usage getNoteById(noteId)
   */
  fun getNoteById(noteId: String): Flow<NoteModel?> {
    return callbackFlow {
      val listener = notesCollection.document(noteId).addSnapshotListener { snapshot, error ->
        if (error != null) {
          close(error)
          return@addSnapshotListener
        }
        val note = snapshot?.let {
          NoteModel(
            uid = it.id,
            userId = it.getString("userId"),
            title = it.getString("title") ?: "",
            content = it.getString("content") ?: "",
            timestamp = it.getString("timestamp") ?: ""
          )
        }
        trySend(note)
      }
      awaitClose { listener.remove() }
    }
  }

  /**
   * Elimina la nota de la base de datos de Firestore
   * @param noteId Id de la nota a eliminar
   * @return Task<Void?>
   * @usage deleteNote(noteId)
   */
  fun deleteNote(noteId: String) {
    notesCollection.document(noteId).delete()
  }
}

