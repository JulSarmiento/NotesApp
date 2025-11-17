package com.julhdev.notes.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julhdev.notes.data.core.AppError
import com.julhdev.notes.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
  private val repository: AuthRepository
) : ViewModel() {

  var uiError by mutableStateOf<String?>(null)
    private set
  var isLoading by mutableStateOf(false)
  var isLogged by mutableStateOf(false)
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
      isLoading = true
      val result = repository.login(email, password)
      isLoading = false
      result.onSuccess {
        isLogged = true
        uiError = null
        onResult()
      }.onFailure { ex ->
        val appError = ex.AppError()
        println("Error en login: ${appError.userMessage}")
        uiError = appError.userMessage
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
      isLoading = true
      val result = repository.register(email, password, username)
      isLoading = false
      result.onSuccess {
        isLogged = true
        uiError = null
        onResult()
      }.onFailure { ex ->
        val appError = ex.AppError()
        println("Error en login: ${appError.userMessage}")
        uiError = appError.userMessage
        errorMessage = true
      }
    }
  }
}


//
//  /**
//   * Guarda un usuario en la base de datos de firebase
//   * @param username Nombre de usuario
//   * @return Unit
//   * @usage AuthViewModel().saveUser(username)
//   */
//  fun saveUser(username: String) {
//    val id = auth.currentUser?.uid
//    val email = auth.currentUser?.email
//    val user = UserModel(
//      id = id.toString(),
//      userName = username,
//      email = email.toString()
//    )
//
//    FirebaseFirestore.getInstance().collection("Users")
//      .add(user)
//      .addOnSuccessListener {
//        Log.d("Success", "DocumentSnapshot added with ID: ${it.id}")
//      }
//      .addOnFailureListener { e ->
//        Log.w("Error", "Error adding document", e)
//        errorMessage = true
//      }
//  }
//
//  /**
//   * Cierra la sesión del usuario
//   * @return Unit
//   * @usage AuthViewModel().logout()
//   */
//  fun logout() {
//    auth.signOut()
//  }

