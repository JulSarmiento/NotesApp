package com.julhdev.notes.data.repository

import com.julhdev.notes.data.dataStore.StoreTheme
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Repositorio para manejar las operaciones relacionadas con el estado del tema oscuro.
 * Proporciona métodos para guardar y recuperar el estado del tema oscuro utilizando StoreTheme.
 * @param isDarkMode La instancia de StoreTheme utilizada para las operaciones de datos.
 * @usage Inyectar ThemeRepository en ViewModels o casos de uso para acceder a las operaciones de tema.
 */
class ThemeRepository @Inject constructor(
  private val isDarkMode: StoreTheme
){
  val isDark: Flow<Boolean> = isDarkMode.getIsDark

  /**
   * Guarda el estado del tema oscuro.
   * @param isDark Un valor booleano que indica si el tema oscuro está activado.
   * @usage themeRepository.saveIsDark(isDark)
   */
  suspend fun saveIsDark(isDark: Boolean) {
    isDarkMode.saveIsDark(isDark = isDark)
  }
}