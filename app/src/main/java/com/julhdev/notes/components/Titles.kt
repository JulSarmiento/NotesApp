package com.julhdev.notes.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

/**
 * MainTitle Composable
 * @param text de tipo String que representa el texto del título
 * @usage MainTitle(text = "This is the main title")
 */
@Composable
fun MainTitle(
  text: String,
  color: Color
) {
  Text(
    style = MaterialTheme.typography.bodyLarge,
    text = text,
    color = color,
    fontSize = 25.sp,
    fontWeight = FontWeight.Bold,
  )
}

/**
 * Subtitle Composable
 * @param text de tipo String que representa el texto del subtítulo
 * @param modifier de tipo Modifier para modificar el estilo del subtítulo
 * @usage Subtitle(text = "This is a subtitle", modifier = Modifier.padding(8.dp))
 */
@Composable
fun SubTitle(
  text: String,
  modifier: Modifier = Modifier,
  color: Color = MaterialTheme.colorScheme.onSurface,
  textAlign: TextAlign = TextAlign.Start
) {
  Text(
    text = text,
    fontSize = 18.sp,
    fontWeight = FontWeight.Bold,
    textAlign = textAlign,
    color = color,
    modifier = Modifier
      .then(modifier)
  )
}

/**
 * OnBoardingTitle Composable
 * @param text de tipo String que representa el texto del título
 * @param modifier de tipo Modifier para modificar el estilo del título
 * @usage OnBoardingTitle(text = "Welcome to the App", modifier = Modifier.padding
 */
@Composable
fun OnBoardingTitle(
  text: String,
  modifier: Modifier = Modifier
) {
  Text(
    text = text,
    fontSize = 35.sp,
    fontWeight = FontWeight.Bold,
    color = MaterialTheme.colorScheme.primary,
    modifier = Modifier
      .then(modifier)

  )
}