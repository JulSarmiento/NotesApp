package com.julhdev.notes.data.model

/**
 * UserModel data class
 * @property id String
 * @property userName String
 * @property email String
 * @usage UserModel(userId, userName, email)
 */
data class UserModel(
  val id: String?,
  val userName: String,
  val email: String?
) {

  /**
   * Funcion que mapea el modelo de datos a un mapa
   * @return MutableMap<String, String?>
   */
  fun toMap(): MutableMap<String, String?> {
    return mutableMapOf(
      "id" to this.id,
      "userName" to this.userName,
      "email" to this.email
    )
  }
}
