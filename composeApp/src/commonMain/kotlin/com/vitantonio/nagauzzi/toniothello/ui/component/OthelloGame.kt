package com.vitantonio.nagauzzi.toniothello.ui.component

import androidx.compose.foundation.layout.Arrangement
import com.vitantonio.nagauzzi.toniothello.ui.state.OthelloGameUiState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.toniothello.domain.logic.OthelloGameLogic
import com.vitantonio.nagauzzi.toniothello.ui.state.CellState
import com.vitantonio.nagauzzi.toniothello.ui.state.Player
import com.vitantonio.nagauzzi.toniothello.ui.state.opponent
import org.jetbrains.compose.resources.stringResource
import toniothello.composeapp.generated.resources.Res
import toniothello.composeapp.generated.resources.new_game

@Composable
fun OthelloGame() {
    // Centralized UI state
    var uiState by remember { mutableStateOf(OthelloGameUiState.initial()) }
    val snackbarHostState = remember { SnackbarHostState() }

    // Check for game over condition
    LaunchedEffect(uiState.board, uiState.currentPlayer) {
        if (OthelloGameLogic.isGameOver(uiState.board)) {
            val winner = OthelloGameLogic.getWinner(uiState.board)
            val message = when (winner) {
                Player.BLACK -> if (uiState.language == com.vitantonio.nagauzzi.toniothello.ui.state.Language.JAPANESE) {
                    "くろ の かち！"
                } else {
                    "Black wins!"
                }
                Player.WHITE -> if (uiState.language == com.vitantonio.nagauzzi.toniothello.ui.state.Language.JAPANESE) {
                    "しろ の かち！"
                } else {
                    "White wins!"
                }
                null -> if (uiState.language == com.vitantonio.nagauzzi.toniothello.ui.state.Language.JAPANESE) {
                    "ひきわけ！"
                } else {
                    "It's a draw!"
                }
            }
            val result = snackbarHostState.showSnackbar(
                message = message,
                actionLabel = "OK",
                withDismissAction = true
            )
            // Reset game after user dismisses the snackbar
            uiState = OthelloGameUiState.initial()
        }
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
                currentPlayer = uiState.currentPlayer,
                blackScore = uiState.blackScore,
                whiteScore = uiState.whiteScore
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Game board
            OthelloBoard(
                board = uiState.board,
                onCellClick = { row, col ->
                    // Use game logic to make a move
                    val newBoard = OthelloGameLogic.makeMove(
                        uiState.board,
                        row,
                        col,
                        uiState.currentPlayer
                    )

                    if (newBoard != null) {
                        // Move was valid, update state
                        var nextPlayer = uiState.currentPlayer.opponent()

                        // If the next player has no valid moves, skip their turn
                        if (OthelloGameLogic.getValidMoves(newBoard, nextPlayer).isEmpty()) {
                            // Check if the current player can continue
                            if (OthelloGameLogic.getValidMoves(newBoard, uiState.currentPlayer).isNotEmpty()) {
                                nextPlayer = uiState.currentPlayer
                            }
                        }

                        uiState = uiState.copy(
                            board = newBoard,
                            currentPlayer = nextPlayer
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Reset button
            Button(
                onClick = {
                    uiState = OthelloGameUiState.initial()
                }
            ) {
                Text(stringResource(Res.string.new_game))
            }
        }
    }
}
