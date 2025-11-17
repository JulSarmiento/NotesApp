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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.julhdev.notes.components.MainBtn
import com.julhdev.notes.components.MainTextField
import com.julhdev.notes.components.MainTitle
import com.julhdev.notes.components.TopBar
import com.julhdev.notes.viewmodel.AuthViewModel
import com.julhdev.notes.viewmodel.ThemeViewModel

@Composable
fun RegisterView(
  navController: NavController,
  themeViewModel: ThemeViewModel,
  authViewModel: AuthViewModel
) {

  val focus1 = remember { FocusRequester() }
  val focus2 = remember { FocusRequester() }
  val focus3 = remember { FocusRequester() }

  Scaffold(
    topBar = {
      TopBar(
        navController,
        themeViewModel,
        showBackBtn = true
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
          MainTitle(
            text = "Crea tu cuenta",
            color = MaterialTheme.colorScheme.primary
          )
          Spacer(
            modifier = Modifier
              .height(10.dp)
          )
          MainTextField(
            value = "",
            label = " Usuario",
            isError = false,
            focusRequester = focus2,
            onValueChange = { /*TODO*/ },
            nextFocusRequester = focus2
          )
          Spacer(
            modifier = Modifier
              .height(5.dp)
          )
          MainTextField(
            value = "",
            label = "Email",
            onValueChange = { /*TODO*/ },
            isError = false,
            focusRequester = focus1,
            nextFocusRequester = focus2,
            keyboardType = KeyboardType.Email
          )
          Spacer(
            modifier = Modifier
              .height(5.dp)          )
          MainTextField(
            value = "",
            label = "Contraseña",
            isError = false,
            focusRequester = focus2,
            onValueChange = { /*TODO*/ },
            nextFocusRequester = focus2
          )
          Spacer(
            modifier = Modifier
              .height(5.dp)
          )
          MainTextField(
            value = "",
            label = "Confirmar Contraseña",
            isError = false,
            focusRequester = focus2,
            onValueChange = { /*TODO*/ },
            nextFocusRequester = focus3
          )
          Spacer(
            modifier = Modifier
              .height(5.dp)
          )
          MainBtn(
            text = "Crear",
            onClick = { /*TODO*/ },
            icon = null,
            description = "Icono de iniciar sesión"
          )
        }
      }
    }
  }
}

