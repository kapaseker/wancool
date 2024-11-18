package com.xetom.wancool.api

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*

private val javaClient = HttpClient(OkHttp)

actual fun getHttpClient(): HttpClient {
    return javaClient
}