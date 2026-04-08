package com.adam.evaluation.core.di

import android.content.Context
import com.adam.evaluation.core.data.local.AndroidDatabaseDriverFactory
import com.adam.evaluation.core.data.local.DatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Crée le module Koin spécifique à Android.
 *
 * @param context Contexte Android utilisé pour instancier le driver de base.
 * @return Le [Module] Koin contenant les dépendances plateforme Android.
 */
fun platformModule(context: Context): Module = module {
    single<DatabaseDriverFactory> { AndroidDatabaseDriverFactory(context) }
}
