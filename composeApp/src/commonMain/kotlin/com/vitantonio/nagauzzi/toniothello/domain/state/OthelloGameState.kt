package com.vitantonio.nagauzzi.toniothello.domain.state

import com.vitantonio.nagauzzi.toniothello.domain.entity.Cell
import com.vitantonio.nagauzzi.toniothello.domain.entity.Player

data class OthelloGameState(
    val board: List<List<Cell>>,
    val currentPlayer: Player,
    val isGameOver: Boolean = false
) {
    // Calculate scores from board state
    val blackScore: Int
        get() = board.sumOf { row -> row.count { it == Cell.BLACK } }

    val whiteScore: Int
        get() = board.sumOf { row -> row.count { it == Cell.WHITE } }

    /**
     * Determines the winner based on the piece count.
     * Returns the winning player, or null if it's a draw.
     */
    val winner: Player?
        get() {
            return when {
                blackScore > whiteScore -> Player.BLACK
                whiteScore > blackScore -> Player.WHITE
                else -> null  // Draw
            }
        }

    companion object {
        fun initial(): OthelloGameState {
            val board = List(8) { MutableList(8) { Cell.EMPTY } }.apply {
                // Initial Othello setup
                this[3][3] = Cell.WHITE
                this[3][4] = Cell.BLACK
                this[4][3] = Cell.BLACK
                this[4][4] = Cell.WHITE
            }.map { it.toList() }
            return OthelloGameState(
                board = board,
                currentPlayer = Player.BLACK
            )
        }
    }
}
