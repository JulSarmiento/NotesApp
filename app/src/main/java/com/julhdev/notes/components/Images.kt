package com.julhdev.notes.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition


/**
 * MainImage Composable
 * @param modifier: Modifier debe ser pasado para personalizar el diseño del componente.
 * @param image: Int es el recurso de la imagen Lottie a mostrar.
 * @usage MainImage(modifier = Modifier.size(200.dp), image = R.raw.example
 */
@Composable
fun MainImage(
  modifier: Modifier = Modifier,
  image: Int
) {
  val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(image ))

  BoxWithConstraints(
    contentAlignment = Alignment.Center,
    modifier = Modifier
      .fillMaxWidth()
  ){

    val imageSize = maxWidth * 0.8f

    LottieAnimation(
      composition = composition,
      iterations = LottieConstants.IterateForever,
      modifier = Modifier
        .size(imageSize)
        .then(modifier)
    )
  }
}

/**
 * PngImage Composable
 * @param modifier: Modifier debe ser pasado para personalizar el diseño del componente.
 * @param image: Int es el recurso de la imagen PNG a mostrar.
 * @param description: String es la descripción de la imagen para accesibilidad.
 * @usage PngImage(modifier = Modifier.size(200.dp), image = R.drawable.example, description = "Example Image")
 */
@Composable
fun PngImage(
  modifier: Modifier = Modifier,
  image: Int,
  description: String
) {
  BoxWithConstraints(
    contentAlignment = Alignment.Center,
    modifier = Modifier
      .fillMaxWidth()
  ){
    val imageSize = maxWidth * 0.9f
    Image(
      painter = painterResource(id = image),
      contentDescription = description,
      modifier = modifier
        .size(imageSize)
        .then(modifier)
    )
  }
}