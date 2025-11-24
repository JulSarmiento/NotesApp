package com.julhdev.notes.utils

import android.util.Patterns


fun validateNotNull(value: String?, field: String): String? =
  when {
    value.isNullOrBlank() -> when (field) {
      "username" -> "Usuario no puede estar vacío"
      "email" -> "Email no puede estar vacío"
      "password" -> "Password no puede estar vacío"
      "confirmPassword" -> "La confirmacion de la contraseña no puede estar vacío"
      else -> null
    }
    else -> null
  }

/**
 * Valida el nombre de usuario
 * @param username Nombre de usuario a validar
 * @return Boolean
 */
fun validateUsername(
  username: String
): Boolean {
  return username.length >= 4
}


/**
 * Valida el formato de una contraseña
 * @param password Contraseña a validar
 * @return Boolean
 */
fun validatePasswordFormat(
  password: String
): Boolean {
  return password.length < 6
}

/**
 * Valida si la contraseña y la confirmación de contraseña son iguales
 * @param password Contraseña
 * @param confirmPassword Confirmación de contraseña
 * @return Boolean
 */
fun validateConfirmedPassword(
  password: String,
  confirmPassword: String
): Boolean {
  return password == confirmPassword && password.isNotEmpty()
}

/**
 * Valida si el correo electrónico contiene el carácter '@'
 * @param email Correo electrónico
 * @return Boolean
 */
fun validateEmail(
  email: String
): Boolean {
  return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}


