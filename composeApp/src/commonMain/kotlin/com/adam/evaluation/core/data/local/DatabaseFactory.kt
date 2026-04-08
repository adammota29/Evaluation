package com.adam.evaluation.core.data.local

import app.cash.sqldelight.db.SqlDriver

/**
 * Crée une instance de [AppDatabase] à partir d'un driver SQLDelight existant.
 *
 * @param driver Driver SQLDelight déjà configuré.
 * @return Une instance prête à l'emploi de [AppDatabase].
 */
fun createAppDatabase(driver: SqlDriver): AppDatabase {
    return AppDatabase(driver)
}

/**
 * Crée une instance de [AppDatabase] à partir d'une fabrique de driver.
 *
 * @param driverFactory Fabrique responsable de créer le driver SQLDelight.
 * @return Une instance prête à l'emploi de [AppDatabase].
 */
fun createAppDatabase(driverFactory: DatabaseDriverFactory): AppDatabase {
    return AppDatabase(driverFactory.createDriver())
}


