package com.julhdev.notes.utils.resources

/**
 * Clase generica para manejar los estados de una respuesta de red.
 * @param T Tipo de dato que se maneja en la respuesta.
 * - Success: Indica que la respuesta fue exitosa y contiene los datos.
 * - Loading: Indica que la respuesta está en proceso de carga.
 * - Failure: Indica que la respuesta falló y contiene un mensaje de error y opcionalmente datos parciales.
 * @usage Utilizar esta class para manejar estados de respuestas en repositorios o view models.
 */
sealed class Resource<T> {
  class Success<T>(val data: T): Resource<T>()
  class Error<T>(val message: String?, val data: T? = null): Resource<T>()
  class Loading<T>: Resource<T>()
}