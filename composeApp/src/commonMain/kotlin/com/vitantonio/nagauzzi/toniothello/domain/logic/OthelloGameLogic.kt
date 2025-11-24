package com.vitantonio.nagauzzi.toniothello.domain.logic

import com.vitantonio.nagauzzi.toniothello.domain.entity.CellState
import com.vitantonio.nagauzzi.toniothello.domain.entity.Player
import com.vitantonio.nagauzzi.toniothello.domain.entity.opponent
import com.vitantonio.nagauzzi.toniothello.domain.state.OthelloGameState

object OthelloGameLogic {
    private val DIRECTIONS = listOf(
        Pair(-1, -1), Pair(-1, 0), Pair(-1, 1),  // Top-left, Top, Top-right
        Pair(0, -1),               Pair(0, 1),   // Left, Right
        Pair(1, -1),  Pair(1, 0),  Pair(1, 1)    // Bottom-left, Bottom, Bottom-right
    )

    /**
     * Gets all positions that would be flipped if a piece is placed at the specified position.
     */
    fun getFlippedPositions(
        board: Array<Array<CellState>>,
        row: Int,
        col: Int,
        player: Player
    ): List<Pair<Int, Int>> {
        val playerCell = player.toCellState()
        val opponentCell = player.opponent().toCellState()
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
     * Makes a move at the specified position for the given player.
     * Returns a new board with the move applied, or null if the move is invalid.
     */
    fun makeMove(
        state: OthelloGameState,
        row: Int,
        col: Int
    ): OthelloGameState {
        val player = state.currentPlayer

        if (!state.isValidMove(row = row, col = col, player = player)) {
            return state
        }

        val newBoard = Array(size = 8) { r -> Array(size = 8) { c -> state.board[r][c] } }
        val playerCell = player.toCellState()
        val flippedPositions = getFlippedPositions(state.board, row, col, player)

        // Place the new piece
        newBoard[row][col] = playerCell

        // Flip all captured pieces
        for ((r, c) in flippedPositions) {
            newBoard[r][c] = playerCell
        }

        // Move was valid, update state
        val nextPlayer = state.currentPlayer.opponent()
        val newState = state.copy(
            board = newBoard,
            currentPlayer = nextPlayer
        )

        // If the next player has no valid moves, skip their turn
        if (newState.getValidMoves(nextPlayer).isEmpty()) {
            // Check if the current player can continue
            if (newState.getValidMoves(state.currentPlayer).isNotEmpty()) {
                val nextPlayer = state.currentPlayer
                return newState.copy(currentPlayer = nextPlayer)
            }
        }

        return newState
    }

    /**
     * Gets the score for a specific player.
     */
    fun getScore(board: Array<Array<CellState>>, player: Player): Int {
        val cellState = player.toCellState()
        return board.sumOf { row -> row.count { it == cellState } }
    }

    // Extension function for convenience
    private fun Player.toCellState(): CellState = when (this) {
        Player.BLACK -> CellState.BLACK
        Player.WHITE -> CellState.WHITE
    }
}
