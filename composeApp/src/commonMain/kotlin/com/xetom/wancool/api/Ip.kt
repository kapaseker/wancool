package com.xetom.wancool.api

import com.xetom.wancool.ext.isOk
import com.xetom.wancool.http.getHttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Ip(val origin: String)

object IpApi {

    private val client = getHttpClient()

    suspend fun currentIp(): String {
        val response = client.get("https://httpbin.org/ip")
        if (response.isOk()) {
            val ip = Json.decodeFromString<Ip>(response.bodyAsText())
            return ip.origin
        }
        return ""
    }
}