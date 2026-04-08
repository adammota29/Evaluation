package com.adam.evaluation.core.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object LocationListDestination : NavKey

@Serializable
data class LocationDetailDestination(
    val locationId: Int
) : NavKey

