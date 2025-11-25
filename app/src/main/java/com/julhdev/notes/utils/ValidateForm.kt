package com.julhdev.notes.utils

import android.util.Patterns

/**
 * Valida si un campo no está vacío
 * @param value Valor a validar
 * @param field Campo a validar
 * @return String?
 */
fun validateNotNull(value: String?, field: String): String? =
  when {
    value.isNullOrBlank() -> when (field) {
      "username" -> "El campo 'Usuario' no puede estar vacío"
      "email" -> "El campo 'Email' no puede estar vacío"
      "password" -> "El campo 'Contraseña' no puede estar vacío."
      "confirmPassword" -> "El campo 'Confirmar contraseña' no puede estar vacío"
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
  return password.length <= 5
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


