package com.julhdev.notes.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.julhdev.notes.R
import com.julhdev.notes.components.MainImage
import com.julhdev.notes.navigation.Routes
import com.julhdev.notes.viewmodel.SplashViewModel
import kotlinx.coroutines.delay

/**
 * SplashView Composable
 * @param navController de tipo NavController para la navegación entre pantallas
 * @param store de tipo Boolean que indica si el usuario ya ha completado el onboarding
 * @usage SplashView(navController = navController, store = store)
 */
@Composable
fun SplashView(
  navController: NavController,
  store: Boolean,
  splashViewModel: SplashViewModel
) {
  val destination = if (store) Routes.LOGIN else Routes.ONBOARDING

  var animationFinished by remember { mutableStateOf(false) }

  LaunchedEffect(Unit) {
    delay(2000)
    animationFinished = true
  }

  LaunchedEffect(splashViewModel.ready, animationFinished) {
    if (splashViewModel.ready && animationFinished) {
      navController.navigate(destination) {
        popUpTo(Routes.SPLASH) { inclusive = true }
      }
    }
  }

  Scaffold { innerPadding ->
    Box(
      contentAlignment = Alignment.Center,
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      MainImage(image = R.raw.loading)
    }
  }
}