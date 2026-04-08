package com.adam.evaluation.core.data.local

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

class AndroidDatabaseDriverFactory(
    private val context: Context
) : DatabaseDriverFactory {
    override fun createDriver() = AndroidSqliteDriver(
        schema = AppDatabase.Schema,
        context = context,
        name = "evaluation.db"
    )
}

