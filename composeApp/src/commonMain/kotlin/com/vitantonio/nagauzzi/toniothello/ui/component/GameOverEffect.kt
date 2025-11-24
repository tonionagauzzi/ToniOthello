package com.vitantonio.nagauzzi.toniothello.ui.component

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.vitantonio.nagauzzi.toniothello.domain.entity.Player
import com.vitantonio.nagauzzi.toniothello.domain.state.OthelloGameState
import org.jetbrains.compose.resources.stringResource
import toniothello.composeapp.generated.resources.Res
import toniothello.composeapp.generated.resources.draw
import toniothello.composeapp.generated.resources.player_black
import toniothello.composeapp.generated.resources.player_white
import toniothello.composeapp.generated.resources.wins_player

@Composable
fun GameOverEffect(
    state: OthelloGameState,
    snackbarHostState: SnackbarHostState,
    onSnackbarDismissed: () -> Unit
) {
    // Get string resources outside of LaunchedEffect
    val blackWinsMessage = stringResource(
        resource = Res.string.wins_player, stringResource(Res.string.player_black)
    )
    val whiteWinsMessage = stringResource(
        resource = Res.string.wins_player, stringResource(Res.string.player_white)
    )
    val drawMessage = stringResource(resource = Res.string.draw)

    LaunchedEffect(state.isGameOver) {
        if (state.isGameOver) {
            val message = when (state.winner) {
                Player.BLACK -> blackWinsMessage
                Player.WHITE -> whiteWinsMessage
                null -> drawMessage
            }
            snackbarHostState.showSnackbar(message = message, withDismissAction = true)
            onSnackbarDismissed()
        }
    }
}
