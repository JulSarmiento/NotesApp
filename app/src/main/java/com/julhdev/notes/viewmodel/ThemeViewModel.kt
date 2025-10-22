package com.julhdev.notes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julhdev.notes.data.repository.ThemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ThemeViewModel es un ViewModel que gestiona el estado y las operaciones relacionadas con el tema oscuro de la aplicación.
 * Interactúa con el ThemeRepository para guardar y recuperar el estado del tema oscuro.
 * @property repository El ThemeRepository utilizado para las operaciones de datos.
 * @see ThemeRepository
 * @usage Inyectar ThemeViewModel en componentes de UI para observar y manipular el estado del tema oscuro.
 */
@HiltViewModel
class ThemeViewModel @Inject constructor(
  private val repository: ThemeRepository
): ViewModel() {

  private val _isDark = repository.isDark
    .stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(1000),
      false
    )

  val isDark: StateFlow<Boolean> = _isDark

  /**
   * Guarda el estado del tema oscuro.
   * @param isDark Un valor booleano que indica si el tema oscuro está activado.
   * @usage Llamar a saveIsDark(isDark) para almacenar el estado del tema oscuro.
   */
  fun saveIsDark(isDark: Boolean) {
    viewModelScope.launch {
      repository.saveIsDark(isDark)
    }
  }
}