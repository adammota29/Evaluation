package com.adam.evaluation.core.domain.model

/**
 * Modèle domaine représentant une localisation métier.
 *
 * @property id Identifiant unique de la localisation.
 * @property name Nom affichable de la localisation.
 * @property type Type de localisation (planète, station, etc.).
 * @property dimension Dimension associée à la localisation.
 * @property residentIds Liste des identifiants des résidents liés à cette localisation.
 * @property created Horodatage de création fourni par la source distante.
 */
data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residentIds: List<String>,
    val created: String
) {
    /**
     * Libellé prêt à l'affichage du nombre de résidents.
     *
     * @return Une chaîne localisée décrivant le nombre de résidents.
     */
    val residentCountLabel: String
        get() = if (residentIds.isEmpty()) "Aucun résident" else "${residentIds.size} résidents"
}