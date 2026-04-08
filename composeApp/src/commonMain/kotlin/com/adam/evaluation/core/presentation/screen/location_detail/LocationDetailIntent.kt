package com.adam.evaluation.core.presentation.screen.location_detail

sealed class LocationDetailIntent {
    data object Load : LocationDetailIntent()
}

