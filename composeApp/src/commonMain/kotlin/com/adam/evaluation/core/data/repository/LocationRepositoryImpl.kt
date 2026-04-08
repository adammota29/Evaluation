package com.adam.evaluation.core.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.adam.evaluation.core.data.local.AppDatabase
import com.adam.evaluation.core.data.local.LocationEntity
import com.adam.evaluation.core.data.remote.RickAndMortyApi
import com.adam.evaluation.core.domain.model.Location
import com.adam.evaluation.core.domain.repository.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocationRepositoryImpl(
    private val api: RickAndMortyApi,
    private val database: AppDatabase
) : LocationRepository {

    private val queries = database.locationEntityQueries

    override fun getLocations(): Flow<List<Location>> {
        return queries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities -> entities.map(LocationEntity::toDomain) }
    }

    override suspend fun getLocationById(id: Int): Location? {
        val entity = queries.selectById(id.toLong()).executeAsOneOrNull()
        return entity?.toDomain()
    }

    /**
     * 2. Cette fonction est le cœur de notre "Fetch".
     * Elle appelle l'API et sauvegarde le résultat dans la BDD.
     */
    suspend fun syncLocations() {
        try {
            // On fetch l'API
            val response = api.fetchLocations()

            // On sauvegarde chaque DTO dans la base de données (Cache)
            database.transaction { // Transaction pour des performances optimales
                response.results.forEach { dto ->
                    queries.insertLocation(
                        id = dto.id.toLong(),
                        name = dto.name,
                        type = dto.type,
                        dimension = dto.dimension,
                        // On extrait les IDs et on les joint avec une virgule pour le stockage
                        residentIds = dto.residentUrls.map { it.substringAfterLast("/") }.joinToString(","),
                        created = dto.created
                    )
                }
            }
        } catch (e: Exception) {
            // Gestion d'erreur (pas de réseau, etc.)
            e.printStackTrace()
        }
    }

    private fun LocationEntity.toDomain(): Location {
        return Location(
            id = id.toInt(),
            name = name,
            type = type,
            dimension = dimension,
            residentIds = if (residentIds.isBlank()) emptyList() else residentIds.split(","),
            created = created
        )
    }
}