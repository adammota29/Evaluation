package com.adam.evaluation.core.data.local

import app.cash.sqldelight.db.SqlDriver

fun createAppDatabase(driver: SqlDriver): AppDatabase {
    return AppDatabase(driver)
}

fun createAppDatabase(driverFactory: DatabaseDriverFactory): AppDatabase {
    return AppDatabase(driverFactory.createDriver())
}


