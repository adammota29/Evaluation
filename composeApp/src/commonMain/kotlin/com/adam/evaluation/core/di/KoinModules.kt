package com.adam.evaluation.core.di

import com.adam.evaluation.core.data.local.createAppDatabase
import com.adam.evaluation.core.data.remote.RickAndMortyApi
import com.adam.evaluation.core.data.repository.LocationRepositoryImpl
import com.adam.evaluation.core.domain.usecase.GetLocationsUseCase
import com.adam.evaluation.core.domain.usecase.ObserveLocationByIdUseCase
import com.adam.evaluation.core.domain.usecase.SyncLocationsUseCase
import com.adam.evaluation.core.domain.repository.LocationRepository
import com.adam.evaluation.core.presentation.screen.location_detail.LocationDetailViewModel
import com.adam.evaluation.core.presentation.screen.location_list.LocationListViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.GlobalContext
import org.koin.core.module.Module
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * Module Koin de la couche domaine.
 *
 * Enregistre les cas d'usage exposés à la couche présentation.
 */
val domainModule = module {
    factory { GetLocationsUseCase(get()) }
    factory { ObserveLocationByIdUseCase(get()) }
    factory { SyncLocationsUseCase(get()) }
}

/**
 * Module Koin de la couche data.
 *
 * Configure le client HTTP, l'accès base de données et l'implémentation du repository.
 */
val dataModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }

    single { RickAndMortyApi(get()) }

    single { createAppDatabase(driverFactory = get()) }

    single<LocationRepository> {
        LocationRepositoryImpl(
            api = get(),
            database = get()
        )
    }
}

/**
 * Module Koin de la couche présentation.
 *
 * Enregistre les composants UI orientés état, notamment les ViewModels.
 */
val presentationModule = module {
    factory { LocationListViewModel(get(), get()) }
    factory { (locationId: Int) ->
        LocationDetailViewModel(
            locationId = locationId,
            observeLocationByIdUseCase = get()
        )
    }
}

/**
 * Initialise Koin si aucun contexte global n'est déjà démarré.
 *
 * @param extraModules Modules supplémentaires à charger selon la plateforme.
 * @return `Unit`.
 */
fun initKoin(vararg extraModules: Module) {
    if (GlobalContext.getOrNull() == null) {
        startKoin {
            modules(
                domainModule,
                dataModule,
                presentationModule,
                *extraModules
            )
        }
    }
}