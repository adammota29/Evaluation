package com.adam.evaluation.core.di

import android.content.Context
import com.adam.evaluation.core.data.local.AndroidDatabaseDriverFactory
import com.adam.evaluation.core.data.local.DatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

fun platformModule(context: Context): Module = module {
    single<DatabaseDriverFactory> { AndroidDatabaseDriverFactory(context) }
}

