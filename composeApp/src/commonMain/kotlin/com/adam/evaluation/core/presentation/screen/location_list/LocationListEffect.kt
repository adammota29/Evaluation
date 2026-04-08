package com.adam.evaluation.core.presentation.screen.location_list

sealed class LocationListEffect {
    data class NavigateToDetail(val locationId: Int) : LocationListEffect()
}

