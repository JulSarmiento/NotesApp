package com.julhdev.notes.data.model

/**
 * UserModel data class
 * @property id String
 * @property userName String
 * @property email String
 * @usage UserModel(userId, userName, email)
 */
data class UserModel(
  val id: String,
  val userName: String,
  val email: String
) {
  fun toMap(): Map<String, Any> {
    return mapOf(
      "id" to id,
      "userName" to userName,
      "email" to email
    )
  }
}
