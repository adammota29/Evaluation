package com.adam.evaluation.core.data.local

import app.cash.sqldelight.db.SqlDriver

interface DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}


