package com.julhdev.notes.data.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * StoreTheme es una clase que gestiona el almacenamiento del estado del tema oscuro utilizando DataStore.
 * Proporciona métodos para guardar y recuperar el estado del tema oscuro.
 * @param context El contexto de la aplicación utilizado para acceder a DataStore.
 * @usage val storeTheme = StoreTheme(context)
 * @return Una instancia de StoreTheme para gestionar el estado del tema oscuro.
 */
class StoreTheme(
  private val context: Context
) {

  companion object {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("storeTheme")
    val STORE_THEME = booleanPreferencesKey("dark_mode")
  }

  val getIsDark: Flow<Boolean> = context.dataStore.data
    .map {
      preferences -> preferences[STORE_THEME] ?: false
    }

  /**
   * Guarda el estado del tema oscuro en DataStore.
   * @param isDark Un valor booleano que indica si el tema oscuro está activado.
   */
  suspend fun saveIsDark(isDark: Boolean) {
    context.dataStore.edit { preferences ->
      preferences[STORE_THEME] = isDark
    }
  }
}