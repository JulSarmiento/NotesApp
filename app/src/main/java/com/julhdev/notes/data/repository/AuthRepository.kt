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
   * Autentica un usuario con correo electrónico y contraseña.
   * @param email Correo electrónico del usuario.
   * @param password Contraseña del usuario.
   * @return [Result] con el usuario autenticado o un error.
   */
  suspend fun login(email: String, password: String):  Result<FirebaseUser?> {
    return dataSource.login(email, password)
  }

  /**
   * Registra un nuevo usuario con correo electrónico y contraseña.
   * @param email Correo electrónico del usuario.
   * @param password Contraseña del usuario.
   * @return [Result] con el usuario registrado o un error.
   */
  suspend fun register(email: String, password: String, username: String): Result<FirebaseUser?> {
    return dataSource.register(email, password, username)
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
  fun logout() {
    dataSource.logout()
  }

}