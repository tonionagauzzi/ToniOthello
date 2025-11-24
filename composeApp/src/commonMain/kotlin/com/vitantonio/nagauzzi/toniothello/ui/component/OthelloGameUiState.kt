package com.vitantonio.nagauzzi.toniothello.ui.component

data class OthelloGameUiState(
    val language: Language,
    val board: Array<Array<CellState>>,
    val currentPlayer: Player
) {
    // Calculate scores from board state
    val blackScore: Int
        get() = board.sumOf { row -> row.count { it == CellState.BLACK } }

    val whiteScore: Int
        get() = board.sumOf { row -> row.count { it == CellState.WHITE } }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as OthelloGameUiState

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
        fun initial(language: Language): OthelloGameUiState {
            return OthelloGameUiState(
                language = language,
                board = Array(8) { Array(8) { CellState.EMPTY } }.apply {
                    // Initial Othello setup
                    this[3][3] = CellState.WHITE
                    this[3][4] = CellState.BLACK
                    this[4][3] = CellState.BLACK
                    this[4][4] = CellState.WHITE
                },
                currentPlayer = Player.BLACK
            )
        }
    }
}
