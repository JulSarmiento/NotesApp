package com.julhdev.notes.data.model

/**
 * UserModel data class
 * @property userId String
 * @property title String
 * @property content String
 * @property timestamp Long
 * @usage UserModel(userId, userName, email)
 */
data class NoteModel(
  val userId: String?,
  val title: String,
  val content: String,
  val timestamp: String
){
  /**
   * Funcion que mapea el modelo de datos a un mapa
   * @return MutableMap<String, String?>
   */
  fun toMap(): MutableMap<String, String?> {
    return mutableMapOf(
      "userId" to this.userId,
      "title" to this.title,
      "content" to this.content,
      "timestamp" to this.timestamp
    )
  }
}
