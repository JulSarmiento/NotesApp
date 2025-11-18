package com.julhdev.notes.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.julhdev.notes.data.core.AppError
import com.julhdev.notes.data.repository.AuthRepository
import com.julhdev.notes.data.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
  private val repository: AuthRepository,
  private val usersRepository: UsersRepository,
  private val auth: FirebaseAuth
) : ViewModel() {

  var uiError by mutableStateOf<String?>(null)
    private set
  var isLoading by mutableStateOf(false)
  var isLogged by mutableStateOf(false)
    private set
  var errorMessage: Boolean by mutableStateOf(false)

  /**
   * Cierra la sesión del usuario
   * @return Unit
   * @usage AuthViewModel().logout()
   */
  fun logout() {
    repository.logout()
  }

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
      if (result.isSuccess) {
        val user = result.getOrNull() ?: auth.currentUser
        try {
          usersRepository.saveUser(username, user)
          isLogged = true
          uiError = null
          onResult()
        } catch (ex: Exception) {
          val appError = ex.AppError()
          uiError = appError.userMessage
          errorMessage = true
        }
      } else {
        val ex = result.exceptionOrNull()!!
        val appError = ex.AppError()
        uiError = appError.userMessage
        errorMessage = true
      }
      isLoading = false
    }
  }
}





