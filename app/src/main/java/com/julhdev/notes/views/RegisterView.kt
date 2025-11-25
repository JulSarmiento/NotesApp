package com.julhdev.notes.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.julhdev.notes.components.EmailTextField
import com.julhdev.notes.components.FormBtn
import com.julhdev.notes.components.MainTextField
import com.julhdev.notes.components.MainTitle
import com.julhdev.notes.components.NotificationMessage
import com.julhdev.notes.components.PasswordTextField
import com.julhdev.notes.components.TopBar
import com.julhdev.notes.navigation.Routes
import com.julhdev.notes.utils.resources.Resource
import com.julhdev.notes.utils.validateEmail
import com.julhdev.notes.utils.validateUsername
import com.julhdev.notes.viewmodel.AuthViewModel
import com.julhdev.notes.viewmodel.RegisterFormViewModel
import com.julhdev.notes.viewmodel.ThemeViewModel

/**
 * Composable para la vista de Registro de usuarios
 * @param navController Controlador de navegación de Jetpack Compose
 * @param themeViewModel Modelo de vista para el tema
 * @param authViewModel Modelo de vista para la autenticación
 * @usage [RegisterView]
 */
@Composable
fun RegisterView(
  navController: NavController,
  themeViewModel: ThemeViewModel,
  authViewModel: AuthViewModel,
  registerViewModel: RegisterFormViewModel
) {

  DisposableEffect(Unit) {
    onDispose {
      authViewModel.cleanError()
    }
  }

  val state = authViewModel.state.collectAsState()
  val isLoading = authViewModel.isLoading.collectAsState()

  val formState = registerViewModel.state

  val focusUsername = remember { FocusRequester() }
  val focusEmail = remember { FocusRequester() }
  val focusPassword = remember { FocusRequester() }
  val focusConfirmPassword = remember { FocusRequester() }

  val visiblePassword = rememberSaveable { mutableStateOf(false) }
  var username: String by rememberSaveable { mutableStateOf("") }
  var email: String by rememberSaveable { mutableStateOf("") }
  var password: String by rememberSaveable { mutableStateOf("") }
  var confirmPassword: String by rememberSaveable { mutableStateOf("") }

  Scaffold(
    topBar = {
      TopBar(
        navController,
        themeViewModel,
        showBackBtn = true,
        showLogoutBtn = false,
        authViewModel
      )
    }
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .padding(20.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
    ) {

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .heightIn(max = 650.dp)
          .background(
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = MaterialTheme.shapes.medium
          )
          .padding(10.dp)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center,
        ) {
          Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Icono de iniciar sesión",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
              .size(100.dp)
              .align(Alignment.CenterHorizontally)
              .padding(top = 10.dp)
          )
          NotificationMessage(
            text = when {
              state.value is Resource.Error -> authViewModel.uiError.collectAsState().value
              formState.usernameError != null -> formState.usernameError
              formState.emailError != null -> formState.emailError
              formState.passwordError != null -> formState.passwordError
              formState.confirmPasswordError != null -> formState.confirmPasswordError
              else -> ""
            }
          )
          MainTitle(
            text = "Crea tu cuenta",
            color = MaterialTheme.colorScheme.primary
          )
          Spacer(
            modifier = Modifier
              .height(10.dp)
          )
          MainTextField(
            value = username.trim(),
            label = " Usuario",
            isError = formState.usernameError != null,
            focusRequester = focusUsername,
            onValueChange = { username = it },
            nextFocusRequester = focusEmail,
          )
          Spacer(
            modifier = Modifier
              .height(5.dp)
          )
          EmailTextField(
            value = email.trim(),
            label = "Email",
            onValueChange = { email = it },
            isError = formState.emailError != null,
            focusRequester = focusEmail,
            nextFocusRequester = focusPassword,
          )
          Spacer(
            modifier = Modifier
              .height(5.dp)
          )
          PasswordTextField(
            value = password.trim(),
            label = "Contraseña",
            isError = formState.passwordError != null,
            focusRequester = focusPassword,
            onValueChange = { password = it },
            nextFocusRequester = focusConfirmPassword,
            trailingIcon = if (password.isNotEmpty()) Icons.Default.RemoveRedEye else null,
            trailingIconClickAction = {
              visiblePassword.value = !visiblePassword.value
            }
          )
          Spacer(
            modifier = Modifier
              .height(5.dp)
          )
          PasswordTextField(
            value = confirmPassword.trim(),
            label = "Confirmar Contraseña",
            isError = formState.confirmPasswordError != null,
            focusRequester = focusConfirmPassword,
            onValueChange = { confirmPassword = it },
            nextFocusRequester = null,
            trailingIcon = if (confirmPassword.isNotEmpty()) Icons.Default.RemoveRedEye else null,
            trailingIconClickAction = {
              visiblePassword.value = !visiblePassword.value
            }
          )
          Spacer(
            modifier = Modifier
              .height(5.dp)
          )
          FormBtn(
            text = "Crear",
            enabled = !isLoading.value,
            onClick = {
              registerViewModel.validateAndSubmit(
                username = username.trim(),
                email = email.trim(),
                password = password.trim(),
                confirmPassword = confirmPassword
              ) { username, email, password ->
                authViewModel.register(email, password, username) {
                  navController.navigate(Routes.HOME)
                }
              }
            }
          )
        }
      }
    }
  }
}

