package com.julhdev.notes.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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