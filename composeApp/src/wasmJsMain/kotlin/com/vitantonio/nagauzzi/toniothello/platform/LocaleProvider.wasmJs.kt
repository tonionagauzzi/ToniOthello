package com.vitantonio.nagauzzi.toniothello.platform

import kotlinx.browser.window

actual fun getSystemLanguage(): String {
    return window.navigator.language.split("-").getOrNull(0) ?: "en"
}
