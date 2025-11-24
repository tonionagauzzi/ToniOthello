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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.toniothello.domain.logic.OthelloGameLogic
import com.vitantonio.nagauzzi.toniothello.domain.entity.Player
import org.jetbrains.compose.resources.stringResource
import toniothello.composeapp.generated.resources.Res
import toniothello.composeapp.generated.resources.new_game
import toniothello.composeapp.generated.resources.wins_player
import toniothello.composeapp.generated.resources.draw
import toniothello.composeapp.generated.resources.player_black
import toniothello.composeapp.generated.resources.player_white

@Composable
fun OthelloGame() {
    // Centralized UI state
    var state by remember { mutableStateOf(OthelloGameState.initial()) }
    val snackbarHostState = remember { SnackbarHostState() }

    // Get string resources outside of LaunchedEffect
    val blackWinsMessage = stringResource(Res.string.wins_player, stringResource(Res.string.player_black))
    val whiteWinsMessage = stringResource(Res.string.wins_player, stringResource(Res.string.player_white))
    val drawMessage = stringResource(Res.string.draw)

    // Check for game over condition
    LaunchedEffect(state.board, state.currentPlayer) {
        if (state.isGameOver) {
            val winner = state.getWinner()
            val message = when (winner) {
                Player.BLACK -> blackWinsMessage
                Player.WHITE -> whiteWinsMessage
                null -> drawMessage
            }
            val result = snackbarHostState.showSnackbar(
                message = message,
                actionLabel = "OK",
                withDismissAction = true
            )
            // Reset game after user dismisses the snackbar
            state = OthelloGameState.initial()
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
                    state = OthelloGameLogic.makeMove(state = state, row = row, col = col)
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
