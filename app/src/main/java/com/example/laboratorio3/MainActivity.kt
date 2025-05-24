package com.example.laboratorio3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.laboratorio3.ui.theme.Laboratorio3Theme
import kotlinx.coroutines.delay
import androidx.compose.ui.res.painterResource



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Laboratorio3Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NumberGuessingGame()
                }
            }
        }
    }
}

@Composable
fun NumberGuessingGame() {
    var started by remember { mutableStateOf(false) }
    var timer by remember { mutableStateOf(0) }
    val maxTime = 60

    var secretNumber by remember { mutableStateOf((0..100).random()) }
    var userInput by remember { mutableStateOf("") }
    var attemptsLeft by remember { mutableStateOf(3) }
    var message by remember { mutableStateOf("Adivina un número entre 0 y 100") }
    var gameOver by remember { mutableStateOf(false) }

    if (started && !gameOver) {
        LaunchedEffect(timer) {
            delay(1000)
            if (timer + 1 >= maxTime) {
                gameOver = true
                message = "🕒 ¡Tiempo agotado! El número era $secretNumber."
            } else {
                timer++
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.image_bomb),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.6f))
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!started) {
                Button(onClick = {
                    started = true
                    timer = 0
                    secretNumber = (0..100).random()
                    attemptsLeft = 3
                    userInput = ""
                    message = "Adivina un número entre 0 y 100"
                    gameOver = false
                }) {
                    Text("Iniciar juego")
                }
            } else {
                Text("⏱ Tiempo: ${timer}s / $maxTime s")
                Spacer(modifier = Modifier.height(8.dp))
                Text(message)

                if (!gameOver) {
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = userInput,
                        onValueChange = { userInput = it },
                        label = { Text("Tu intento") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {
                        val guess = userInput.toIntOrNull()
                        if (guess != null) {
                            if (guess == secretNumber) {
                                message = "🎉 ¡Correcto! El número era $secretNumber."
                                gameOver = true
                            } else {
                                attemptsLeft--
                                message = if (guess < secretNumber) {
                                    "⬆️ Es mayor. Intentos: $attemptsLeft"
                                } else {
                                    "⬇️ Es menor. Intentos: $attemptsLeft"
                                }

                                if (attemptsLeft == 0) {
                                    message = "❌ ¡Te quedaste sin intentos! Era $secretNumber."
                                    gameOver = true
                                }
                            }
                            userInput = ""
                        } else {
                            message = "⚠️ Ingresa un número válido"
                        }
                    }) {
                        Text("Intentar")
                    }
                } else {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {
                        started = false
                    }) {
                        Text("Jugar de nuevo")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNumberGuessingGame() {
    Laboratorio3Theme {
        NumberGuessingGame()
    }
}
