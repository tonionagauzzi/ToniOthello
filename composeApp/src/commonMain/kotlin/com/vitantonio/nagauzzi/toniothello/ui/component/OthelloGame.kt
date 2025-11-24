package com.vitantonio.nagauzzi.toniothello.ui.component

import androidx.compose.foundation.layout.Arrangement
import com.vitantonio.nagauzzi.toniothello.domain.state.OthelloGameState
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.toniothello.domain.logic.makeMove
import org.jetbrains.compose.resources.stringResource
import toniothello.composeapp.generated.resources.Res
import toniothello.composeapp.generated.resources.new_game

@Composable
fun OthelloGame() {
    // Centralized UI state
    var state by remember { mutableStateOf(OthelloGameState.initial()) }
    val snackbarHostState = remember { SnackbarHostState() }

    // Check for game over condition
    GameOverEffect(
        state = state,
        snackbarHostState = snackbarHostState,
        onSnackbarDismissed = {
            // Reset game after user dismisses the snackbar
            state = OthelloGameState.initial()
        }
    )

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
                currentPlayer = state.currentPlayer,
                blackScore = state.blackScore,
                whiteScore = state.whiteScore
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Game board
            OthelloBoard(
                board = state.board,
                onCellClick = { row, col ->
                    // Use game logic to make a move
                    state = makeMove(state = state, row = row, col = col)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Reset button
            Button(
                onClick = {
                    state = OthelloGameState.initial()
                }
            ) {
                Text(stringResource(Res.string.new_game))
            }
        }
    }
}
