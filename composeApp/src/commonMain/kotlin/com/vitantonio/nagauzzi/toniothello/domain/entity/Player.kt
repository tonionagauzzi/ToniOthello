package com.vitantonio.nagauzzi.toniothello.domain.entity

enum class Player(val cell: Cell) {
    BLACK(cell = Cell.BLACK),
    WHITE(cell = Cell.WHITE);
}

/**
 * Returns the opponent player.
 */
fun Player.opponent(): Player = when (this) {
    Player.BLACK -> Player.WHITE
    Player.WHITE -> Player.BLACK
}
