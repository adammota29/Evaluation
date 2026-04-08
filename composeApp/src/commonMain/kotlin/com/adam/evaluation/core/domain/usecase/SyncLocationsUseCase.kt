package com.adam.evaluation.core.domain.usecase

import com.adam.evaluation.core.domain.repository.LocationRepository

class SyncLocationsUseCase(
    private val repository: LocationRepository
) {
    suspend operator fun invoke() {
        repository.refreshLocations()
    }
}

