package com.example.laboratorio3.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.example.laboratorio3.ui.screens.StartScreen
import com.example.laboratorio3.ui.screens.PlayingScreen
import com.example.laboratorio3.ui.screens.GameOverScreen

enum class GameScreen {
    START,
    PLAYING,
    GAME_OVER
}

@Composable
fun NumberGuessingGameApp() {
    var currentScreen by remember { mutableStateOf(GameScreen.START) }

    var timer by remember { mutableStateOf(0) }
    val maxTime = 60

    var secretNumber by remember { mutableStateOf((0..100).random()) }
    var userInput by remember { mutableStateOf("") }
    var attemptsLeft by remember { mutableStateOf(3) }
    var message by remember { mutableStateOf("Adivina un número entre 0 y 100") }

    // Temporizador
    if (currentScreen == GameScreen.PLAYING) {
        LaunchedEffect(timer) {
            delay(1000)
            if (timer + 1 >= maxTime) {
                message = "🕒 ¡Tiempo agotado! El número era $secretNumber."
                currentScreen = GameScreen.GAME_OVER
            } else {
                timer++
            }
        }
    }

    // Este Box + Column es lo que daba la estructura original visual
    Box(modifier = Modifier.fillMaxSize()) {
        androidx.compose.foundation.Image(
            painter = androidx.compose.ui.res.painterResource(id = com.example.laboratorio3.R.drawable.image_bomb),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )

        androidx.compose.foundation.layout.Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.6f))
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (currentScreen) {
                GameScreen.START -> StartScreen(
                    onStart = {
                        timer = 0
                        secretNumber = (1..100).random()
                        attemptsLeft = 3
                        userInput = ""
                        message = "Adivina un número entre 0 y 100"
                        currentScreen = GameScreen.PLAYING
                    }
                )

                GameScreen.PLAYING -> PlayingScreen(
                    timer = timer,
                    maxTime = maxTime,
                    userInput = userInput,
                    onUserInputChange = { userInput = it },
                    message = message,
                    onTry = {
                        val guess = userInput.toIntOrNull()
                        if (guess != null) {
                            if (guess == secretNumber) {
                                message = "🎉 ¡Correcto! El número era $secretNumber."
                                currentScreen = GameScreen.GAME_OVER
                            } else {
                                attemptsLeft--
                                message = if (guess < secretNumber) {
                                    "⬆️ Es mayor. Intentos: $attemptsLeft"
                                } else {
                                    "⬇️ Es menor. Intentos: $attemptsLeft"
                                }

                                if (attemptsLeft == 0) {
                                    message = "❌ ¡Te quedaste sin intentos! Era $secretNumber."
                                    currentScreen = GameScreen.GAME_OVER
                                }
                            }
                            userInput = ""
                        } else {
                            message = "⚠️ Ingresa un número válido"
                        }
                    }
                )

                GameScreen.GAME_OVER -> GameOverScreen(
                    message = message,
                    onRestart = { currentScreen = GameScreen.START }
                )
            }
        }
    }
}
