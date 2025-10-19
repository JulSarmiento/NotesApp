package com.julhdev.notes.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun MainTitle(
  text: String
) {
  Text(
    style = MaterialTheme.typography.bodyLarge,
    text = text,
  )
}

@Composable
fun Subtitle(
  text: String,
  modifier: Modifier = Modifier
) {
  Text(
    text = text,
    style = MaterialTheme.typography.bodyMedium,
    fontWeight = FontWeight.Bold,
    modifier = Modifier
      .then(modifier)
  )
}

@Composable
fun OnBoardingTitle(
  text: String,
  modifier: Modifier = Modifier
) {
  Text(
    text = text,
    style = MaterialTheme.typography.headlineLarge,
    fontWeight = FontWeight.Bold,
    modifier = Modifier
      .then(modifier)

  )
}