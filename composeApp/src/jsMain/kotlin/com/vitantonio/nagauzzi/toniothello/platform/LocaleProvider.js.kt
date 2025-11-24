package com.vitantonio.nagauzzi.toniothello.platform

actual fun getSystemLanguage(): String {
    return js("navigator.language").toString().split("-").getOrNull(0) ?: "en"
}
