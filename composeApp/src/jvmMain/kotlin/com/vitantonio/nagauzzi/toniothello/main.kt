package com.vitantonio.nagauzzi.toniothello

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.vitantonio.nagauzzi.toniothello.ui.component.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "ToniOthello",
    ) {
        App()
    }
}