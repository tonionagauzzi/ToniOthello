package com.vitantonio.nagauzzi.toniothello.domain.state

import com.vitantonio.nagauzzi.toniothello.domain.entity.Cell
import com.vitantonio.nagauzzi.toniothello.domain.entity.Language
import com.vitantonio.nagauzzi.toniothello.domain.entity.Player
import com.vitantonio.nagauzzi.toniothello.platform.getSystemLanguage

data class OthelloGameState(
    val language: Language,
    val board: Array<Array<Cell>>,
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
            val blackCount = board.sumOf { row -> row.count { it == Cell.BLACK } }
            val whiteCount = board.sumOf { row -> row.count { it == Cell.WHITE } }

            return when {
                blackCount > whiteCount -> Player.BLACK
                whiteCount > blackCount -> Player.WHITE
                else -> null  // Draw
            }
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as OthelloGameState

        if (language != other.language) return false
        if (!board.contentDeepEquals(other.board)) return false
        if (currentPlayer != other.currentPlayer) return false

        return true
    }

    override fun hashCode(): Int {
        var result = language.hashCode()
        result = 31 * result + board.contentDeepHashCode()
        result = 31 * result + currentPlayer.hashCode()
        return result
    }

    companion object {
        fun initial(): OthelloGameState {
            val language = if (getSystemLanguage().startsWith("ja")) {
                Language.JAPANESE
            } else {
                Language.ENGLISH
            }
            return OthelloGameState(
                language = language,
                board = Array(8) { Array(8) { Cell.EMPTY } }.apply {
                    // Initial Othello setup
                    this[3][3] = Cell.WHITE
                    this[3][4] = Cell.BLACK
                    this[4][3] = Cell.BLACK
                    this[4][4] = Cell.WHITE
                },
                currentPlayer = Player.BLACK
            )
        }
    }
}
