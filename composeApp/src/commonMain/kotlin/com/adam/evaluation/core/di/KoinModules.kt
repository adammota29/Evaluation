package com.adam.evaluation.core.di

import com.adam.evaluation.core.data.local.DatabaseDriverFactory
import com.adam.evaluation.core.data.local.createAppDatabase
import com.adam.evaluation.core.data.remote.RickAndMortyApi
import com.adam.evaluation.core.data.repository.LocationRepositoryImpl
import com.adam.evaluation.core.domain.usecase.GetLocationsUseCase
import com.adam.evaluation.core.domain.usecase.SyncLocationsUseCase
import com.adam.evaluation.core.domain.repository.LocationRepository
import com.adam.evaluation.core.presentation.screen.location_list.LocationListViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.GlobalContext
import org.koin.core.module.Module
import org.koin.core.context.startKoin
import org.koin.dsl.module

// 1. Module Domain
val domainModule = module {
    // factory signifie : "Crée une nouvelle instance à chaque fois qu'on la demande"
    factory { GetLocationsUseCase(get()) }
    factory { SyncLocationsUseCase(get()) }
}

// 2. Module Data
val dataModule = module {
    // Configuration du client Ktor pour les appels réseau
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true }) // ignoreUnknownKeys évite les crashs si l'API ajoute des champs
            }
        }
    }

    single { RickAndMortyApi(get()) }

    // Le driver est fourni par un module plateforme (android/jvm), puis on construit AppDatabase ici.
    single { createAppDatabase(driverFactory = get()) }

    // On lie l'implémentation (LocationRepositoryImpl) à son interface (LocationRepository)
    // C'est CRUCIAL en Clean Architecture : le reste de l'app ne connaît que l'interface !
    single<LocationRepository> {
        LocationRepositoryImpl(
            api = get(),
            database = get()
        )
    }
}

// 3. Module Presentation
val presentationModule = module {
    factory { LocationListViewModel(get(), get()) }
}

// 4. Fonction d'initialisation globale
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