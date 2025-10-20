package com.julhdev.notes.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.julhdev.notes.R
import com.julhdev.notes.components.MainBtn
import com.julhdev.notes.components.MainImage
import com.julhdev.notes.components.OnBoardingTitle
import com.julhdev.notes.components.Subtitle
import com.julhdev.notes.navigation.Routes
import com.julhdev.notes.viewmodel.OnBoardingViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun OnBoardingView(navController: NavController, onBoardingViewModel: OnBoardingViewModel) {
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
      )

      Subtitle(
        text = "¡Bienvenido a Dashi's Notes!"
      )

      Spacer(
        modifier = Modifier
          .height(10.dp)
      )

      Text(
        text = "La mejor app para tomar notas de forma rápida y sencilla.",
        textAlign = TextAlign.Center,
        fontSize = 16.sp,
        modifier = Modifier
          .padding(horizontal = 10.dp)
      )

      Spacer(
        modifier = Modifier
          .height(25.dp)
      )

      MainBtn(
        text = "Comenzar",
        onClick = {
          CoroutineScope(Dispatchers.IO).launch {
            onBoardingViewModel.saveBoarding(true)
          }
          navController.navigate(Routes.HOME) {
            popUpTo(Routes.ONBOARDING) { inclusive = true }
            launchSingleTop = true
          }
        },
      )
    }

  }
}

