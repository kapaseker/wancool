package com.xetom.wancool.api

import com.xetom.wancool.ext.isOk
import com.xetom.wancool.http.getHttpClient
import com.xetom.wancool.load.LoadData
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.jvm.JvmStatic


private const val NARUTO_URL = "https://dattebayo-api.onrender.com"


@Serializable
data class Characters(
    val characters: List<Character> = emptyList<Character>(),
    val currentPage: Int = 0,
    val pageSize: Int = 0,
    val total: Int = 0,
) {
    companion object {
        private val EMPTY = Characters()

        @JvmStatic
        fun empty() = EMPTY
    }
}


@Serializable
data class Character(
    var id: Int = 0,
    var name: String = "",
    var images: List<String> = emptyList<String>(),
)

object NarutoApi {

    private val client = getHttpClient()
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun characters(page:Int, limit:Int): Pair<LoadData.Result, Characters> {
        val response = client.get {
            url("$NARUTO_URL/characters")
            parameter("page", page)
            parameter("limit", limit)
        }
        if (response.isOk()) {
            val naruto = json.decodeFromString<Characters>(response.bodyAsText())
            return LoadData.Success to naruto
        }
        return LoadData.Error to Characters.empty()
    }
}