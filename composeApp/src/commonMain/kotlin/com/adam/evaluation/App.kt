package com.adam.evaluation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.adam.evaluation.core.presentation.screen.location_list.LocationListScreen
import com.adam.evaluation.core.presentation.screen.location_list.LocationListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    MaterialTheme {
        // Koin injecte automatiquement le ViewModel avec toutes ses dépendances !
        val viewModel = koinViewModel<LocationListViewModel>()

        // On affiche notre écran
        LocationListScreen(viewModel = viewModel)
    }
}