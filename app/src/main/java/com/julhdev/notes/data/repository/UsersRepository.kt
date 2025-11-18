package com.julhdev.notes.data.repository

import com.google.firebase.auth.FirebaseUser
import com.julhdev.notes.data.firebase.UserFirebaseFirestoreDataSource
import javax.inject.Inject

/**
 * Repositorio para operaciones relacionadas con usuarios.
 * @property dataSource La fuente de datos para interactuar con la base de datos.
 * @usage @Inject constructor(dataSource: UserFirebaseFirestoreDataSource)
 */
class UsersRepository @Inject constructor(
  private val dataSource: UserFirebaseFirestoreDataSource
) {

  /**
   * Guarda un usuario en la fuente de datos.
   * @param username El nombre de usuario del usuario a guardar.
   * @param currentUser El objeto FirebaseUser del usuario actual.
   * @return El resultado de la operación.
   * @usage saveUser("johndoe", currentUser).
   */
  suspend fun saveUser(username: String, currentUser: FirebaseUser?): Result<Unit> {
    return try {
      dataSource.saveUser(username, currentUser)
      Result.success(Unit)
    } catch (e: Exception) {
      Result.failure(e)
    }
  }

}