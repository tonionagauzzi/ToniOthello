package com.vitantonio.nagauzzi.toniothello.ui.component

import androidx.compose.foundation.layout.Arrangement
import com.vitantonio.nagauzzi.toniothello.ui.state.OthelloGameUiState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OthelloGame() {
    // Centralized UI state
    var uiState by remember { mutableStateOf(OthelloGameUiState.initial()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Game status
        GameStatus(
            currentPlayer = uiState.currentPlayer,
            blackScore = uiState.blackScore,
            whiteScore = uiState.whiteScore,
            language = uiState.language
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Game board
        OthelloBoard(
            board = uiState.board,
            onCellClick = { row, col ->
                // Placeholder click handler (logic will be implemented later)
                if (uiState.board[row][col] == CellState.EMPTY) {
                    val newBoard = Array(8) { r -> Array(8) { c -> uiState.board[r][c] } }
                    newBoard[row][col] =
                        if (uiState.currentPlayer == Player.BLACK) CellState.BLACK else CellState.WHITE
                    uiState = uiState.copy(
                        board = newBoard,
                        currentPlayer = if (uiState.currentPlayer == Player.BLACK) Player.WHITE else Player.BLACK
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
            Text(if (uiState.language == Language.JAPANESE) "もういちど" else "New Game")
        }
    }
}
