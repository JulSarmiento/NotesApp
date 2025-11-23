package com.julhdev.notes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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
 * SubTitle Composable
 * @param text de tipo String que representa el texto del subtítulo
 * @usage SubTitle(text = "Subtítulo")
 */
@Composable
fun NotificationMessage(
  text: String
) {
  Text(
    text = text,
    style = MaterialTheme.typography.bodyMedium,
    color = MaterialTheme.colorScheme.error
  )
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
  time: Long,
  onClick: () -> Unit
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
      .clickable { onClick() }
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
 * PasswordTextField Composable
 * Composable para gestionar el área de texto de contraseña
 * @param value de tipo String que representa el valor del área de texto
 * @param onValueChange de tipo (String) -> Unit que representa la función a ejecutar al cambiar el valor del área de texto
 * @param label de tipo String que representa la etiqueta del área de texto
 * @param isError de tipo Boolean que representa si el área de texto tiene errores
 * @param focusRequester de tipo FocusRequester que representa el foco del área de texto
 * @param nextFocusRequester de tipo FocusRequester que representa el siguiente foco del área de texto
 * @param keyboardType de tipo KeyboardType que representa el tipo de teclado del área de texto
 * @param trailingIcon de tipo ImageVector que representa el icono del área de texto
 * @param trailingIconClickAction de tipo () -> Unit que representa la acción a realizar al hacer clic en el icono del área de texto
 * @usage PasswordTextField(value = noteContent, onValueChange = { noteContent = it }, label = "Contenido de la Nota")
 */
@Composable
fun PasswordTextField(
  value: String,
  onValueChange: (String) -> Unit,
  label: String,
  isError: Boolean = false,
  focusRequester: FocusRequester = remember { FocusRequester() },
  nextFocusRequester: FocusRequester?,
  keyboardType: KeyboardType = KeyboardType.Password,
  trailingIcon: ImageVector?,
  trailingIconClickAction: () -> Unit = {}
) {
  val focusManager = LocalFocusManager.current
  var activeBtn: Boolean by remember { mutableStateOf(false) }

  OutlinedTextField(
    value = value,
    onValueChange = onValueChange,
    label = { Text(text = label) },
    isError = isError,
    visualTransformation = if (activeBtn) VisualTransformation.None else PasswordVisualTransformation(),
    singleLine = true,
    trailingIcon = {
      if(trailingIcon != null){
        Icon(
          imageVector = trailingIcon,
          contentDescription = "Done Icon",
          tint = if (activeBtn) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.clickable {
            trailingIconClickAction()
            !activeBtn
          }
        )
      }
    },
    modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = 15.dp)
      .focusRequester(focusRequester),
    keyboardOptions = KeyboardOptions(
      keyboardType = keyboardType,
      imeAction = if (nextFocusRequester != null) ImeAction.Next else ImeAction.Done,
    ),
    keyboardActions = KeyboardActions(
      onNext = {
        nextFocusRequester?.requestFocus()
      },
      onDone = {
        focusManager.clearFocus()
      }
    )
  )
}

/**
 * EmailTextField Composable
 * Composable para gestionar el área de texto de email
 * @param value de tipo String que representa el valor del área de texto
 * @param onValueChange de tipo (String) -> Unit que representa la función a ejecutar al cambiar el valor del área de texto
 * @param label de tipo String que representa la etiqueta del área de texto
 * @param isError de tipo Boolean que representa si el área de texto tiene errores
 * @param focusRequester de tipo FocusRequester que representa el foco del área de texto
 * @param nextFocusRequester de tipo FocusRequester que representa el siguiente foco del área de texto
 * @usage EmailTextField(value = noteContent, onValueChange = { noteContent = it }, label = "Contenido de la Nota")
 */
