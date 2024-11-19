package com.xetom.wancool.api

import com.xetom.wancool.ext.isOk
import com.xetom.wancool.http.getHttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


private const val DOG_URL = "https://dog.ceo/api/"

@Serializable
data class DogBreed(val message: Map<String, Array<String>>, val status: String)

object DogApi {

    val client = getHttpClient()

    suspend fun breeds(): Map<String, Array<String>> {
        val response = client.get("${DOG_URL}breeds/list/all")
        if (response.isOk()) {
            val breed = Json.decodeFromString<DogBreed>(response.bodyAsText())
            return breed.message
        }
        return mapOf()
    }
}