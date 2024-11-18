package com.xetom.wancool.api

import io.ktor.client.*
import io.ktor.client.engine.js.*

private val jsClient = HttpClient(Js)

actual fun getHttpClient(): HttpClient {
    return jsClient
}