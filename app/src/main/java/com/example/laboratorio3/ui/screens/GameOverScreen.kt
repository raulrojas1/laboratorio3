package com.example.laboratorio3.ui.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GameOverScreen(message: String, onRestart: () -> Unit) {
    Text(message)
    Spacer(modifier = Modifier.height(16.dp))
    Button (onClick = onRestart) {
        Text("Jugar de nuevo")
    }
}
