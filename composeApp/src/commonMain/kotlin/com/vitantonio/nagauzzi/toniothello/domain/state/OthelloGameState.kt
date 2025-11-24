package com.vitantonio.nagauzzi.toniothello.domain.state

import com.vitantonio.nagauzzi.toniothello.domain.entity.Cell
import com.vitantonio.nagauzzi.toniothello.domain.entity.Language
import com.vitantonio.nagauzzi.toniothello.domain.entity.Player
import com.vitantonio.nagauzzi.toniothello.domain.logic.getFlippedPositions
import com.vitantonio.nagauzzi.toniothello.platform.getSystemLanguage

data class OthelloGameState(
    val language: Language,
    val board: Array<Array<Cell>>,
    val currentPlayer: Player
) {
    // Calculate scores from board state
    val blackScore: Int
        get() = board.sumOf { row -> row.count { it == Cell.BLACK } }

    val whiteScore: Int
        get() = board.sumOf { row -> row.count { it == Cell.WHITE } }

    /**
     * Checks if a move is valid for the given player at the specified position.
     */
    fun isValidMove(row: Int, col: Int, player: Player): Boolean {
        if (row !in 0..7 || col !in 0..7) return false
        if (board[row][col] != Cell.EMPTY) return false

        return getFlippedPositions(board, row, col, player).isNotEmpty()
    }

    /**
     * Gets all valid moves for the given player on the board.
     */
    fun getValidMoves(player: Player): List<Pair<Int, Int>> {
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

    val isGameOver: Boolean
        get() = getValidMoves(Player.BLACK).isEmpty() && getValidMoves(Player.WHITE).isEmpty()

    /**
     * Determines the winner based on the piece count.
     * Returns the winning player, or null if it's a draw.
     */
    fun getWinner(): Player? {
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

    companion object Companion {
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
