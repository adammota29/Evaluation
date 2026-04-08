package com.adam.evaluation.core.data.mapper

import com.adam.evaluation.core.data.remote.model.LocationDto
import com.adam.evaluation.core.domain.model.Location

/**
 * Convertit un [LocationDto] en modèle domaine [Location].
 *
 * @receiver DTO issu de l'API distante.
 * @return Le modèle domaine prêt à être consommé par la couche métier.
 */
fun LocationDto.toDomain(): Location {
    return Location(
        id = this.id,
        name = this.name,
        type = this.type,
        dimension = this.dimension,
        // L'API renvoie des URLs de résidents; on extrait uniquement l'identifiant final.
        residentIds = this.residentUrls.map { url ->
            url.substringAfterLast("/")
        },
        created = this.created
    )
}