package com.vitantonio.nagauzzi.toniothello.ui.component

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import toniothello.composeapp.generated.resources.Res
import toniothello.composeapp.generated.resources.settings

@Composable
fun SettingButton(
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick
    ) {
        Text(stringResource(Res.string.settings))
    }
}
