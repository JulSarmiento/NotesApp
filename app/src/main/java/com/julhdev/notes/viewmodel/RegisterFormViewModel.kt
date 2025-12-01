package com.julhdev.notes.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.julhdev.notes.data.model.RegisterFormState
import com.julhdev.notes.utils.validateConfirmedPassword
import com.julhdev.notes.utils.validateEmail
import com.julhdev.notes.utils.validateNotNull
import com.julhdev.notes.utils.validatePasswordFormat
import com.julhdev.notes.utils.validateUsername

/**
 * ViewModel para el formulario de registro de usuarios
 * @usage RegisterFormViewModel()
 */
class RegisterFormViewModel: ViewModel() {
  var state by mutableStateOf(value = RegisterFormState())
    private set

  /**
   * Limpia el estado del formulario de registro
   * @usage RegisterFormViewModel().cleanState()
   */
  fun cleanState() {
    state = state.copy(
      username = "",
      email = "",
      password = "",
      confirmPassword = "",
      usernameError = null,
      emailError = null,
      passwordError = null,
      confirmPasswordError = null
    )
  }

  /**
   * Valida y envía los datos del formulario de registro
   * @param username Nombre de usuario
   * @param email Correo electrónico
   * @param password Contraseña
   * @param confirmPassword Confirmación de contraseña
   * @param onSuccess Callback que se ejecuta si el formulario es válido
   * @usage RegisterFormViewModel().validateAndSubmit(username, email, password, confirmPassword) { username, email, password ->
   *   // Acciones a realizar si el formulario es válido
   *   // Puedes enviar los datos al servidor o realizar otras acciones aquí
   *   // Luego, puedes navegar a otra pantalla o realizar cualquier otra acción necesaria
   * }
   */
  fun validateAndSubmit(username: String, email: String, password: String, confirmPassword: String, onSuccess: (String, String, String) -> Unit) {
    val userErr = validateNotNull(username, "username")
      ?: if (!validateUsername(username)) "Usuario muy corto (min 4)" else null

    val emailErr = validateNotNull(email, "email")
      ?: if (!validateEmail(email)) "Email inválido" else null

    val passErr = validateNotNull(password, "password")
      ?: if (validatePasswordFormat(password)) "Mínimo 6 caracteres" else null

    val confirmErr = validateNotNull(confirmPassword, "confirmPassword")
      ?: if (!validateConfirmedPassword(password, confirmPassword)) "Las contraseñas no coinciden" else null

    state = state.copy(
      usernameError = userErr,
      emailError = emailErr,
      passwordError = passErr,
      confirmPasswordError = confirmErr
    )

    if (userErr == null && emailErr == null && passErr == null && confirmErr == null) {
      onSuccess(username, email, password)
    }
    cleanState()

  }
}
