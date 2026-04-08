package com.adam.evaluation.core.presentation.screen.location_list

import com.adam.evaluation.core.domain.model.Location

data class LocationListState(
    val isLoading: Boolean = false,
    val locations: List<Location> = emptyList(),
    val error: String? = null
)