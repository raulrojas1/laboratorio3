package com.example.laboratorio3.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun PlayingScreen(
    timer: Int,
    maxTime: Int,
    userInput: String,
    onUserInputChange: (String) -> Unit,
    message: String,
    onTry: () -> Unit
) {
    Text("⏱ Tiempo: ${timer}s / $maxTime s")
    Spacer(modifier = Modifier.height(8.dp))
    Text(message)
    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = userInput,
        onValueChange = onUserInputChange,
        label = { Text("Tu intento") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )

    Spacer(modifier = Modifier.height(16.dp))
    Button (onClick = onTry) {
        Text("Intentar")
    }
}