@Composable
fun EmailTextField(
  value: String,
  onValueChange: (String) -> Unit,
  label: String,
  isError: Boolean = false,
  focusRequester: FocusRequester = remember { FocusRequester() },
  nextFocusRequester: FocusRequester?,
) {
  val focusManager = LocalFocusManager.current

  OutlinedTextField(
    value = value,
    onValueChange = onValueChange,
    label = { Text(text = label) },
    isError = isError,
    singleLine = true,
    modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = 15.dp)
      .focusRequester(focusRequester),
    keyboardOptions = KeyboardOptions(
      keyboardType = KeyboardType.Email,
      imeAction = if (nextFocusRequester != null) ImeAction.Next else ImeAction.Done,
    ),
    keyboardActions = KeyboardActions(
      onNext = {
        nextFocusRequester?.requestFocus()
      },
      onDone = {
        focusManager.clearFocus()
      }
    )
  )
}

/**
 * MainTextArea Composable
 * @param value de tipo String que representa el valor del área de texto
 * @param onValueChange de tipo (String) -> Unit que representa la función a ejecutar al cambiar el valor del área de texto
 * @param label de tipo String que representa la etiqueta del área de texto
 * @param isError de tipo Boolean que representa si el área de texto tiene errores
 * @param focusRequester de tipo FocusRequester que representa el foco del área de texto
 * @param nextFocusRequester de tipo FocusRequester que representa el siguiente foco del área de texto
 * @usage MainTextArea(value = noteContent, onValueChange = { noteContent = it }, label = "Contenido de la Nota")
 */
@Composable
fun MainTextField(
  value: String,
  onValueChange: (String) -> Unit,
  label: String,
  isError: Boolean = false,
  focusRequester: FocusRequester = remember { FocusRequester() },
  nextFocusRequester: FocusRequester?,
) {
  val focusManager = LocalFocusManager.current

  OutlinedTextField(
    value = value,
    onValueChange = onValueChange,
    label = { Text(text = label) },
    isError = isError,
    singleLine = true,
    modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = 15.dp)
      .focusRequester(focusRequester),
    keyboardOptions = KeyboardOptions(
      keyboardType = KeyboardType.Text,
      capitalization = KeyboardCapitalization.Sentences,
      imeAction = if (nextFocusRequester != null) ImeAction.Next else ImeAction.Done,
    ),
    keyboardActions = KeyboardActions(
      onNext = {
        nextFocusRequester?.requestFocus()
      },
      onDone = {
        focusManager.clearFocus()
      }
    )
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
  label: String,
  modifier: Modifier = Modifier,
  isError: Boolean = false,
  focusRequester: FocusRequester = remember { FocusRequester() }
) {
  val focusManager = LocalFocusManager.current
  val keyboardController = LocalSoftwareKeyboardController.current

  OutlinedTextField(
    value = value,
    onValueChange = onValueChange,
    singleLine = false,
    label = { Text(text = label) },
    isError = isError,
    modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = 15.dp)
      .heightIn(min = 200.dp)
      .focusRequester(focusRequester)
      .then(modifier),
    maxLines = 40,
    keyboardOptions = KeyboardOptions(
      capitalization = KeyboardCapitalization.Sentences,
    ),
    keyboardActions = KeyboardActions(
      onNext = {
        focusManager.clearFocus()
      }
    ),
    trailingIcon = {
      IconButton(
        onClick = {
          keyboardController?.hide()
          focusManager.clearFocus()
        }
      ) {
        Icon(
          imageVector = Icons.Default.Done,
          contentDescription = "Done Icon"
        )
      }
    }
  )
}

/**
 * MainDialog Composable
 * @param title de tipo String que representa el título del diálogo
 * @param content de tipo String que representa el contenido del diálogo
 * @param onDismiss de tipo () -> Unit que representa la función a ejecutar al cerrar el diálogo
 * @usage MainDialog(title = "Confirmar", content = "¿Estás seguro?", onDismiss = { /* acción */ })
 */
@Composable
fun MainDialog(
  title: String,
  content: String,
  onDismiss: () -> Unit,
) {
  Dialog(
    onDismissRequest = { onDismiss() },
    properties = DialogProperties(
      dismissOnBackPress = true,
      dismissOnClickOutside = true
    ),
    content = {
      Box(
        modifier = Modifier
          .background(
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = MaterialTheme.shapes.medium
          )
          .padding(20.dp)
      ) {
        Column {
          Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.secondary
          )
          Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
          )
        }
      }
    }
  )
}