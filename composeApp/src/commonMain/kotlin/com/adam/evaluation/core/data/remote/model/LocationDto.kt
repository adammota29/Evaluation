package com.adam.evaluation.core.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    @SerialName("residents") // Permet de lier le champ JSON "residents" à notre variable
    val residentUrls: List<String>,
    val url: String,
    val created: String
)