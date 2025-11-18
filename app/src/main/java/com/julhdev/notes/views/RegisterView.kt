package com.julhdev.notes.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.julhdev.notes.components.MainBtn
import com.julhdev.notes.components.MainTextField
import com.julhdev.notes.components.MainTitle
import com.julhdev.notes.components.NotificationMessage
import com.julhdev.notes.components.TopBar
import com.julhdev.notes.navigation.Routes
import com.julhdev.notes.viewmodel.AuthViewModel
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
  authViewModel: AuthViewModel
) {
  val visiblePassword = remember { mutableStateOf(false) }

  val focus1 = remember { FocusRequester() }
  val focus2 = remember { FocusRequester() }
  val focus3 = remember { FocusRequester() }

  var username: String by remember { mutableStateOf("") }
  var email: String by remember { mutableStateOf("") }
  var password: String by remember { mutableStateOf("") }
  var confirmPassword: String by remember { mutableStateOf("") }

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

      if(authViewModel.isLoading) {
        Box(
          modifier = Modifier
            .fillMaxSize(),
          contentAlignment = Alignment.Center
        ) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
          ) {
            Surface(
              shape = RoundedCornerShape(10.dp),
              tonalElevation = 8.dp,
              modifier = Modifier.wrapContentSize()
            ) {
              Column(
                modifier = Modifier
                  .padding(20.dp)
                  .widthIn(min = 120.dp),
                horizontalAlignment = Alignment.CenterHorizontally
              ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(20.dp))
              }
            }
          }
        }
      }
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
          if (authViewModel.errorMessage) {
            NotificationMessage(
              text = authViewModel.uiError ?: "Ha ocurrido un error"
            )
            Spacer(
              modifier = Modifier
                .height(20.dp)
            )
          }
          MainTitle(
            text = "Crea tu cuenta",
            color = MaterialTheme.colorScheme.primary
          )
          Spacer(
            modifier = Modifier
              .height(10.dp)
          )
          MainTextField(
            value = username,
            label = " Usuario",
            isError = false,
            focusRequester = focus2,
            onValueChange = { username = it },
            nextFocusRequester = focus2
          )
          Spacer(
            modifier = Modifier
              .height(5.dp)
          )
          MainTextField(
            value = email,
            label = "Email",
            onValueChange = { email = it },
            isError = false,
            focusRequester = focus1,
            nextFocusRequester = focus2,
            keyboardType = KeyboardType.Email
          )
          Spacer(
            modifier = Modifier
              .height(5.dp)
          )
          MainTextField(
            value = password,
            label = "Contraseña",
            isError = false,
            visualTransformation = if (visiblePassword.value) null else PasswordVisualTransformation(),
            focusRequester = focus2,
            onValueChange = { password = it },
            nextFocusRequester = focus2,
            trailingIcon = if (password.isNotEmpty()) Icons.Default.RemoveRedEye else null,
            trailingIconClickAction = {
              visiblePassword.value = !visiblePassword.value
            }
          )
          Spacer(
            modifier = Modifier
              .height(5.dp)
          )
          MainTextField(
            value = confirmPassword,
            label = "Confirmar Contraseña",
            isError = confirmPassword != password,
            visualTransformation = if (visiblePassword.value) null else PasswordVisualTransformation(),
            focusRequester = focus2,
            onValueChange = { confirmPassword = it },
            nextFocusRequester = focus3,
            trailingIcon = if (password.isNotEmpty()) Icons.Default.RemoveRedEye else null,
            trailingIconClickAction = {
              visiblePassword.value = !visiblePassword.value
            }
          )
          Spacer(
            modifier = Modifier
              .height(5.dp)
          )
          MainBtn(
            text = "Crear",
            onClick = {
              authViewModel.register(email, password, username) {
                navController.navigate(Routes.HOME)
              }
            },
            icon = null,
            description = "Icono de iniciar sesión"
          )
        }
      }
    }
  }
}

