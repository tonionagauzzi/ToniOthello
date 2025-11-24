package com.vitantonio.nagauzzi.toniothello.domain.logic

import com.vitantonio.nagauzzi.toniothello.ui.state.CellState
import com.vitantonio.nagauzzi.toniothello.ui.state.Player

object OthelloGameLogic {
    private val DIRECTIONS = listOf(
        Pair(-1, -1), Pair(-1, 0), Pair(-1, 1),  // Top-left, Top, Top-right
        Pair(0, -1),               Pair(0, 1),   // Left, Right
        Pair(1, -1),  Pair(1, 0),  Pair(1, 1)    // Bottom-left, Bottom, Bottom-right
    )

    /**
     * Checks if a move is valid for the given player at the specified position.
     */
    fun isValidMove(board: Array<Array<CellState>>, row: Int, col: Int, player: Player): Boolean {
        if (row !in 0..7 || col !in 0..7) return false
        if (board[row][col] != CellState.EMPTY) return false

        return getFlippedPositions(board, row, col, player).isNotEmpty()
    }

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
        board: Array<Array<CellState>>,
        row: Int,
        col: Int,
        player: Player
    ): Array<Array<CellState>>? {
        if (!isValidMove(board, row, col, player)) return null

        val newBoard = Array(8) { r -> Array(8) { c -> board[r][c] } }
        val playerCell = player.toCellState()
        val flippedPositions = getFlippedPositions(board, row, col, player)

        // Place the new piece
        newBoard[row][col] = playerCell

        // Flip all captured pieces
        for ((r, c) in flippedPositions) {
            newBoard[r][c] = playerCell
        }

        return newBoard
    }

    /**
     * Gets all valid moves for the given player on the board.
     */
    fun getValidMoves(board: Array<Array<CellState>>, player: Player): List<Pair<Int, Int>> {
        val validMoves = mutableListOf<Pair<Int, Int>>()
        for (row in 0..7) {
            for (col in 0..7) {
                if (isValidMove(board, row, col, player)) {
                    validMoves.add(Pair(row, col))
                }
            }
        }
        return validMoves
    }

    /**
     * Checks if the game is over (no valid moves for either player).
     */
    fun isGameOver(board: Array<Array<CellState>>): Boolean {
        return getValidMoves(board, Player.BLACK).isEmpty() &&
               getValidMoves(board, Player.WHITE).isEmpty()
    }

    /**
     * Determines the winner based on the piece count.
     * Returns the winning player, or null if it's a draw.
     */
    fun getWinner(board: Array<Array<CellState>>): Player? {
        val blackCount = board.sumOf { row -> row.count { it == CellState.BLACK } }
        val whiteCount = board.sumOf { row -> row.count { it == CellState.WHITE } }

        return when {
            blackCount > whiteCount -> Player.BLACK
            whiteCount > blackCount -> Player.WHITE
            else -> null  // Draw
        }
    }

    /**
     * Gets the score for a specific player.
     */
    fun getScore(board: Array<Array<CellState>>, player: Player): Int {
        val cellState = player.toCellState()
        return board.sumOf { row -> row.count { it == cellState } }
    }

    // Extension functions for convenience
    private fun Player.toCellState(): CellState = when (this) {
        Player.BLACK -> CellState.BLACK
        Player.WHITE -> CellState.WHITE
    }

    private fun Player.opponent(): Player = when (this) {
        Player.BLACK -> Player.WHITE
        Player.WHITE -> Player.BLACK
    }
}
