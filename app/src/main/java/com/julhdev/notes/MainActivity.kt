package com.julhdev.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.julhdev.notes.navigation.NavManager
import com.julhdev.notes.ui.theme.NotesTheme
import com.julhdev.notes.viewmodel.AuthViewModel
import com.julhdev.notes.viewmodel.FormViewModel
import com.julhdev.notes.viewmodel.NoteViewModel
import com.julhdev.notes.viewmodel.OnBoardingViewModel
import com.julhdev.notes.viewmodel.SplashViewModel
import com.julhdev.notes.viewmodel.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val onBoardingViewModel: OnBoardingViewModel by viewModels()
    val noteViewModel: NoteViewModel by viewModels()
    val themeViewModel: ThemeViewModel by viewModels()
    val formViewModel: FormViewModel by viewModels()
    val authViewModel: AuthViewModel by viewModels()
    val splashViewModel: SplashViewModel by viewModels()
    enableEdgeToEdge()
    setContent {
      NotesTheme(
        darkTheme = themeViewModel.isDark.collectAsState().value
      ) {
        NavManager(
          onBoardingViewModel,
          noteViewModel,
          themeViewModel,
          formViewModel,
          authViewModel,
          splashViewModel
        )
      }
    }
  }
}

