package com.adam.evaluation.core.presentation.screen.location_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adam.evaluation.core.domain.model.Location

/**
 * Affiche l'écran de liste des localisations.
 *
 * @param viewModel ViewModel qui expose l'état et traite les intentions utilisateur.
 * @return `Unit`.
 */
@Composable
fun LocationListScreen(
    viewModel: LocationListViewModel,
    onNavigateToDetail: (Int) -> Unit
) {
    // On observe l'état du ViewModel de manière réactive
    val state by viewModel.state.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is LocationListEffect.NavigateToDetail -> onNavigateToDetail(effect.locationId)
            }
        }
    }

    // L'UI est pilotée uniquement par l'état: loading, erreur, vide ou liste.
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading && state.locations.isEmpty() -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            state.error != null -> {
                Text(
                    text = state.error!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            state.locations.isEmpty() -> {
                Text(
                    text = "Aucune location disponible",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.locations) { location ->
                        LocationItem(
                            location = location,
                            onClick = {
                                // On envoie l'intention au ViewModel
                                viewModel.handleIntent(LocationListIntent.OnLocationClicked(it))
                            }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Affiche une carte de localisation cliquable.
 *
 * @param location Localisation à afficher.
 * @param onClick Callback appelé avec l'identifiant de la localisation.
 * @return `Unit`.
 */
@Composable
fun LocationItem(location: Location, onClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(location.id) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = location.name, style = MaterialTheme.typography.titleLarge)
            Text(text = "Type: ${location.type}")
            // Affiche le libellé dérivé calculé dans le modèle domaine.
            Text(text = location.residentCountLabel, style = MaterialTheme.typography.bodySmall)
        }
    }
}