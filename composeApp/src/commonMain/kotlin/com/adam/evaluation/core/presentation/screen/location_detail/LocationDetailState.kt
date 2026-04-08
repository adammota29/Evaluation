package com.adam.evaluation.core.presentation.screen.location_detail

import com.adam.evaluation.core.domain.model.Location

data class LocationDetailState(
    val isLoading: Boolean = true,
    val location: Location? = null,
    val error: String? = null
)

