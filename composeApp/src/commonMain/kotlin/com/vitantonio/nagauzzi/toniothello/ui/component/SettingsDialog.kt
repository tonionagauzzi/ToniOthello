package com.vitantonio.nagauzzi.toniothello.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import toniothello.composeapp.generated.resources.Res
import toniothello.composeapp.generated.resources.ai_mode_black
import toniothello.composeapp.generated.resources.ai_mode_white
import toniothello.composeapp.generated.resources.close
import toniothello.composeapp.generated.resources.settings_title

@Composable
fun SettingsDialog(
    isBlackAI: Boolean,
    isWhiteAI: Boolean,
    onBlackAIChange: (Boolean) -> Unit,
    onWhiteAIChange: (Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(Res.string.settings_title))
        },
        text = {
            Column {
                // Black AI checkbox
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isBlackAI,
                        onCheckedChange = onBlackAIChange
                    )
                    Text(text = stringResource(Res.string.ai_mode_black))
                }

                Spacer(modifier = Modifier.height(8.dp))

                // White AI checkbox
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isWhiteAI,
                        onCheckedChange = onWhiteAIChange
                    )
                    Text(text = stringResource(Res.string.ai_mode_white))
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(Res.string.close))
            }
        }
    )
}
