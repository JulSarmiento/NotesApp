package com.julhdev.notes.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.julhdev.notes.data.model.UserModel
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

  private val auth: FirebaseAuth = Firebase.auth

  var uiError by mutableStateOf<String?>(null)
    private set

  var errorMessage: Boolean by mutableStateOf(false)

  /**
   * Inicia sesión con un usuario de firebase
   * @param email Correo del usuario
   * @param password Contraseña del usuario
   * @param onResult Callback que se ejecuta al finalizar el inicio de sesión
   * @return Unit
   * @usage AuthViewModel().login(email, password) {}
   */
  fun login(
    email: String,
    password: String,
    onResult: () -> Unit
  ) {

    viewModelScope.launch {
      try {
        auth.signInWithEmailAndPassword(email, password)
          .addOnCompleteListener { task ->
            if (task.isSuccessful) {
              uiError = null
              val user = auth.currentUser
              Log.d("Login", "${user?.email}")
              onResult()
            } else {
              val code = (task.exception as? FirebaseAuthException)?.errorCode
              Log.d("LoginErrorCode", "🔥 Error code exacto: $code")
              uiError = when (code) {
                "ERROR_INVALID_CREDENTIAL" -> "El correo o la contraseña son incorrectos"
                "ERROR_INVALID_EMAIL" -> "El correo no es válido"
                "ERROR_WEAK_PASSWORD" -> "La contraseña debe tener al menos 6 caracteres"
                else -> "No se pudo iniciar sesión, intente de nuevo"
              }
              errorMessage = true
            }
          }
      } catch (e: Exception) {
        Log.d("Error en jetpack", "${e.message}")
        errorMessage = true
      }
    }
  }

  /**
   * Registra a un nuevo usuario en firebase
   * @param email Correo del usuario
   * @param password Contraseña del usuario
   * @param username Nombre de usuario
   * @param onResult Callback que se ejecuta al finalizar el registro
   * @return Unit
   * @usage AuthViewModel().register(email, password, username) {}
   */
  fun register(
    email: String,
    password: String,
    username: String,
    onResult: () -> Unit
  ) {
    viewModelScope.launch {
      try {
        auth.createUserWithEmailAndPassword(email, password)
          .addOnCompleteListener { task ->
            if (task.isSuccessful) {
              saveUser(username)
              onResult()
              val user = auth.currentUser
              Log.d("Regiser", "${user?.email}")
            } else {
              val code = (task.exception as? FirebaseAuthException)?.errorCode
              Log.d("LoginErrorCode", "🔥 Error code exacto: $code")
              uiError = when (code) {
                "ERROR_EMAIL_ALREADY_IN_USE" -> "El correo ya está en uso"
                "ERROR_INVALID_EMAIL" -> "El correo no es válido"
                else -> "No se pudo registrar el usuario, intente de nuevo"
              }
              errorMessage = true
            }
          }
      } catch (e: Exception) {
        Log.d("Error en jetpack", "${e.message}")
        errorMessage = true
      }
    }
  }

  /**
   * Guarda un usuario en la base de datos de firebase
   * @param username Nombre de usuario
   * @return Unit
   * @usage AuthViewModel().saveUser(username)
   */
  fun saveUser(username: String) {
    val id = auth.currentUser?.uid
    val email = auth.currentUser?.email
    val user = UserModel(
      id = id.toString(),
      userName = username,
      email = email.toString()
    )

    FirebaseFirestore.getInstance().collection("Users")
      .add(user)
      .addOnSuccessListener {
        Log.d("Success", "DocumentSnapshot added with ID: ${it.id}")
      }
      .addOnFailureListener { e ->
        Log.w("Error", "Error adding document", e)
        errorMessage = true
      }
  }

  /**
   * Cierra la sesión del usuario
   * @return Unit
   * @usage AuthViewModel().logout()
   */
  fun logout() {
    auth.signOut()
  }
}
