package com.vitantonio.nagauzzi.toniothello.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.ui.tooling.preview.Preview

// Temporary data model for UI (logic will be implemented later)
enum class CellState {
    EMPTY,
    BLACK,
    WHITE
}

enum class Player {
    BLACK,
    WHITE
}

enum class Language {
    JAPANESE,
    ENGLISH
}

// Custom pink color scheme with dark pink text
private val PinkColorScheme = lightColorScheme(
    primary = Color(0xFFFF69B4),           // Hot Pink for buttons
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFE4EC),  // Light Pink for cards
    onPrimaryContainer = Color(0xFFC71585), // Medium Violet Red (dark pink) for text
    onSurface = Color(0xFFC71585),         // Dark pink for all surface text
    onBackground = Color(0xFFC71585)       // Dark pink for background text
)

@Composable
@Preview
fun App() {
    MaterialTheme(
        colorScheme = PinkColorScheme
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            OthelloGame()
        }
    }
}
