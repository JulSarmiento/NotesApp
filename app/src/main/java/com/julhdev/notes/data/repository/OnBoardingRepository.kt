package com.julhdev.notes.data.repository

import com.julhdev.notes.data.dataStore.StoreBoarding
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Repositorio para manejar las operaciones relacionadas con el estado del onboarding.
 * Proporciona métodos para guardar y recuperar el estado del onboarding utilizando StoreBoarding.
 * @param storeBoarding La instancia de StoreBoarding utilizada para las operaciones de datos.
 * @usage Inyectar OnBoardingRepository en ViewModels o casos de uso para acceder a las operaciones de onboarding.
 */
class OnBoardingRepository @Inject constructor(
  private val storeBoarding: StoreBoarding
) {

  val getBoarding: Flow<Boolean> = storeBoarding.getBoarding

  /**
   * Guarda el estado de finalización del onboarding.
   * @param completed Un valor booleano que indica si el onboarding ha sido completado.
   * @usage onBoardingRepository.saveBoarding(completed)
   */
  suspend fun saveBoarding(completed: Boolean) {
    storeBoarding.saveBoarding(completed = completed)
  }
}