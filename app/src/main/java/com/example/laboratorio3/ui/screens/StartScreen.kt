package com.example.laboratorio3.ui.screens

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun StartScreen(onStart: () -> Unit) {
    Button(onClick = onStart) {
        Text("Iniciar juego")
    }
}
