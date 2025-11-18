package com.julhdev.notes.data.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
/**
 * Clase que implementa la lógica de autenticación con Firebase.
 * @param auth Instancia de FirebaseAuth para interactuar con Firebase Authentication.
 * @usage Ejemplo de uso:
 * val firebaseAuth = FirebaseAuth.getInstance()
 * val authDataSource = FirebaseAuthDataSource(firebaseAuth)
 */
class FirebaseAuthDataSource @Inject constructor(
  private val auth: FirebaseAuth
) {

  fun warmUpAuth(): Task<AuthResult?> {
    return auth.signInAnonymously()
  }

  /**
   * Inicia sesión con un correo electrónico y una contraseña.
   * @param email Correo electrónico del usuario.
   * @param password Contraseña del usuario.
   * @return FirebaseUser si el inicio de sesión es exitoso, de lo contrario null.
   * @throws Exception Si ocurre un error durante el inicio de sesión.
   * @usage Ejemplo de uso:
   * val currentUser = authDataSource.login("john.mclean@examplepetstore.com", "password123")
   */
  suspend fun login(email: String, password: String): FirebaseUser? {
    return auth.signInWithEmailAndPassword(email, password).await().user
  }

  /**
   * Registra un nuevo usuario con un correo electrónico y una contraseña.
   * @param email Correo electrónico del usuario.
   * @param password Contraseña del usuario.
   * @return FirebaseUser si el registro es exitoso, de lo contrario null.
   * @throws Exception Si ocurre un error durante el registro.
   * @usage Ejemplo de uso:
   * val newUser = authDataSource.register("john.archibald.campbell@example-pet-store.com", "password456")
   */
  suspend fun register(email: String, password: String): FirebaseUser? {
    return auth.createUserWithEmailAndPassword(email, password).await().user
  }

  /**
   * Obtiene el usuario actual autenticado.
   * @return FirebaseUser si el usuario está autenticado, de lo contrario null.
   * @usage Ejemplo de uso:
   * val currentUser = authDataSource.getCurrentUser()
   */
  fun getCurrentUser(): FirebaseUser? {
    return auth.currentUser
  }

  /**
   * Cierra la sesión actual del usuario.
   * @throws Exception Si ocurre un error durante el cierre de sesión
   * @usage Ejemplo de uso:
   * authDataSource.logout()
   */

  fun logout() {
    auth.signOut()
  }

}