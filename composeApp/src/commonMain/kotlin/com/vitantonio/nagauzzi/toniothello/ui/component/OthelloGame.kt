package com.vitantonio.nagauzzi.toniothello.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.toniothello.domain.logic.makeMove
import com.vitantonio.nagauzzi.toniothello.ui.state.OthelloGameUiState

@Composable
fun OthelloGame() {
    // Centralized UI state
    var uiState by remember { mutableStateOf(OthelloGameUiState.initial()) }
    val snackbarHostState = remember { SnackbarHostState() }
    var showSettingsDialog by remember { mutableStateOf(false) }

    // Check for game over condition
    GameOverEffect(
        state = uiState.gameState,
        snackbarHostState = snackbarHostState,
        onSnackbarDismissed = {
            // Reset game after user dismisses the snackbar
            uiState = OthelloGameUiState.initial()
        }
    )

    // Check for AI turn
    if (!showSettingsDialog) {
        AiTurnEffect(
            state = uiState.gameState,
            onNewState = { newState ->
                uiState = uiState.copy(gameState = newState)
            }
        )
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Game status
            GameStatus(
                currentPlayer = uiState.gameState.currentPlayer,
                blackScore = uiState.gameState.blackScore,
                whiteScore = uiState.gameState.whiteScore
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Game board
            OthelloBoard(
                board = uiState.gameState.board,
                onCellClick = { row, col ->
                    if (!uiState.gameState.isGameOver) {
                        // Use game logic to make a move
                        val newGameState = makeMove(state = uiState.gameState, row = row, col = col)
                        uiState = uiState.copy(gameState = newGameState)
                    }
                },
                enabled = !uiState.gameState.isGameOver
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Buttons row
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Reset button
                ResetButton(
                    onClick = {
                        uiState = OthelloGameUiState.initial()
                    }
                )

                // Settings button
                SettingButton(
                    onClick = {
                        showSettingsDialog = true
                    }
                )
            }
        }
    }

    // Settings dialog
    if (showSettingsDialog) {
        SettingsDialog(
            isBlackAI = uiState.gameState.isBlackAI,
            isWhiteAI = uiState.gameState.isWhiteAI,
            onBlackAIChange = { isAI ->
                uiState = uiState.copy(
                    gameState = uiState.gameState.copy(isBlackAI = isAI)
                )
            },
            onWhiteAIChange = { isAI ->
                uiState = uiState.copy(
                    gameState = uiState.gameState.copy(isWhiteAI = isAI)
                )
            },
            onDismiss = {
                showSettingsDialog = false
            }
        )
    }
}
