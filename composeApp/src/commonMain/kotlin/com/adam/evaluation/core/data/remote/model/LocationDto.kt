package com.adam.evaluation.core.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Représente une localisation telle que renvoyée par l'API distante.
 *
 * @property id Identifiant unique de la localisation.
 * @property name Nom de la localisation.
 * @property type Type de localisation (planète, station, etc.).
 * @property dimension Dimension associée à la localisation.
 * @property residentUrls URLs complètes des personnages résidents.
 * @property url URL canonique de la ressource localisation.
 * @property created Horodatage de création côté API.
 */
@Serializable
data class LocationDto(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    @SerialName("residents")
    val residentUrls: List<String>,
    val url: String,
    val created: String
)