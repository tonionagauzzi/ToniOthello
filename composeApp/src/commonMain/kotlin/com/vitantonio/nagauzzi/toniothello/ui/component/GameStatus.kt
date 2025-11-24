package com.vitantonio.nagauzzi.toniothello.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vitantonio.nagauzzi.toniothello.domain.entity.Player
import org.jetbrains.compose.resources.stringResource
import toniothello.composeapp.generated.resources.Res
import toniothello.composeapp.generated.resources.current_player
import toniothello.composeapp.generated.resources.player_black
import toniothello.composeapp.generated.resources.player_white

@Composable
fun GameStatus(
    currentPlayer: Player,
    blackScore: Int,
    whiteScore: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(
                    Res.string.current_player,
                    stringResource(
                        if (currentPlayer == Player.BLACK) {
                            Res.string.player_black
                        } else {
                            Res.string.player_white
                        }
                    )
                ),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ScoreDisplay(
                    label = stringResource(Res.string.player_black),
                    score = blackScore,
                    color = Color.Black
                )
                ScoreDisplay(
                    label = stringResource(Res.string.player_white),
                    score = whiteScore,
                    color = Color.White
                )
            }
        }
    }
}
