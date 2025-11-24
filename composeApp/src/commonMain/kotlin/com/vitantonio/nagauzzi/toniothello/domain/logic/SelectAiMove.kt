package com.vitantonio.nagauzzi.toniothello.domain.logic

import com.vitantonio.nagauzzi.toniothello.domain.state.OthelloGameState

/**
 * Selects the best move for the AI player using a greedy strategy.
 * The AI chooses the move that flips the most opponent pieces.
 *
 * @param state The current game state.
 * @return A pair of (row, col) representing the chosen move, or null if no valid moves exist.
 */
fun selectAiMove(state: OthelloGameState): Pair<Int, Int>? {
    val validMoves = state.getValidMovesWithFlipCount()

    if (validMoves.isEmpty()) {
        return null
    }

    // Select the move that flips the most pieces
    // If there are multiple moves with the same flip count, pick the first one
    return validMoves.maxByOrNull { it.third }?.let { (row, col, _) ->
        Pair(row, col)
    }
}
