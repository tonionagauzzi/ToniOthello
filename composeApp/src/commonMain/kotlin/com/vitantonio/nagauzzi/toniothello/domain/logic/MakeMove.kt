package com.vitantonio.nagauzzi.toniothello.domain.logic

import com.vitantonio.nagauzzi.toniothello.domain.entity.Cell
import com.vitantonio.nagauzzi.toniothello.domain.entity.Player
import com.vitantonio.nagauzzi.toniothello.domain.entity.opponent
import com.vitantonio.nagauzzi.toniothello.domain.state.OthelloGameState

private val DIRECTIONS = listOf(
    // Top-left, Top, Top-right
    Pair(-1, -1), Pair(-1, 0), Pair(-1, 1),

    // Left, Right
    Pair(0, -1), Pair(0, 1),

    // Bottom-left, Bottom, Bottom-right
    Pair(1, -1), Pair(1, 0), Pair(1, 1)
)

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
        board = newBoard.map { it.toList() },
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
 * Gets all positions that would be flipped if a piece is placed at the specified position.
 *
 * @param board The current game board.
 * @param row The row index where the piece is placed.
 * @param col The column index where the piece is placed.
 * @param player The player making the move.
 * @return A list of positions (row, col) that would be flipped.
 */
private fun getFlippedPositions(
    board: List<List<Cell>>,
    row: Int,
    col: Int,
    player: Player
): List<Pair<Int, Int>> {
    val playerCell = player.cell
    val opponentCell = player.opponent().cell
    val flippedPositions = mutableListOf<Pair<Int, Int>>()

    for ((dr, dc) in DIRECTIONS) {
        val currentFlipped = mutableListOf<Pair<Int, Int>>()
        var r = row + dr
        var c = col + dc

        // Check if there's at least one opponent piece in this direction
        while (r in 0..7 && c in 0..7 && board[r][c] == opponentCell) {
            currentFlipped.add(Pair(r, c))
            r += dr
            c += dc
        }

        // If we found opponent pieces and ended with our own piece, add them to flipped list
        if (currentFlipped.isNotEmpty() && r in 0..7 && c in 0..7 && board[r][c] == playerCell) {
            flippedPositions.addAll(currentFlipped)
        }
    }

    return flippedPositions
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
