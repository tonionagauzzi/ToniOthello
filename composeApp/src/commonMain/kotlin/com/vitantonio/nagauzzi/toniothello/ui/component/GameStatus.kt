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
import com.vitantonio.nagauzzi.toniothello.ui.state.Language
import com.vitantonio.nagauzzi.toniothello.ui.state.Player

@Composable
fun GameStatus(
    currentPlayer: Player,
    blackScore: Int,
    whiteScore: Int,
    language: Language
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
                text = if (language == Language.JAPANESE) {
                    if (currentPlayer == Player.BLACK) {
                        "いまは くろ だよ"
                    } else {
                        "いまは しろ だよ"
                    }
                } else {
                    if (currentPlayer == Player.BLACK) {
                        "Now it's Black"
                    } else {
                        "Now it's White"
                    }
                },
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
                    label = if (language == Language.JAPANESE) "くろ" else "Black",
                    score = blackScore,
                    color = Color.Black,
                    language = language
                )
                ScoreDisplay(
                    label = if (language == Language.JAPANESE) "しろ" else "White",
                    score = whiteScore,
                    color = Color.White,
                    language = language
                )
            }
        }
    }
}
