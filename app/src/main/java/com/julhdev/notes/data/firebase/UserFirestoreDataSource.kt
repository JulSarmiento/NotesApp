package com.julhdev.notes.data.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.julhdev.notes.data.model.NoteModel
import com.julhdev.notes.data.model.UserModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
/**
 * Clase de fuente de datos para Firebase Firestore
 * @param store Instancia de FirebaseFirestore
 * @constructor Crea una instancia de UserFirebaseFirestoreDataSource
 * @usage val dataSource = UserFirebaseFirestoreDataSource(store)
 */
class UserFirestoreDataSource @Inject constructor(
  private val store: FirebaseFirestore,
) {

  private val usersCollection = store.collection("Users")
  private val notesCollection = store.collection("Notes")

  /**
   * Guarda el usuario en la base de datos de Firestore
   * @param username Nombre de usuario
   * @param currentUser Usuario actual
   * @return Task<Void?>
   * @usage saveUser("username", currentUser)
   */
  fun saveUser(username: String, currentUser: FirebaseUser) {
    val id = currentUser.uid
    val email = currentUser.email
    val user = UserModel(
      id = id,
      userName = username,
      email = email
    ).toMap()
    Log.d("Saving user:", "saveUser: $user")
    usersCollection.document(id).set(user)
  }

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
    Log.d("Saving note:", "$noteMap")
    notesCollection.add(noteMap)
  }

  /**
   * Obtiene el usuario de la base de datos de Firestore
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
}

