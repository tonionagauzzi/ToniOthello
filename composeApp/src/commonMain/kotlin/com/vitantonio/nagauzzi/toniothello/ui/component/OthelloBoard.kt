package com.vitantonio.nagauzzi.toniothello.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun OthelloBoard(
    board: Array<Array<CellState>>,
    onCellClick: (Int, Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF228B22))
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            for (row in 0..7) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    for (col in 0..7) {
                        BoardCell(
                            state = board[row][col],
                            onClick = { onCellClick(row, col) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}
