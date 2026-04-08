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
        // Le fichier SQLite persiste entre les runs Desktop: on ignore la création si le schéma existe déjà.
        runCatching { AppDatabase.Schema.create(driver) }
            .onFailure { error ->
                val message = error.message.orEmpty()
                if (!message.contains("already exists", ignoreCase = true)) {
                    throw error
                }
            }
    }
}
