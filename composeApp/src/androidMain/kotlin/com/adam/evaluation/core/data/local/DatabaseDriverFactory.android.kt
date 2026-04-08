package com.adam.evaluation.core.data.local

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

/**
 * Implémentation Android de [DatabaseDriverFactory].
 *
 * @property context Contexte Android utilisé pour ouvrir la base SQLite.
 */
class AndroidDatabaseDriverFactory(
    private val context: Context
) : DatabaseDriverFactory {
    /**
     * Crée un driver SQLDelight Android pointant vers `evaluation.db`.
     *
     * @return Un [AndroidSqliteDriver] prêt à être utilisé par [AppDatabase].
     */
    override fun createDriver() = AndroidSqliteDriver(
        schema = AppDatabase.Schema,
        context = context,
        name = "evaluation.db"
    )
}
