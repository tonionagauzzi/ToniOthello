package com.vitantonio.nagauzzi.toniothello.domain.entity

enum class Player(val cell: Cell) {
    BLACK(Cell.BLACK),
    WHITE(Cell.WHITE);
}

/**
 * Returns the opponent player.
 *
 * For BLACK player, returns WHITE.
 * For WHITE player, returns BLACK.
 *
 * @return The opposite player
 */
fun Player.opponent(): Player = when (this) {
    Player.BLACK -> Player.WHITE
    Player.WHITE -> Player.BLACK
}
