package com.julhdev.notes.data.model

/**
 * Clase que representa el estado del formulario de inicio de sesión
 * @property email Correo electrónico del usuario
 * @property password Contraseña del usuario
 * @property emailError Mensaje de error para el campo de correo electrónico
 * @property passwordError Mensaje de error para el campo de contraseña
 * @usage LoginFormState(
 *   email = "",
 *   password = "",
 *   emailError = "",
 *   passwordError = ""
 * )
 */
data class LoginFormState(
  val email: String = "",
  val password: String = "",
  val emailError: String? = null,
  val passwordError: String? = null,
)
