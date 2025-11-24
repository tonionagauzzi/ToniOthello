package com.vitantonio.nagauzzi.toniothello.ui.state

import com.vitantonio.nagauzzi.toniothello.domain.entity.Language
import com.vitantonio.nagauzzi.toniothello.domain.state.OthelloGameState
import com.vitantonio.nagauzzi.toniothello.platform.getSystemLanguage

/**
 * UI state for the Othello game.
 * This contains UI-specific concerns like language/localization,
 * while delegating game logic to the domain state.
 */
data class OthelloGameUiState(
    val language: Language,
    val gameState: OthelloGameState
) {
    companion object {
        fun initial(): OthelloGameUiState {
            val language = if (getSystemLanguage().startsWith("ja")) {
                Language.JAPANESE
            } else {
                Language.ENGLISH
            }
            return OthelloGameUiState(
                language = language,
                gameState = OthelloGameState.initial()
            )
        }
    }
}
