package com.vitantonio.nagauzzi.toniothello.domain.logic

import com.vitantonio.nagauzzi.toniothello.domain.entity.Cell
import com.vitantonio.nagauzzi.toniothello.domain.entity.Player
import com.vitantonio.nagauzzi.toniothello.domain.entity.opponent

private val DIRECTIONS = listOf(
    // Top-left, Top, Top-right
    Pair(-1, -1), Pair(-1, 0), Pair(-1, 1),

    // Left, Right
    Pair(0, -1), Pair(0, 1),

    // Bottom-left, Bottom, Bottom-right
    Pair(1, -1), Pair(1, 0), Pair(1, 1)
)

/**
 * Gets all positions that would be flipped if a piece is placed at the specified position.
 *
 * @param board The current game board.
 * @param row The row index where the piece is placed.
 * @param col The column index where the piece is placed.
 * @param player The player making the move.
 * @return A list of positions (row, col) that would be flipped.
 */
fun getFlippedPositions(
    board: Array<Array<Cell>>,
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
