package com.julhdev.notes.viewmodel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.julhdev.notes.data.repository.AuthRepository
import com.julhdev.notes.utils.ErrorMapper.map
import com.julhdev.notes.utils.resources.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * ViewModel para la autenticación de usuario
 * @param repository Repositorio para la autenticación de usuario
 * @usage Ejemplo de uso:
 * val authViewModel = AuthViewModel(authRepository)
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
  private val repository: AuthRepository,
) : ViewModel() {

  private val _state = MutableStateFlow<Resource<FirebaseUser?>>(Resource.Loading())
  val state = _state.asStateFlow()
  private val _isLoading = MutableStateFlow(false)
  val isLoading = _isLoading.asStateFlow()
  private val _uiError = MutableStateFlow("")
  var uiError = _uiError.asStateFlow()
  private val _isError = MutableStateFlow(false)
  var isError = _isError.asStateFlow()

  /**
   * Obtiene el usuario actual
   * @return FirebaseUser?
   * @usage AuthViewModel().getCurrentUser()
   */
  fun cleanError() {
    _isError.value = false
    _uiError.value = ""
  }

  /**
   * Obtiene el usuario actual
   * @return FirebaseUser?
   * @usage AuthViewModel().getCurrentUser()
   */
  fun currentUser() = repository.getCurrentUser()

  /**
   * Verifica si el usuario está autenticado.
   * @return True si el usuario está autenticado, de lo contrario false.
   * @usage Ejemplo de uso:
   * val isUserLogged = authRepository.isUserLogged()
   */
  fun isUserLogged(): Boolean {
    return repository.getCurrentUser() != null
  }

  /**
   * Cierra la sesión del usuario
   * @return Unit
   * @usage AuthViewModel().logout()
   */
  fun logout() {
    repository.logout()
  }

  /**
   * Actualiza la contraseña de un usuario
   * @param email
   * @param onResult Callback que se ejecuta al finalizar la operación
   * @return Unit
   * @usage AuthViewModel().updatePassword(newPassword, actionCode)
   */
  fun sendEmailToResetPassword(email: String, onResult: () -> Unit) {
    viewModelScope.launch(Dispatchers.IO) {
      withContext(Dispatchers.Main) {
        _isLoading.value = true
      }
      try {
        repository.resetPassword(email)
        withContext(Dispatchers.Main) {
          _isLoading.value = false
          onResult()
        }
      } catch (e: Exception) {
        withContext(Dispatchers.Main) {

          Log.d("TAG", "sendEmailToResetPassword: ${e.message}")
        }
          _isError.value = true
          _uiError.value = e.message.toString()
          _isLoading.value = false
        }
    }
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
    viewModelScope.launch(Dispatchers.IO) {
      _isLoading.value = true
      when (val result = repository.login(email, password)) {
        is Resource.Success -> {
          _state.value = result
          withContext(Dispatchers.Main.immediate) {
            onResult()
          }
          _isLoading.value = false
        }

        is Resource.Error -> {
          _state.value = result
          _isError.value = true
          _uiError.value = map(result.message)
          _isLoading.value = false
        }

        is Resource.Loading -> {
          /*TODO*/
          _isLoading.value = false
        }
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

    viewModelScope.launch(Dispatchers.IO) {
      _isLoading.value = true
      when (val result = repository.register(email, password, username)) {
        is Resource.Success -> {
          _state.value = result
          withContext(Dispatchers.Main.immediate) {
            onResult()
          }
          _isLoading.value = false
        }

        is Resource.Error -> {
          _state.value = result
          _isError.value = true
          _uiError.value = map(result.message)
          _isLoading.value = false
        }

        is Resource.Loading -> {
          /*TODO*/
          _isLoading.value = false
        }
      }
    }
  }
}






