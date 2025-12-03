package com.julhdev.notes.data.model

/**
 * FormState data class
 * Representa el estado de un formulario de notas, incluyendo título, contenido, marca de tiempo y errores de validación.
 * @property title Título de la nota
 * @property content Contenido de la nota
 * @property timeStamp Marca de tiempo asociada a la nota
 * @property titleError Error relacionado con el título, si existe
 * @property contentError Indica si hay un error en el contenido
 * @property isValid Indica si el formulario es válido (sin errores y campos no vacíos)
 */
data class FormState(
  val noteId: String? = null,
  val title: String = "",
  val content: String = "",
  val timeStamp: Long = 0L,
  val titleError: String? = null,
  val contentError: String? = null,
  val isSubmitting: Boolean = false,
) {

  /**
   * Indica si el formulario es válido.
   * Un formulario es válido si no hay errores en el título o contenido, y ambos campos no están vacíos.
   */
  val isValid: Boolean
    get() = titleError == null && contentError == null && title.isNotBlank() && content.isNotBlank() && title.length <= 80
}