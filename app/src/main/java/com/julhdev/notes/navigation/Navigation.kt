package com.julhdev.notes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.julhdev.notes.views.AddView
import com.julhdev.notes.views.EditView
import com.julhdev.notes.views.HomeView


/**
 * Administra la navegación entre las diferentes vistas de la aplicación utilizando NavController y NavHost.
 * Define las rutas de navegación y los parámetros necesarios para cada vista.
 * @usage Incluir este Composable en el punto de entrada de la aplicación para habilitar la navegación.
 */
@Composable
fun NavManager() {
  val navController = rememberNavController()

  NavHost(navController = navController, startDestination = Routes.HOME) {
    composable(Routes.HOME) {
      HomeView()
    }

    composable(Routes.ADD) {
      AddView()
    }

    composable(Routes.EDIT, arguments =  listOf( navArgument("id") { type = NavType.IntType})) {
      val id = it.arguments?.getInt("id") ?: -1
      EditView(id)
    }
  }
}