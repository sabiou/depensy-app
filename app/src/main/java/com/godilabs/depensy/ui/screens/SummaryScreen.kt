package com.godilabs.depensy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SummaryScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(Modifier.height(48.dp))

            Text(
                text = "Résumé",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = Color.Black,
                letterSpacing = 0.1.sp
            )

            Spacer(Modifier.height(32.dp))

            Text(
                text = "Résumé des dépenses (bientôt disponible)",
                fontSize = 16.sp,
                color = Color.Black.copy(alpha = 0.6f)
            )
        }
    }
}
