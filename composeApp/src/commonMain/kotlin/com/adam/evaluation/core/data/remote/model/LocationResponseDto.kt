package com.adam.evaluation.core.data.remote.model

import kotlinx.serialization.Serializable

/**
 * Représente la réponse paginée de l'endpoint des localisations.
 *
 * @property info Métadonnées de pagination de la réponse.
 * @property results Liste des localisations renvoyées pour la page demandée.
 */
@Serializable
data class LocationResponseDto(
    val info: InfoDto,
    val results: List<LocationDto>
)