package com.julhdev.notes.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.julhdev.notes.R
import com.julhdev.notes.components.MainBtn
import com.julhdev.notes.components.MainImage
import com.julhdev.notes.components.OnBoardingTitle
import com.julhdev.notes.components.Subtitle

@Composable
fun OnBoardingView() {
  Scaffold { innerPadding ->
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)

    ) {
      OnBoardingTitle(
        text = "Dashi's Notes",
      )
      Spacer(
        modifier = Modifier
          .height(20.dp)
      )

      MainImage(
        image= R.raw.copywriting,
        modifier = Modifier
          .padding(vertical = 30.dp)
          .fillMaxWidth()
      )

      Subtitle(
        text = "!Bienvenido a Dashi's Notes!"
      )

      Spacer(
        modifier = Modifier
          .height(10.dp)
      )

      Text(
        text = "La mejor app para tomar notas de forma rápida y sencilla.",
        style = MaterialTheme.typography.bodySmall,
        textAlign = TextAlign.Center
      )

      Spacer(
        modifier = Modifier
          .height(25.dp)
      )

      MainBtn(
        text = "Comenzar",
        onClick = {/*TODO: Navegar a la siguiente pantalla*/ },
      )
    }

  }
}

