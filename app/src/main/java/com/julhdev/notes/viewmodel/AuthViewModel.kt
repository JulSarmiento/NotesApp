package com.julhdev.notes.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

  private val auth: FirebaseAuth = Firebase.auth

  fun login(
    email: String,
    password: String,
    onResult: () -> Unit
  ) {

    viewModelScope.launch {
      try {
        auth.signInWithEmailAndPassword(email, password)
          .addOnCompleteListener { task ->
            if (task.isSuccessful) {
              onResult()
            } else {
              Log.d("Error en firebase", "${task.exception?.message}")
            }
          }
      } catch (e: Exception) {
        Log.d("Error en jetpack", "${e.message}")
      }
    }
  }
}
