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
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  onClick: () -> Unit,
  icon: ImageVector,
  description: String,
) {
  Button(
    onClick = onClick,
    enabled = enabled,
    colors = ButtonDefaults.buttonColors(
      containerColor = MaterialTheme.colorScheme.primary,
      contentColor = MaterialTheme.colorScheme.onPrimary
    )
  ) {
    Icon(
      imageVector = icon,
      contentDescription = description,
      tint = MaterialTheme.colorScheme.onPrimary
    )
    Text(
      text = text,
      letterSpacing = 0.5.sp,
      fontWeight = FontWeight.Medium,
      modifier = Modifier
        .padding(horizontal = 10.dp)
        .then(modifier)
    )
  }
}

/**
 * IconButton Composable
 * @param icon de tipo ImageVector que representa el icono del botón
 * @param onClick de tipo () -> Unit que representa la acción a realizar al hacer clic en el botón
 * @param description de tipo String que representa la descripción del icono para accesibilidad
 * @usage IconButton(icon = Icons.Default.Home, onClick = { /* acción a realizar */ }, description = "Home Icon")
 */
@Composable
fun IconButton(
  icon: ImageVector,
  onClick: () -> Unit,
  description: String,
  modifier: Modifier = Modifier
) {
  IconButton(
    onClick = onClick,
    modifier = modifier
  ) {
    Icon(
      imageVector = icon,
      contentDescription = description,
      tint = MaterialTheme.colorScheme.onPrimary
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

  IconToggleButton(
    checked = isDark,
    onCheckedChange = {
      onToggle(it)
    }
  ) {
    Icon(
      imageVector = if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
      contentDescription = "Toggle Theme",
      tint = MaterialTheme.colorScheme.onPrimary
    )
  }
}