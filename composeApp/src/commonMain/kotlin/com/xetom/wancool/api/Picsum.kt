package com.xetom.wancool.api

import com.xetom.wancool.ext.isOk
import com.xetom.wancool.http.getHttpClient
import com.xetom.wancool.load.LoadData
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


private const val PICSUM_URL = "https://picsum.photos/v2/list"

@Serializable
data class PicsumItem(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    @SerialName("download_url")
    val downloadUrl: String,
)

object PicsumApi {

    private val client = getHttpClient()

    /**
     * list pics, page is start by 1
     */
    suspend fun list(page: Int, size: Int): Pair<LoadData.Result, List<PicsumItem>> {
        val response = client.get {
            url(PICSUM_URL)
            parameter("page", page)
            parameter("limit", size)
        }
        if (response.isOk()) {
            val pictures = Json.decodeFromString<List<PicsumItem>>(response.bodyAsText())
            return LoadData.Success to pictures
        }
        return LoadData.Error to emptyList()
    }
}