package com.adam.evaluation.core.domain.usecase

import com.adam.evaluation.core.domain.model.Location
import com.adam.evaluation.core.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow

class ObserveLocationByIdUseCase(
    private val repository: LocationRepository
) {
    operator fun invoke(id: Int): Flow<Location?> {
        return repository.observeLocationById(id)
    }
}

