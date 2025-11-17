package com.julhdev.notes.data.core

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestoreException

/**
 * Convierte un [Throwable] a un [AppError]
 * @return [AppError]
 */
fun Throwable.AppError(): AppError = when (this) {
  is FirebaseNetworkException -> AppError.NetworkError
  is FirebaseTooManyRequestsException -> AppError.Ratelimit

  is FirebaseAuthException -> when (this.errorCode) {
    "ERROR_INVALID_CREDENTIAL" -> AppError.Auth.InvalidCredentials
    "ERROR_INVALID_EMAIL" -> AppError.Auth.InvalidEmail
    "ERROR_WEAK_PASSWORD" -> AppError.Auth.WeakPassword
    "ERROR_USER_DISABLED" -> AppError.Auth.UserDisabled
    "ERROR_USER_NOT_FOUND" -> AppError.Auth.UserNotFound
    "ERROR_EMAIL_ALREADY_IN_USE" -> AppError.Auth.EmailAlreadyInUse
    else -> AppError.Unknown(message)
  }

  is FirebaseFirestoreException -> when (this.code) {
    FirebaseFirestoreException.Code.PERMISSION_DENIED -> AppError.Firestore.PermissionDenied
    FirebaseFirestoreException.Code.NOT_FOUND -> AppError.Firestore.NotFound
    FirebaseFirestoreException.Code.UNAVAILABLE -> AppError.Firestore.Unavailable
    FirebaseFirestoreException.Code.ABORTED -> AppError.Firestore.Aborted
    else -> AppError.Unknown(message)
  }

  else -> AppError.Unknown(message)
}