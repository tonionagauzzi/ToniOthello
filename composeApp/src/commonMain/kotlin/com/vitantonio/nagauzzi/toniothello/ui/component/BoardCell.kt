package com.vitantonio.nagauzzi.toniothello.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BoardCell(
    state: CellState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .border(BorderStroke(1.dp, Color.Black))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            CellState.BLACK -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(0.8f)
                        .clip(CircleShape)
                        .background(Color.Black)
                        .border(1.dp, Color.DarkGray, CircleShape)
                )
            }

            CellState.WHITE -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(0.8f)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(1.dp, Color.LightGray, CircleShape)
                )
            }

            CellState.EMPTY -> {
                // Empty cell - no piece
            }
        }
    }
}
