package com.adam.evaluation.core.data.local

import app.cash.sqldelight.db.SqlDriver

/**
 * Contrat multiplateforme pour créer un driver SQLDelight natif à la plateforme.
 */
interface DatabaseDriverFactory {
    /**
     * Crée le driver SQLDelight de la plateforme courante.
     *
     * @return Le [SqlDriver] configuré pour l'application.
     */
    fun createDriver(): SqlDriver
}


