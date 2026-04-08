package com.adam.evaluation.core.data.remote

import com.adam.evaluation.core.data.remote.model.LocationResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

/**
 * Client d'accès à l'API Rick and Morty pour les localisations.
 *
 * @property httpClient Client HTTP utilisé pour exécuter les requêtes réseau.
 */
class RickAndMortyApi(
    private val httpClient: HttpClient
) {
    /**
     * Récupère une page de localisations depuis l'API distante.
     *
     * @param page Numéro de page demandé (base 1).
     * @return Une [LocationResponseDto] contenant les informations de pagination et les résultats.
     */
    suspend fun fetchLocations(page: Int = 1): LocationResponseDto {
        return httpClient.get("https://rickandmortyapi.com/api/location/?page=$page").body()
    }
}