package com.adam.evaluation.core.data.remote.model

import kotlinx.serialization.Serializable

/**
 * Métadonnées de pagination renvoyées par l'API.
 *
 * @property count Nombre total d'éléments disponibles.
 * @property pages Nombre total de pages disponibles.
 * @property next URL de la page suivante, ou `null` s'il n'y en a pas.
 * @property prev URL de la page précédente, ou `null` s'il n'y en a pas.
 */
@Serializable
data class InfoDto(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)