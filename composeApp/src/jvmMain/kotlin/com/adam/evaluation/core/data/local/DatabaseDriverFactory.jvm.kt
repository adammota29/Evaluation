package com.adam.evaluation.core.data.local

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

/**
 * Implémentation JVM de [DatabaseDriverFactory].
 */
class JvmDatabaseDriverFactory : DatabaseDriverFactory {
    /**
     * Crée un driver SQLite JVM et initialise le schéma si nécessaire.
     *
     * @return Un [JdbcSqliteDriver] prêt à être utilisé par [AppDatabase].
     */
    override fun createDriver() = JdbcSqliteDriver("jdbc:sqlite:evaluation.db").also { driver ->
        // Sur JVM, la base peut être vierge au démarrage: on crée explicitement le schéma.
        AppDatabase.Schema.create(driver)
    }
}
