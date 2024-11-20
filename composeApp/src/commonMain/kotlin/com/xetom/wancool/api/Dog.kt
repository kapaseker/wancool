package com.xetom.wancool.api

import com.xetom.wancool.ext.isOk
import com.xetom.wancool.http.getHttpClient
import com.xetom.wancool.load.LoadData
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


private const val DOG_URL = "https://dog.ceo/api/"

@Serializable
data class DogBreed(val message: Map<String, Array<String>>, val status: String)

@Serializable
data class Dogs(val message: Array<String>, val status: String)

object DogApi {

    val client = getHttpClient()

    suspend fun breeds(): Pair<LoadData.Result, Map<String, Array<String>>> {
        val response = client.get("${DOG_URL}breeds/list/all")
        if (response.isOk()) {
            val breed = Json.decodeFromString<DogBreed>(response.bodyAsText())
            return LoadData.Success to breed.message
        }
        return LoadData.Error to emptyMap()
    }

    suspend fun mainDogs(main: String): Pair<LoadData.Result, Array<String>> {
        val response = client.get("${DOG_URL}breed/$main/images")
        if (response.isOk()) {
            val breed = Json.decodeFromString<Dogs>(response.bodyAsText())
            return LoadData.Success to breed.message
        }
        return LoadData.Error to emptyArray()
    }

    suspend fun subDogs(main: String, sub: String): Pair<LoadData.Result, Array<String>> {
        val response = client.get("${DOG_URL}breed/$main/$sub/images")
        if (response.isOk()) {
            val breed = Json.decodeFromString<Dogs>(response.bodyAsText())
            return LoadData.Success to breed.message
        }
        return LoadData.Error to emptyArray()
    }
}