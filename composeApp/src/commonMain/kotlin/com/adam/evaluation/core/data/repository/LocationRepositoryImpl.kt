package com.adam.evaluation.core.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.adam.evaluation.core.data.local.AppDatabase
import com.adam.evaluation.core.data.local.LocationEntity
import com.adam.evaluation.core.data.remote.RickAndMortyApi
import com.adam.evaluation.core.domain.model.Location
import com.adam.evaluation.core.domain.repository.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implémentation data du dépôt des localisations.
 *
 * @property api Client distant utilisé pour rafraîchir les localisations.
 * @property database Base locale utilisée pour persister et lire les localisations.
 */
class LocationRepositoryImpl(
    private val api: RickAndMortyApi,
    private val database: AppDatabase
) : LocationRepository {

    private val queries = database.locationEntityQueries

    /**
     * Observe toutes les localisations stockées localement.
     *
     * @return Un [Flow] de listes de [Location] mises à jour depuis la base.
     */
    override fun getLocations(): Flow<List<Location>> {
        return queries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities -> entities.map { entity -> entity.toDomain() } }
    }

    override fun observeLocationById(id: Int): Flow<Location?> {
        return queries.selectById(id.toLong())
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .map { entity -> entity?.toDomain() }
    }

    /**
     * Récupère une localisation par son identifiant.
     *
     * @param id Identifiant de la localisation recherchée.
     * @return La [Location] correspondante, ou `null` si absente en base.
     */
    override suspend fun getLocationById(id: Int): Location? {
        val entity = queries.selectById(id.toLong()).executeAsOneOrNull()
        return entity?.toDomain()
    }

    /**
     * Rafraîchit les localisations locales depuis l'API distante.
     *
     * @return `Unit`.
     */
    override suspend fun refreshLocations() {
        val response = api.fetchLocations()

        database.transaction {
            response.results.forEach { dto ->
                queries.insertLocation(
                    id = dto.id.toLong(),
                    name = dto.name,
                    type = dto.type,
                    dimension = dto.dimension,
                    // Les résidents sont persistés en CSV pour rester compatibles avec un champ TEXT unique.
                    residentIds = dto.residentUrls.map { it.substringAfterLast("/") }.joinToString(","),
                    created = dto.created
                )
            }
        }
    }

    /**
     * Convertit une entité SQLDelight en modèle domaine.
     *
     * @receiver Entité locale à convertir.
     * @return Le modèle [Location] prêt pour la couche domaine.
     */
    private fun LocationEntity.toDomain(): Location {
        return Location(
            id = id.toInt(),
            name = name,
            type = type,
            dimension = dimension,
            // La colonne `residentIds` est stockée en CSV puis reconstruite en liste côté domaine.
            residentIds = if (residentIds.isBlank()) emptyList() else residentIds.split(","),
            created = created
        )
    }
}