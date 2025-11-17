package com.julhdev.notes.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julhdev.notes.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

/**
 * SplashViewModel permite que firebase haga una precarga
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
  private val authRepository: AuthRepository
) : ViewModel() {

  var ready by mutableStateOf(false)
    private set

  var warmUpError by mutableStateOf<String?>(null)
    private set

  init {
    viewModelScope.launch {
      try {
        withTimeout(10_000) {
          val result = authRepository.warmUpAuth()
          if (result.isFailure) {
            Log.w("SplashVM", "warmUpAuth failed", result.exceptionOrNull())
            warmUpError = result.exceptionOrNull()?.message
          }
        }
      } catch (t: TimeoutCancellationException) {
        Log.w("SplashVM", "warmUpAuth timed out", t)
        warmUpError = "Timeout inicialización"
      } catch (e: Exception) {
        Log.w("SplashVM", "warmUpAuth error", e)
        warmUpError = e.message
      } finally {
        ready = true
      }
    }
  }
}