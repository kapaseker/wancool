package com.xetom.wancool.api

import com.xetom.wancool.ext.isOk
import com.xetom.wancool.http.getHttpClient
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

private const val IP_URL = "https://httpbin.org/"

@Serializable
data class Ip(val origin: String)

object IpApi {

    private val client = getHttpClient()

    suspend fun currentIp(): String {
        val response = client.get("${IP_URL}ip")
        if (response.isOk()) {
            val ip = Json.decodeFromString<Ip>(response.bodyAsText())
            return ip.origin
        }
        return ""
    }
}