package com.julhdev.notes.components

import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Formatea un tiempo en milisegundos a una cadena legible.
 * @param time de tipo Long que representa el tiempo en milisegundos
 * @return String con el tiempo formateado en el patrón "dd/MM/yyyy HH:mm"
 * @usage TimeFormat(1625072400000L) // Devuelve "30/06/2021 15:00" (dependiendo de la zona horaria)
 */
@Composable
fun timeFormat(time: Long): String {
  val pattern = "dd/MM/yyyy HH:mm"
  val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
  val formattedTime = Instant.ofEpochMilli(time)
    .atZone(ZoneId.systemDefault())
    .toLocalDateTime()
    .format(formatter)
  return formattedTime
}

/**
 * NoteCard Composable
 * @param title de tipo String que representa el título de la nota
 * @param content de tipo String que representa el contenido de la nota
 * @param time de tipo String que representa la hora de creación o modificación de la nota
 * @usage NoteCard(title = "Note Title", content = "This is the content of the note.", time = "12:00 PM")
 */
@Composable
fun NoteCard(
  title: String,
  content: String,
  time: Long
) {
  Box(
    modifier = Modifier
      .background(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.medium
      )
      .padding(15.dp)
      .fillMaxWidth()
      .heightIn(max = 150.dp)
  ) {
    Column {
      SubTitle(
        text = title,
        color = MaterialTheme.colorScheme.secondary
      )
      Spacer(
        modifier = Modifier
          .height(5.dp)
      )
      Text(
        text = content,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface
      )
      Spacer(
        modifier = Modifier
          .height(10.dp)
      )
      Text(
        text = timeFormat(time),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }
  }
}

/**
 * MainTextArea Composable
 * @param value de tipo String que representa el valor del área de texto
 * @param onValueChange de tipo (String) -> Unit que representa la función a ejecutar al cambiar el valor del área de texto
 * @param label de tipo String que representa la etiqueta del área de texto
 * @usage MainTextArea(value = noteContent, onValueChange = { noteContent = it }, label = "Contenido de la Nota")
 */
@Composable
fun MainTextField(
  value: String,
  onValueChange: (String) -> Unit,
  label: String
){
  OutlinedTextField(
    value = value,
    onValueChange = onValueChange,
    label = { Text(text = label) },
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 30.dp)
      .padding(bottom = 15.dp)
  )
}

/**
 * MainTextArea Composable
 * @param value de tipo String que representa el valor del área de texto
 * @param onValueChange de tipo (String) -> Unit que representa la función a ejecutar al cambiar el valor del área de texto
 * @param label de tipo String que representa la etiqueta del área de texto
 * @usage MainTextArea(value = noteContent, onValueChange = { noteContent = it }, label = "Contenido de la nota")
 */
@Composable
fun MainTextArea(
  value: String,
  onValueChange: (String) -> Unit,
  label: String
){
  OutlinedTextField(
    value = value,
    onValueChange = onValueChange,
    singleLine = false,
    label = { Text(text = label) },
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 30.dp)
      .padding(bottom = 15.dp)
      .heightIn(min = 200.dp),
    maxLines = 40,
  )
}

/**
 * MainDialog Composable
 * @param title de tipo String que representa el título del diálogo
 * @param content de tipo String que representa el contenido del diálogo
 * @param onDismiss de tipo () -> Unit que representa la función a ejecutar al cerrar el diálogo
 * @param onConfirm de tipo () -> Unit que representa la función a ejecutar al confirmar la acción en el diálogo
 * @param state de tipo Boolean que representa el estado de visibilidad del diálogo
 * @usage MainDialog(title = "Confirmar", content = "¿Estás seguro?", onDismiss = { /* acción */ }, onConfirm = { /* acción */ })
 */
@Composable
fun MainDialog(
  title: String,
  content: String,
  onDismiss: () -> Unit,
  onConfirm: () -> Unit,
) {
  AlertDialog(
    onDismissRequest = { onDismiss() },
    title = {
      Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onSurface
      )
    },
    text = {
      Text(
        text = content,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface
      )
    },
    confirmButton = {
      Button(
        onClick = { onConfirm() },
        colors = ButtonDefaults.buttonColors(
          containerColor = MaterialTheme.colorScheme.primary,
          contentColor = MaterialTheme.colorScheme.onPrimary
        )
      ) {
        Text(
          text = "Aceptar",
          letterSpacing = 0.5.sp,
          fontWeight = FontWeight.Medium,
          modifier = Modifier
            .padding(horizontal = 10.dp)
        )
      }
    },
    dismissButton = {
      Button(
        onClick = { onDismiss() },
        colors = ButtonDefaults.buttonColors(
          containerColor = MaterialTheme.colorScheme.primary,
          contentColor = MaterialTheme.colorScheme.onPrimary
        )
      ) {
        Text(
          text = "Cancelar",
          letterSpacing = 0.5.sp,
          fontWeight = FontWeight.Medium,
          modifier = Modifier
            .padding(horizontal = 10.dp)
        )
      }
    }
  )

}