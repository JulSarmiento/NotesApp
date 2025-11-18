package com.julhdev.notes.data.firebase

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.julhdev.notes.data.model.UserModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Clase de fuente de datos para Firebase Firestore
 * @param store Instancia de FirebaseFirestore
 * @constructor Crea una instancia de UserFirebaseFirestoreDataSource
 * @usage val dataSource = UserFirebaseFirestoreDataSource(store)
 */
class UserFirebaseFirestoreDataSource @Inject constructor(
  private val store: FirebaseFirestore,
) {

  private val userCollection: String = "Users"

  /**
   * Guarda el usuario en la base de datos de Firestore
   * @param username Nombre de usuario
   * @param currentUser Usuario actual
   * @return Task<Void?>
   * @usage saveUser("username", currentUser)
   */
  suspend fun saveUser(username: String, currentUser: FirebaseUser?) {
    val id = currentUser?.uid ?: error("User not logged in")
    val email = currentUser.email ?: ""
    val user = UserModel(id, username, email)

    store.collection(userCollection)
      .document(id)
      .set(user)
      .await()
  }
}
