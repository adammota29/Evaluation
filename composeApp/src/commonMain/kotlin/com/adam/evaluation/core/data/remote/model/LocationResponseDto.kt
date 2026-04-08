package com.adam.evaluation.core.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class LocationResponseDto(
    val info: InfoDto,
    val results: List<LocationDto>
)