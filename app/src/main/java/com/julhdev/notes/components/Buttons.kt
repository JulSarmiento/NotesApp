package com.julhdev.notes.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * MainBtn Composable
 * @param text de tipo String que representa el texto del botón
 * @param onClick de tipo () -> Unit que representa la acción a realizar al hacer clic en el botón
 * @usage MainBtn(text = "Login", onClick = { /* acción a realizar */ })
 */
@Composable
fun MainBtn(
  text: String,
  onClick: () -> Unit
) {
  Button(
    onClick = onClick,
    colors = ButtonDefaults.buttonColors(
      containerColor = MaterialTheme.colorScheme.primary,
      contentColor = MaterialTheme.colorScheme.onPrimary
    )
  ) {
    Text(
      text = text,
      letterSpacing = 0.5.sp,
      fontWeight = FontWeight.Medium,
      modifier = Modifier
        .padding(horizontal = 10.dp)
    )
  }
}

/**
 * FloatingButton Composable
 * @param onClick de tipo () -> Unit que representa la acción a realizar al hacer clic en el botón flotante
 * @usage FloatingButton(onClick = { /* acción a realizar */ })
 */
@Composable
fun FloatingButton(
  onClick: () -> Unit,
) {
  FloatingActionButton(
    onClick = onClick,
    containerColor = MaterialTheme.colorScheme.primary,
    contentColor = MaterialTheme.colorScheme.onPrimary,
  ) {
    Icon(
      imageVector = Icons.Default.Add,
      contentDescription = "Nueva Nota",
    )
  }
}


/**
 * SwitchButton Composable
 * @param isDark de tipo Boolean que representa el estado inicial del switch
 * @param onToggle de tipo (Boolean) -> Unit que representa la acción a realizar al cambiar el estado del switch
 * @usage SwitchButton(isDark = false, onToggle = { isChecked -> /* acción a realizar */ })
 */
@Composable
fun SwitchButton(
  isDark: Boolean,
  onToggle: (Boolean) -> Unit
) {
  var switchState by remember { mutableStateOf(isDark) }

  IconToggleButton(
    checked = switchState,
    onCheckedChange = {
      switchState = it
      onToggle(it)
    }
  ) {
    Icon(
      imageVector = if (switchState) Icons.Default.DarkMode else Icons.Default.LightMode,
      contentDescription = "Toggle Theme",
      tint = MaterialTheme.colorScheme.onPrimary
    )
  }
}