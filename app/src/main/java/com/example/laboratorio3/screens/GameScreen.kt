package com.example.laboratorio3.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
@Composable
fun GameScreen(
    secretNumber: Int,
    message: MutableState<String>,
    attempts: MutableState<Int>,
    onGameOver: () -> Unit
) {
    var input by remember { mutableStateOf("") }
    var timeLeft by remember { mutableStateOf(60) }

    LaunchedEffect(Unit) {
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft--
        }
        if (timeLeft == 0) {
            message.value = "⏰ Tiempo agotado. El número era $secretNumber."
            onGameOver()
        }
    }

    BackgroundContainer {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "⏱️ Tiempo restante: ${timeLeft}s",
                color = Color.Black,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = message.value,
                color = Color.Black,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = input,
                onValueChange = {
                    val numeric = it.filter { char -> char.isDigit() }
                    val number = numeric.toIntOrNull()
                    if (number == null || number in 1..100) {
                        input = numeric
                    }
                },
                label = {
                    Text(
                        "Tu intento (1-100)",
                        color = Color.LightGray
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = LocalTextStyle.current.copy(fontSize = 20.sp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
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
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Intentar", fontSize = 18.sp)
            }
        }
    }
}
