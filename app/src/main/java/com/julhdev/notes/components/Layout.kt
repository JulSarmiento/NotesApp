package com.julhdev.notes.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.julhdev.notes.navigation.Routes
import com.julhdev.notes.viewmodel.AuthViewModel
import com.julhdev.notes.viewmodel.ThemeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * TopBar Composable
 * @param navController de tipo NavController  que representa el controlador de navegacion.
 * @param themeViewModel de tipo ThemeViewModel que representa el viewModel del tema para la aplicacion.
 * @param showBackBtn de tipo Boolean para mostrar  o no el navigationIcon para regresar atras.
 * @param showLogoutBtn de tipo Boolean para mostrar  o no el navigationIcon para cerrar sesion.
 * @param authViewModel de tipo AuthViewModel que representa el viewModel de autenticacion para la aplicacion.
 * @usage TopBar(navController = NavController, themeViewModel = ThemeViewModel, showBackBtn = true)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
  navController: NavController,
  themeViewModel: ThemeViewModel,
  showBackBtn: Boolean = false,
  showLogoutBtn: Boolean = false,
  authViewModel: AuthViewModel
) {
  val theme = themeViewModel.isDark.collectAsState().value
  TopAppBar(
    title = {
      MainTitle(
        text = "Dashi's Notes",
        color = MaterialTheme.colorScheme.onPrimary
      )
    },
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = MaterialTheme.colorScheme.primary,
    ),
    actions = {
      SwitchButton(
        isDark = theme,
        onToggle = {
          CoroutineScope(Dispatchers.Main).launch {
            themeViewModel.saveIsDark(!theme)
          }
        }
      )
      if (showLogoutBtn) {
        IconButton(
          icon = Icons.AutoMirrored.Filled.Logout,
          description = "Logout",
          onClick = {
            authViewModel.logout()
            navController.navigate(Routes.LOGIN){
              popUpTo(navController.graph.startDestinationId) {
                inclusive = true
              }
            }
          }
        )
      }
    },
    navigationIcon = {
      if (showBackBtn) {
        IconButton(
          icon = Icons.AutoMirrored.Filled.ArrowBack,
          description = "Back",
          onClick = {
            navController.popBackStack()
          }
        )
      }
    }
  )
}