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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.julhdev.notes.components.MainBtn
import com.julhdev.notes.components.MainTextField
import com.julhdev.notes.components.MainTitle
import com.julhdev.notes.components.TopBar
import com.julhdev.notes.viewmodel.AuthViewModel
import com.julhdev.notes.viewmodel.ThemeViewModel

@Composable
fun LoginView(
  navController: NavController,
  themeViewModel: ThemeViewModel,
  authViewModel: AuthViewModel
) {

  val focus1 = remember { FocusRequester() }
  val focus2 = remember { FocusRequester() }

  val visiblePassword = remember { mutableStateOf(false) }

  var email by remember { mutableStateOf("") }
  var password by remember { mutableStateOf("") }


  Scaffold(
    topBar = {
      TopBar(
        navController,
        themeViewModel
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
          .heightIn(max = 450.dp)
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
          Spacer(
            modifier = Modifier
              .height(20.dp)
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
              .height(10.dp)
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
              .height(10.dp)
          )
          MainBtn(
            text = "Iniciar Sesión",
            onClick = { /*TODO*/ },
            icon = Icons.AutoMirrored.Filled.Login,
            description = "Icono de iniciar sesión"
          )
          Spacer(
            modifier = Modifier
              .height(10.dp)
          )
          Text(
            text = "¿No tienes una cuenta? Regístrate",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
              .padding(10.dp)
              .align(Alignment.CenterHorizontally)
              .background(MaterialTheme.colorScheme.surfaceVariant)
              .clickable {
                navController.navigate("register")
              }
          )
        }
      }
    }
  }
}