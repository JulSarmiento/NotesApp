package com.julhdev.notes.viewmodel

import android.util.Log
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

@HiltViewModel
class AuthViewModel @Inject constructor(
  private val repository: AuthRepository,
) : ViewModel() {

  private val _state = MutableStateFlow<Resource<FirebaseUser?>>(Resource.Loading())
  val state = _state.asStateFlow()

  private val _isLoading = MutableStateFlow(false)
  val isLoading = _isLoading.asStateFlow()

  var isError = false

  var uiError: String = ""

  /**
   * Obtiene el usuario actual
   * @return FirebaseUser?
   * @usage AuthViewModel().getCurrentUser()
   */
  fun cleanError() {
    isError = false
    uiError = ""
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
          isError = true
          uiError = map(result.message)
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
      when ( val result = repository.register(email, password, username)) {
        is Resource.Success -> {
          _state.value = result
          withContext(Dispatchers.Main.immediate) {
            onResult()
          }
          _isLoading.value = false
        }
        is Resource.Error -> {
          _state.value = result
          isError = true
          uiError = map(result.message)
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






