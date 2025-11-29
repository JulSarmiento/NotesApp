package com.julhdev.notes.data.firebase

import android.net.Uri
import com.google.firebase.auth.EmailAuthProvider
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
class AuthDataSource @Inject constructor(
  private val auth: FirebaseAuth,
) {

  /**
   * Inicia sesión con un correo electrónico y una contraseña.
   * @param email Correo electrónico del usuario.
   * @param password Contraseña del usuario.
   * @return FirebaseUser si el inicio de sesión es exitoso, de lo contrario null.
   * @throws Exception Si ocurre un error durante el inicio de sesión.
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
   * Actualiza la contraseña de un usuario.
   * @param newPassword Nueva contraseña del usuario.
   * @return Task<Void> si la actualización de contraseña es exitosa, de lo contrario null.
   */
  suspend fun updatePassword(newPassword: String, actionCode: String): Void? {
    return auth.confirmPasswordReset(actionCode,newPassword).await()
  }

  suspend fun reAuthenticateUser(email: String, password: String): Void? {
    val credential = EmailAuthProvider.getCredential(email, password)
    return auth.currentUser?.reauthenticate(credential)?.await()
  }

  suspend fun resetPassword(email: String): Void? {
    return auth.sendPasswordResetEmail(email).await()
  }

  fun handlePasswordResetLink(deepLink: String): String? {
    return Uri.parse(deepLink).getQueryParameter("oobCode")
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