package com.julhdev.notes.data.core

/**
 * Clase sellada para representar errores en la aplicación.
 * Esta clase puede tener subclases para representar diferentes tipos de errores.
 * @property userMessage Mensaje que se mostrará al usuario.
 * @see AppError.NetworkError
 * @see AppError.Ratelimit
 * @see AppError.Auth
 */
sealed class AppError( open var userMessage: String ) {
  object NetworkError : AppError("Sin conexión. Intenta de nuevo.")
  object Ratelimit: AppError("Demasiados intentos. Intenta más tarde.")

  /**
   * Clase sellada para representar errores de autenticación en la aplicación.
   * Esta clase puede tener subclases para representar diferentes tipos de errores de autenticación.
   * @property userMessage Mensaje que se mostrará al usuario.
   * @see Auth.InvalidCredentials
   * @see Auth.InvalidEmail
   */
  sealed class Auth(userMessage: String) : AppError(userMessage) {
    object InvalidCredentials : Auth("El correo o la contraseña son incorrectos")
    object InvalidEmail : Auth("El correo no es válido")
    object WeakPassword : Auth("La contraseña debe tener al menos 6 caracteres")
    object UserDisabled : Auth("La cuenta está deshabilitada")
    object UserNotFound : Auth("Usuario no encontrado")
    object EmailAlreadyInUse : Auth("El correo ya está en uso")

  }

  /**
   * Clase sellada para representar errores de FireStore en la aplicación.
   * Esta clase puede tener subclases para representar diferentes tipos de errores de FireStore.
   * @property userMessage Mensaje que se mostrará al usuario.
   * @see Firestore.PermissionDenied
   * @see Firestore.NotFound
   * @see Firestore.Unavailable
   * @see Firestore.Aborted
   */
  sealed class Firestore(userMessage: String) : AppError(userMessage) {
    object PermissionDenied : Firestore("No tienes permisos para esta acción")
    object NotFound : Firestore("Recurso no encontrado")
    object Unavailable : Firestore("Servicio temporalmente no disponible")
    object Aborted : Firestore("Operación cancelada, intenta de nuevo")
  }

  /**
   * Esta clase puede tener subclases para representar diferentes tipos de errores desconocidos.
   * @property userMessage Mensaje que se mostrará al usuario.
   * @see Unknown.Unknown
   */
  data class Unknown(val cause: String?) : AppError("Ocurrió un error inesperado")

}