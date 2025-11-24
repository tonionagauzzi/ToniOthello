package com.vitantonio.nagauzzi.toniothello.platform

import java.util.Locale

actual fun getSystemLanguage(): String {
    return Locale.getDefault().language
}
