package com.vitantonio.nagauzzi.toniothello.domain.logic

import com.vitantonio.nagauzzi.toniothello.domain.entity.Cell
import com.vitantonio.nagauzzi.toniothello.domain.entity.Player
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
    val currentPlayer = state.currentPlayer
    val nextPlayer = state.currentPlayer.opponent()

    // If the move is invalid, return the same state
    if (!state.isValidMove(row = row, col = col, player = currentPlayer)) {
        return state
    }

    // Create the new board for the next state
    val newBoard = List(8) { r -> MutableList(8) { c -> state.board[r][c] } }
    val playerCell = currentPlayer.cell

    // Place the new piece
    newBoard[row][col] = playerCell

    // Flip all captured pieces
    val flippedPositions = getFlippedPositions(state.board, row, col, currentPlayer)
    for ((r, c) in flippedPositions) {
        newBoard[r][c] = playerCell
    }

    // Update state
    val newState = state.copy(
        board = newBoard,
        currentPlayer = nextPlayer
    )

    // Check if the next player has valid moves
    val nextPlayerMoves = newState.getValidMoves(nextPlayer)
    if (nextPlayerMoves.isEmpty()) {
        // Next player has no moves - check if current player can continue
        val currentPlayerMoves = newState.getValidMoves(currentPlayer)
        // If neither player has valid moves, game over; otherwise skip next player's turn
        return newState.copy(
            currentPlayer = currentPlayer,
            isGameOver = currentPlayerMoves.isEmpty()
        )
    }

    // Next player has valid moves - continue normally
    return newState
}

/**
 * Checks if a move is valid for the given player at the specified position.
 */
private fun OthelloGameState.isValidMove(row: Int, col: Int, player: Player): Boolean {
    if (row !in 0..7 || col !in 0..7) return false
    if (board[row][col] != Cell.EMPTY) return false

    return getFlippedPositions(board, row, col, player).isNotEmpty()
}

/**
 * Gets all valid moves for the given player on the board.
 */
private fun OthelloGameState.getValidMoves(player: Player): List<Pair<Int, Int>> {
    val validMoves = mutableListOf<Pair<Int, Int>>()
    for (row in 0..7) {
        for (col in 0..7) {
            if (isValidMove(row, col, player)) {
                validMoves.add(Pair(row, col))
            }
        }
    }
    return validMoves
}
