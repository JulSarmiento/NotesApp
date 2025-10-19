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
 * StoreBroading es una clase que gestiona el almacenamiento del estado de finalización del onboarding utilizando DataStore.
 * Proporciona métodos para guardar y recuperar el estado del onboarding.
 * @param context El contexto de la aplicación utilizado para acceder a DataStore.
 * @usage val storeBoarding = StoreBoarding(context)
 * @return Una instancia de StoreBoarding para gestionar el estado del onboarding.
 */
class StoreBoarding(
  private val context: Context
) {

  companion object {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("storeBoarding")
    val STORE_BOARD = booleanPreferencesKey("store_board")
  }

  val getBoarding: Flow<Boolean> = context.dataStore.data
    .map { preferences -> preferences[STORE_BOARD] ?: false }

  /**
   * Guarda el estado de finalización del onboarding en DataStore.
   * @param completed Un valor booleano que indica si el onboarding ha sido completado.
   */
  suspend fun saveBoarding(completed: Boolean) {
    context.dataStore.edit {
      preferences -> preferences[STORE_BOARD] = completed
    }
  }
}