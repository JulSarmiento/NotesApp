package com.julhdev.notes.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.julhdev.notes.data.model.LoginFormState
import com.julhdev.notes.utils.validateEmail
import com.julhdev.notes.utils.validateNotNull
import com.julhdev.notes.utils.validatePasswordFormat

/**
 * ViewModel para el formulario de inicio de sesión
 * @usage LoginFormViewModel()
 */
class LoginFormViewModel: ViewModel() {

  var state by mutableStateOf(LoginFormState())
  private set

  /**
   * Limpia el estado del formulario de inicio de sesión
   * @usage LoginFormViewModel().cleanState()
   */
  fun cleanState() {
    state.copy(
      email = "",
      password = "",
      emailError = null,
      passwordError = null
    )
  }

  /**
   * Valida el formulario de inicio de sesión
   * @param email Correo electrónico
   * @param password Contraseña
   * @param onSuccess Callback que se ejecuta si el formulario es válido
   * @usage LoginFormViewModel().validateLoginForm(email, password) { email, password ->
   *   // Acciones a realizar si el formulario es válido
   * }
   */
  fun validateLoginForm(email: String, password: String, onSuccess: (String, String) -> Unit) {
    val emailValidationMsg = validateNotNull(email, "email")
      ?: if (!validateEmail(email)) "Formato de email inválido" else null

    val passwordValidationMsg = validateNotNull(password, "password")
      ?: if (validatePasswordFormat(password)) "Contraseña muy corta" else null

    state = state.copy(
      email = email,
      password = password,
      emailError = emailValidationMsg,
      passwordError = passwordValidationMsg
    )

    cleanState()

    if (emailValidationMsg == null && passwordValidationMsg == null) {
      onSuccess(email, password)
    }
  }
}