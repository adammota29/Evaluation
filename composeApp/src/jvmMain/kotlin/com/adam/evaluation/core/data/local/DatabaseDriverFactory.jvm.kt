package com.adam.evaluation.core.data.local

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

class JvmDatabaseDriverFactory : DatabaseDriverFactory {
    override fun createDriver() = JdbcSqliteDriver("jdbc:sqlite:evaluation.db").also { driver ->
        AppDatabase.Schema.create(driver)
    }
}

