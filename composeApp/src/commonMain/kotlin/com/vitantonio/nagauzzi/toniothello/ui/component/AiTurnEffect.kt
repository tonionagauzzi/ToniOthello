package com.vitantonio.nagauzzi.toniothello.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.vitantonio.nagauzzi.toniothello.domain.logic.makeMove
import com.vitantonio.nagauzzi.toniothello.domain.logic.selectAiMove
import com.vitantonio.nagauzzi.toniothello.domain.state.OthelloGameState
import kotlinx.coroutines.delay

@Composable
fun AiTurnEffect(
    state: OthelloGameState,
    onNewState: (OthelloGameState) -> Unit
) {
    LaunchedEffect(
        state.currentPlayer,
        state.isCurrentPlayerAI,
        state.isGameOver
    ) {
        if (!state.isGameOver && state.isCurrentPlayerAI) {
            // Add a small delay to make the AI move visible
            delay(500)
            val aiMove = selectAiMove(state)
            if (aiMove != null) {
                val (row, col) = aiMove
                val newState = makeMove(state = state, row = row, col = col)
                onNewState(newState)
            }
        }
    }
}
