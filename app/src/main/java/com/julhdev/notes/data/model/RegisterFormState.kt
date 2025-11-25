package com.julhdev.notes.data.model

/**
 * Clase que representa el estado del formulario de registro
 * @property username Nombre de usuario
 * @property email Correo electrónico del usuario
 * @property password Contraseña del usuario
 * @property confirmPassword Confirmación de contraseña
 * @property usernameError Mensaje de error para el campo de nombre de usuario
 * @property emailError Mensaje de error para el campo de correo electrónico
 * @property passwordError Mensaje de error para el campo de contraseña
 * @property confirmPasswordError Mensaje de error para el campo de confirmación de contraseña
 * @usage RegisterFormState(
 *   username = "",
 *   email = "",
 *   password = "",
 *   confirmPassword = "",
 *   usernameError = "",
 *   emailError = "",
 *   passwordError = "",
 *   confirmPasswordError = ""
 * )
 */
data class RegisterFormState(
  val username: String = "",
  val email: String = "",
  val password: String = "",
  val confirmPassword: String = "",
  val usernameError: String? = null,
  val emailError: String? = null,
  val passwordError: String? = null,
  val confirmPasswordError: String? = null
)
