package com.xetom.wancool.api

import com.xetom.wancool.ext.isOk
import com.xetom.wancool.http.getHttpClient
import com.xetom.wancool.load.LoadData
import io.ktor.client.request.get
import io.ktor.client.request.head
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

private const val CAT_URL = "https://api.thecatapi.com/v1/"
private const val KEY = "live_XBl3KG5gkXiGo5muxgHxCO2HuzphOCphU0UFMCwrBywhYNb3wa0IdRV84uKwdJG5"
@Serializable
data class CatBreed(
    val weight: Weight,
    val id: String = "",
    val name: String = "",
    @SerialName("cfa_url") val cfaUrl: String = "",
    @SerialName("vetstreet_url") val vetstreetUrl: String = "",
    @SerialName("vcahospitals_url") val vcahospitalsUrl: String = "",
    val temperament: String = "",
    val origin: String = "",
    @SerialName("country_codes") val countryCodes: String = "",
    @SerialName("country_code") val countryCode: String = "",
    val description: String = "",
    @SerialName("life_span") val lifeSpan: String = "",
    val indoor: Int,
    val lap: Int? = null,
    @SerialName("alt_names") val altNames: String = "",
    val adaptability: Int,
    @SerialName("affection_level") val affectionLevel: Int,
    @SerialName("cat_friendly") val catFriendly: Int = 0,
    @SerialName("child_friendly") val childFriendly: Int,
    @SerialName("dog_friendly") val dogFriendly: Int,
    @SerialName("energy_level") val energyLevel: Int,
    val grooming: Int,
    @SerialName("health_issues") val healthIssues: Int,
    val intelligence: Int,
    @SerialName("shedding_level") val sheddingLevel: Int,
    @SerialName("social_needs") val socialNeeds: Int,
    @SerialName("stranger_friendly") val strangerFriendly: Int,
    val vocalisation: Int,
    val bidability: Int = 0,
    val experimental: Int,
    val hairless: Int,
    val natural: Int,
    val rare: Int,
    val rex: Int,
    @SerialName("suppressed_tail") val suppressedTail: Int,
    @SerialName("short_legs") val shortLegs: Int,
    @SerialName("wikipedia_url") val wikipediaUrl: String = "",
    val hypoallergenic: Int,
    @SerialName("reference_image_id") val referenceImageId: String = "",
    val image: Image ? = null,
)

@Serializable
data class Weight(
    val imperial: String = "",
    val metric: String = "",
)

@Serializable
data class Image(
    val id: String = "",
    val width: Int,
    val height: Int,
    val url: String = "",
)

object CatApi {

    val client = getHttpClient()

    suspend fun breeds(): Pair<LoadData.Result, List<CatBreed>> {
        val response = client.get("${CAT_URL}breeds?limit=1000&page=0") {
            headers {
                append("Content-Type", "application/json")
                append("x-api-key", KEY)
            }
        }
        if (response.isOk()) {
            val breed = Json {
                ignoreUnknownKeys = true
            }.decodeFromString<List<CatBreed>>(response.bodyAsText())
            return LoadData.Success to breed
        }
        return LoadData.Error to emptyList()
    }

}