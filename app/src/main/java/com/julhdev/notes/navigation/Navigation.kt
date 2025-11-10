package com.julhdev.notes.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.julhdev.notes.viewmodel.FormViewModel
import com.julhdev.notes.viewmodel.NoteViewModel
import com.julhdev.notes.viewmodel.OnBoardingViewModel
import com.julhdev.notes.viewmodel.ThemeViewModel
import com.julhdev.notes.views.AddView
import com.julhdev.notes.views.EditView
import com.julhdev.notes.views.HomeView
import com.julhdev.notes.views.LoginView
import com.julhdev.notes.views.OnBoardingView
import com.julhdev.notes.views.RegisterView
import com.julhdev.notes.views.SplashView


/**
 * Administra la navegación entre las diferentes vistas de la aplicación utilizando NavController y NavHost.
 * Define las rutas de navegación y los parámetros necesarios para cada vista.
 * @usage Incluir este Composable en el punto de entrada de la aplicación para habilitar la navegación.
 */
@Composable
fun NavManager(
  onBoardingViewModel: OnBoardingViewModel,
  noteViewModel: NoteViewModel,
  themeViewModel: ThemeViewModel,
  formViewModel: FormViewModel
) {

  val isOnBoardingCompleted = onBoardingViewModel.completed.collectAsState()
  val navController = rememberNavController()

  NavHost(
    navController = navController,
    startDestination = Routes.SPLASH
  )
  {
    composable(Routes.SPLASH) {
      SplashView(navController, isOnBoardingCompleted.value == true)
    }
    composable(Routes.ONBOARDING) {
      OnBoardingView(navController, onBoardingViewModel)
    }
    composable(Routes.LOGIN){
      LoginView(navController)
    }
    composable(Routes.REGISTER) {
      RegisterView(navController)
    }
    composable(Routes.HOME) {
      HomeView(navController, noteViewModel, themeViewModel)
    }
    composable(Routes.ADD) {
      AddView(navController, themeViewModel, formViewModel)
    }
    composable("${Routes.EDIT}/{id}", arguments = listOf(navArgument("id") { type = NavType.IntType })) {
      val id = it.arguments?.getInt("id") ?: -1
      EditView(id, navController, formViewModel, themeViewModel)
    }
  }
}