package com.adam.evaluation.core.data.mapper

import com.adam.evaluation.core.data.remote.model.LocationDto
import com.adam.evaluation.core.domain.model.Location

fun LocationDto.toDomain(): Location {
    return Location(
        id = this.id,
        name = this.name,
        type = this.type,
        dimension = this.dimension,
        residentIds = this.residentUrls.map { url ->
            url.substringAfterLast("/")
        },
        created = this.created
    )
}