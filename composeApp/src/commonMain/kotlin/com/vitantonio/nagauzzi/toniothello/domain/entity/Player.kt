package com.vitantonio.nagauzzi.toniothello.domain.entity

enum class Player {
    BLACK,
    WHITE
}

/**
 * Returns the opponent player.
 */
fun Player.opponent(): Player = when (this) {
    Player.BLACK -> Player.WHITE
    Player.WHITE -> Player.BLACK
}

/**
 * Converts Player to corresponding CellState.
 */
fun Player.toCellState(): CellState = when (this) {
    Player.BLACK -> CellState.BLACK
    Player.WHITE -> CellState.WHITE
}
