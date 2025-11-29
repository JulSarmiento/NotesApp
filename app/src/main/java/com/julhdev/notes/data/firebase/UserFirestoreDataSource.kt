package com.julhdev.notes.data.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.julhdev.notes.data.model.UserModel
import kotlinx.coroutines.tasks.await
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
    Log.d("Login", "saveUser: $user")
    usersCollection.document(id).set(user)
  }


  /**
   * Obtiene el usuario de la base de datos de Firestore
   * @param email Correo electrónico del usuario
   * @return Boolean
   * @usage getUser("email")
   */
  suspend fun ifUserExist(email: String): Boolean {
    val userExist =  usersCollection.whereEqualTo("email", email).get().await()
    Log.d("Login", "getUser: $userExist")
    return userExist != null && !userExist.isEmpty
  }

}

