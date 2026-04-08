package com.adam.evaluation.core.di

import com.adam.evaluation.core.audio.AudioManager
import com.adam.evaluation.core.audio.PlatformAudioManager
import com.adam.evaluation.core.data.local.DatabaseDriverFactory
import com.adam.evaluation.core.data.local.JvmDatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Crée le module Koin spécifique à la cible JVM desktop.
 *
 * @return Le [Module] Koin contenant les dépendances plateforme JVM.
 */
fun platformModule(): Module = module {
    single<DatabaseDriverFactory> { JvmDatabaseDriverFactory() }
    single<AudioManager> { PlatformAudioManager() }
}
