package com.vitantonio.nagauzzi.toniothello.domain.logic

import com.vitantonio.nagauzzi.toniothello.domain.entity.opponent
import com.vitantonio.nagauzzi.toniothello.domain.state.OthelloGameState

/**
 * Makes a move at the specified position for the current player.
 *
 * @param state The current game state.
 * @param row The row index where the piece is placed.
 * @param col The column index where the piece is placed.
 * @return The new game state after the move, or the same state if the move was invalid.
 */
fun makeMove(
    state: OthelloGameState,
    row: Int,
    col: Int
): OthelloGameState {
    val player = state.currentPlayer

    // If the move is invalid, return the same state
    if (!state.isValidMove(row = row, col = col, player = player)) {
        return state
    }

    // Create the new board for the next state
    val newBoard = Array(size = 8) { r -> Array(size = 8) { c -> state.board[r][c] } }
    val playerCell = player.cell

    // Place the new piece
    newBoard[row][col] = playerCell

    // Flip all captured pieces
    val flippedPositions = getFlippedPositions(state.board, row, col, player)
    for ((r, c) in flippedPositions) {
        newBoard[r][c] = playerCell
    }

    // Update state
    val nextPlayer = state.currentPlayer.opponent()
    val newState = state.copy(
        board = newBoard,
        currentPlayer = nextPlayer
    )

    // If the next player has no valid moves, skip their turn
    val nextPlayerMoves = newState.getValidMoves(nextPlayer)
    if (nextPlayerMoves.isEmpty()) {
        // Check if the current player can continue
        val currentPlayerMoves = newState.getValidMoves(state.currentPlayer)
        if (currentPlayerMoves.isNotEmpty()) {
            val nextPlayer = state.currentPlayer
            return newState.copy(currentPlayer = nextPlayer)
        }
    }

    return newState
}

