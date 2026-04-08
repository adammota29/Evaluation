package com.adam.evaluation.core.presentation.screen.location_list

import com.adam.evaluation.core.domain.model.Location

/**
 * État UI de l'écran de liste des localisations.
 *
 * @property isLoading Indique qu'un chargement est en cours.
 * @property locations Liste actuelle des localisations à afficher.
 * @property error Message d'erreur à afficher, ou `null` en cas d'absence d'erreur.
 */
data class LocationListState(
    val isLoading: Boolean = false,
    val locations: List<Location> = emptyList(),
    val error: String? = null
)