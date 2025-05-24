package com.example.laboratorio3.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun GameScreen(
    secretNumber: Int,
    message: MutableState<String>,
    attempts: MutableState<Int>,
    onGameOver: () -> Unit
) {
    var input by remember { mutableStateOf("") }

    BackgroundContainer {
        Text(message.value)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = input,
            onValueChange = {
                val numeric = it.filter { char -> char.isDigit() }
                val number = numeric.toIntOrNull()
                if (number == null || number in 1..100) {
                    input = numeric
                }
            },
            label = { Text("Tu intento (1-100)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val guess = input.toIntOrNull()
            if (guess == null) {
                message.value = "⚠️ Ingresa un número válido"
            } else if (guess == secretNumber) {
                message.value = "🎉 ¡Correcto! El número era $secretNumber."
                onGameOver()
            } else {
                attempts.value -= 1
                message.value = if (guess < secretNumber)
                    "⬆️ Es mayor. Intentos: ${attempts.value}"
                else
                    "⬇️ Es menor. Intentos: ${attempts.value}"

                if (attempts.value == 0) {
                    message.value = "❌ Sin intentos. Era $secretNumber."
                    onGameOver()
                }
            }
            input = ""
        }) {
            Text("Intentar")
        }
    }
}
