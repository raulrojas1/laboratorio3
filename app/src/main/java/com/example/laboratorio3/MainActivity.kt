package com.example.laboratorio3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.laboratorio3.ui.NumberGuessingGameApp
import com.example.laboratorio3.ui.theme.Laboratorio3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Laboratorio3Theme {
                NumberGuessingGameApp()
            }
        }
    }
}
