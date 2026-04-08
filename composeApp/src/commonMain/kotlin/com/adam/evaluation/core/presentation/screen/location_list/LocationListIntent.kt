package com.adam.evaluation.core.presentation.screen.location_list

sealed class LocationListIntent {
    data object LoadLocations : LocationListIntent()
    data class OnLocationClicked(val locationId: Int) : LocationListIntent()
    data object OnRefresh : LocationListIntent()
}