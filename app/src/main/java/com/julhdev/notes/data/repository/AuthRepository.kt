package com.julhdev.notes.data.repository

import com.google.firebase.auth.FirebaseUser
import com.julhdev.notes.data.firebase.FirebaseAuthDataSource
import javax.inject.Inject

/**
 * Repopsitorio de Firebase Authentication.
 * @param dataSource [FirebaseAuthDataSource]
 */
class AuthRepository @Inject constructor(
  private val dataSource: FirebaseAuthDataSource
) {

  /**
   * Prepara la autenticación con Firebase.
   * @return [Result] con el resultado de la operación.
   */
  suspend fun warmUpAuth(): Result<Unit> {
    return try {
      dataSource.warmUpAuth()
      Result.success(Unit)
    } catch (e: Exception) {
      Result.failure(e)
    }
  }

  /**
   * Autentica un usuario con correo electrónico y contraseña.
   * @param email Correo electrónico del usuario.
   * @param password Contraseña del usuario.
   * @return [Result] con el usuario autenticado o un error.
   */
  suspend fun login(email: String, password: String): Result<FirebaseUser?> {
    return try {
      val user = dataSource.login(email, password)
      Result.success(user)
    } catch (e: Exception) {
      Result.failure(e)
    }
  }

  /**
   * Registra un nuevo usuario con correo electrónico y contraseña.
   * @param email Correo electrónico del usuario.
   * @param password Contraseña del usuario.
   * @return [Result] con el usuario registrado o un error.
   */
  suspend fun register(email: String, password: String, username: String): Result<FirebaseUser?> {
    return try {
      val user = dataSource.register(email, password)
      Result.success(user)
      } catch (e: Exception) {
      Result.failure(e)
    }
  }

  /**
   * Obtiene el usuario actual autenticado.
   * @return [FirebaseUser] o nulo si no hay usuario autenticado.
   */
  fun getCurrentUser(): FirebaseUser? {
    return dataSource.getCurrentUser()
  }

  /**
   * Cierra la sesión del usuario actual.
   */
  suspend fun logout() {
    dataSource.logout()
  }

}