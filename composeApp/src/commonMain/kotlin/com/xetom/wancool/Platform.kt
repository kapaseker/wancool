package com.xetom.wancool

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform