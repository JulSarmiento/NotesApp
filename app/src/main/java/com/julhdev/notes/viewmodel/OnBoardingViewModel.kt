package com.julhdev.notes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julhdev.notes.data.repository.OnBoardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * OnBoardingViewModel es un ViewModel que gestiona el estado y las operaciones relacionadas con el onboarding de la aplicación.
 * Interactúa con el OnBoardingRepository para guardar y recuperar el estado de finalización del onboarding.
 * @property repository El OnBoardingRepository utilizado para las operaciones de datos.
 * @see OnBoardingRepository
 * @usage Inyectar OnBoardingViewModel en componentes de UI para observar y manipular el estado del onboarding.
 */
@HiltViewModel
class OnBoardingViewModel @Inject constructor(
  private val repository: OnBoardingRepository
): ViewModel() {

  private val _completed = repository.getBoarding
    .stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(100),
      null
    )
  val completed: StateFlow<Boolean?> = _completed

  /**
   * Guarda el estado de finalización del onboarding.
   * @param completed Un valor booleano que indica si el onboarding ha sido completado.
   * @usage Llamar a saveBoarding(completed) para almacenar el estado del onboarding.
   */
  fun saveBoarding(completed: Boolean) {
    viewModelScope.launch { repository.saveBoarding(completed) }
  }

}