package com.vitantonio.nagauzzi.toniothello

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform