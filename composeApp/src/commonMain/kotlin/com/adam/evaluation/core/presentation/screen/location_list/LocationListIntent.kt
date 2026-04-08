package com.adam.evaluation.core.presentation.screen.location_list

/**
 * Intention utilisateur manipulée par l'écran de liste des localisations.
 */
sealed class LocationListIntent {
    /**
     * Demande le chargement initial des localisations.
     */
    data object LoadLocations : LocationListIntent()

    /**
     * Signale le clic sur une localisation de la liste.
     *
     * @property locationId Identifiant de la localisation sélectionnée.
     */
    data class OnLocationClicked(val locationId: Int) : LocationListIntent()

    /**
     * Demande une synchronisation manuelle des localisations.
     */
    data object OnRefresh : LocationListIntent()
}