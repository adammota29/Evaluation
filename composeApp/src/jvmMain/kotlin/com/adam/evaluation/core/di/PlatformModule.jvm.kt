package com.adam.evaluation.core.di

import com.adam.evaluation.core.data.local.DatabaseDriverFactory
import com.adam.evaluation.core.data.local.JvmDatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

fun platformModule(): Module = module {
    single<DatabaseDriverFactory> { JvmDatabaseDriverFactory() }
}

