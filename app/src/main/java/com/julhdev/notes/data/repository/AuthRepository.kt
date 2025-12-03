package com.julhdev.notes.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.julhdev.notes.data.firebase.AuthDataSource
import com.julhdev.notes.data.firebase.UserFirestoreDataSource
import com.julhdev.notes.data.model.UserModel
import com.julhdev.notes.utils.resources.Resource
import com.julhdev.notes.utils.safeApiCall
import javax.inject.Inject

/**
 * Repositorio de Firebase Authentication.
 * @param auth [AuthDataSource]
 * @param userStore [UserFirestoreDataSource]
 */
class AuthRepository @Inject constructor(
  private val auth: AuthDataSource,
  private val userStore: UserFirestoreDataSource
) {

  /**
   * Obtiene el usuario actual autenticado.
   * @return [FirebaseUser] si el usuario está autenticado, de lo contrario null.
   * @usage Ejemplo de uso:
   * val currentUser = authRepository.getCurrentUser()
   */
  fun getCurrentUser(): FirebaseUser? {
    return auth.getCurrentUser()
  }

  /**
   * Obtiene el usuario actual autenticado.
   * @return [UserModel] si el usuario está autenticado, de lo contrario null.
   * @usage Ejemplo de uso:
   * val currentUser = authRepository.getCurrentUser()
   */
  suspend fun getUser(user: FirebaseUser?): UserModel? {
    val uid = user?.uid ?: return null
    return userStore.getUser(uid)
  }

  /**
   * Autentica un usuario con correo electrónico y contraseña.
   * @param email Correo electrónico del usuario.
   * @param password Contraseña del usuario.
   */
  suspend fun login(email: String, password: String): Resource<FirebaseUser?> {
    return safeApiCall { auth.login(email, password) }
  }

  /**
   * Registra un nuevo usuario con correo electrónico y contraseña.
   * @param email Correo electrónico del usuario.
   * @param password Contraseña del usuario.
   * @return [Result] con el usuario registrado o un error.
   */
  suspend fun register(
    email: String,
    password: String,
    username: String,
  ): Resource<FirebaseUser?> {
    return safeApiCall {
      val user = auth.register(email, password)
      if (user != null) {
        userStore.saveUser(username, user)
      }
      user
    }
  }

  /**
   * Restablece la contraseña de un usuario.
   * @param email Correo electrónico del usuario.
   * @return [Resource<Unit>] con el resultado de la operación o un error.
   * @usage Ejemplo de uso:
   * val result = authRepository.resetPassword(email)
   */
  suspend fun resetPassword(email: String): Resource<Unit> {
    return safeApiCall { auth.resetPassword(email) }
  }

  /**
   * Cierra la sesión del usuario actual.
   */
  fun logout() {
    auth.logout()
  }
}