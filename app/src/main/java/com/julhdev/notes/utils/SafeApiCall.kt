package com.julhdev.notes.utils

import com.google.firebase.auth.FirebaseAuthException
import com.julhdev.notes.utils.resources.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * Función de orden superior para manejar llamadas a API de forma segura.
 * Envuelve la llamada a la API y captura excepciones comunes, devolviendo un Resource adecuado.
 *
 * @param apiCall La llamada a la API que se va a ejecutar.
 * @return Un objeto Resource que representa el resultado de la llamada a la API.
 * @usage Utilizar esta función para envolver llamadas a API en repositorios o view models.
 */
suspend fun <T> safeApiCall(
  apiCall: suspend () -> T
): Resource<T> {
  return withContext(Dispatchers.IO) {
    try {
      val result = apiCall()
      Resource.Success(result)
    } catch (e: IOException) {
      Resource.Error("Error de red: ${e.localizedMessage}")
    } catch (e: FirebaseAuthException) {
      Resource.Error(e.errorCode)
    } catch (e: Exception) {
      Resource.Error("Error inesperado: ${e.localizedMessage}")
    }
  }
}