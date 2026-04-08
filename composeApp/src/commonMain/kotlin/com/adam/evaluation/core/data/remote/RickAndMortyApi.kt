package com.adam.evaluation.core.data.remote

import com.adam.evaluation.core.data.remote.model.LocationResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class RickAndMortyApi(
    private val httpClient: HttpClient // Sera injecté par Koin plus tard
) {
    suspend fun fetchLocations(page: Int = 1): LocationResponseDto {
        // Ktor va automatiquement désérialiser le JSON en LocationResponseDto
        return httpClient.get("https://rickandmortyapi.com/api/location/?page=$page").body()
    }

    // Tu pourras ajouter fetchLocationById ici plus tard
}