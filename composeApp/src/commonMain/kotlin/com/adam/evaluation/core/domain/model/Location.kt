package com.adam.evaluation.core.domain.model

data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residentIds: List<String>,
    val created: String
) {
    val residentCountLabel: String
        get() = if (residentIds.isEmpty()) "Aucun résident" else "${residentIds.size} résidents"
}