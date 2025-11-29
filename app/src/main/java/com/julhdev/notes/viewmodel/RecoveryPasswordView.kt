package com.julhdev.notes.viewmodel

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
import com.julhdev.notes.components.MainTitle
import com.julhdev.notes.components.NotificationMessage
import com.julhdev.notes.components.TopBar
import com.julhdev.notes.navigation.Routes

@Composable
fun RecoveryPasswordView(
  navController: NavController,
  themeViewModel: ThemeViewModel,
  authViewModel: AuthViewModel
) {

  DisposableEffect(Unit) {
    onDispose {
      authViewModel.cleanError()
    }
  }
  val isLoading = authViewModel.isLoading.collectAsState()

  val focusEmail = remember { FocusRequester() }
  var email by rememberSaveable { mutableStateOf("") }

  Scaffold(
    topBar = {
      TopBar(
        navController,
        themeViewModel,
        showBackBtn = true,
        showLogoutBtn = false,
        authViewModel = authViewModel
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
          .heightIn(max = 550.dp)
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
          NotificationMessage(
            text = when {
              authViewModel.isError.collectAsState().value -> authViewModel.uiError.collectAsState().value
              else -> ""
            }
          )
          MainTitle(
            text = "Recupera tu contraseña",
            color = MaterialTheme.colorScheme.primary
          )
          Spacer(
            modifier = Modifier
              .height(20.dp)
          )
          EmailTextField(
            value = email,
            label = "Email",
            onValueChange = { email = it },
            isError = false,
            focusRequester = focusEmail,
            nextFocusRequester = null,
          )
          Spacer(
            modifier = Modifier
              .height(10.dp)
          )
          FormBtn(
            text = "Validar",
            enabled = !isLoading.value,
            isLoading = isLoading.value,
            onClick = {
              authViewModel.ifUserExist(email = email) {
                authViewModel.sendEmailToResetPassword(email = email)               }
            }
          )
        }
      }
    }
  }
}