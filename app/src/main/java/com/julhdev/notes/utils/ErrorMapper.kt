package com.julhdev.notes.utils

/**
 * Normaliza y mapea un código de error de Firebase a un mensaje en español.
 * Acepta formatos como "auth/invalid-email", "ERROR_INVALID_EMAIL", "invalid-email", etc.
 * @return Un mensaje en español que describe el error.
 */
object ErrorMapper {
  private const val DEFAULT_MSG = "Ocurrió un error desconocido."
  fun map(errorCode: String?): String {
    return when (errorCode) {
      "ERROR_EMAIL_ALREADY_IN_USE" -> "El correo electrónico ya está en uso."
      "ERROR_INVALID_CREDENTIAL" -> "Correo electrónico o contraseña incorrectos."
      "INVALID_EMAIL", "EMAIL_INVALID" -> "El correo electrónico no es válido."
      "USER_DISABLED" -> "La cuenta de usuario está desactivada."
      "USER_NOT_FOUND" -> "No existe una cuenta con ese correo electrónico."
      "WRONG_PASSWORD", "INVALID_PASSWORD" -> "La contraseña es incorrecta."
      "EMAIL_ALREADY_IN_USE", "ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> "El correo electrónico ya está en uso."
      "WEAK_PASSWORD", "WEAK_PASSWORD_PASSWORD" -> "La contraseña es demasiado débil (mínimo 6 caracteres)."
      "TOO_MANY_REQUESTS" -> "Demasiados intentos. Intenta nuevamente más tarde."
      "OPERATION_NOT_ALLOWED" -> "Operación no permitida. Habilita este método en Firebase."
      "INVALID_CUSTOM_TOKEN" -> "Token personalizado no válido."
      "CREDENTIAL_ALREADY_IN_USE" -> "Las credenciales ya están asociadas a otra cuenta."
      "NETWORK_REQUEST_FAILED" -> "Error de red. Revisa tu conexión a Internet."
      "INTERNAL_ERROR" -> "Error interno del servidor. Intenta más tarde."
      "INVALID_API_KEY" -> "Clave de API inválida."
      "EXPIRED_ACTION_CODE", "INVALID_ACTION_CODE" -> "El código de acción es inválido o ha expirado."
      "REQUIRES_RECENT_LOGIN" -> "Se requiere haber iniciado sesión recientemente para realizar esta acción."
      else -> DEFAULT_MSG
    }
  }
}
