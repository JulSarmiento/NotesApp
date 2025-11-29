package com.julhdev.notes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.rememberNavController
import com.julhdev.notes.navigation.NavManager
import com.julhdev.notes.ui.theme.NotesTheme
import com.julhdev.notes.viewmodel.AuthViewModel
import com.julhdev.notes.viewmodel.FormViewModel
import com.julhdev.notes.viewmodel.LoginFormViewModel
import com.julhdev.notes.viewmodel.NoteViewModel
import com.julhdev.notes.viewmodel.OnBoardingViewModel
import com.julhdev.notes.viewmodel.RegisterFormViewModel
import com.julhdev.notes.viewmodel.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.julhdev.notes.navigation.Routes

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val onBoardingViewModel: OnBoardingViewModel by viewModels()
    val noteViewModel: NoteViewModel by viewModels()
    val themeViewModel: ThemeViewModel by viewModels()
    val formViewModel: FormViewModel by viewModels()
    val authViewModel: AuthViewModel by viewModels()
    val loginFormViewModel: LoginFormViewModel by viewModels()
    val registerFormViewModel: RegisterFormViewModel by viewModels()

    val data: Uri? = intent?.data
    var initialDeepLink: String? = null
    if (data != null && data.toString().contains("/__/auth/links")) {
      if (intent?.action == Intent.ACTION_VIEW) {
        initialDeepLink = intent.dataString
      }
    }
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
          loginFormViewModel,
          registerFormViewModel,
          initialDeepLink
        )
      }
    }
  }
}

