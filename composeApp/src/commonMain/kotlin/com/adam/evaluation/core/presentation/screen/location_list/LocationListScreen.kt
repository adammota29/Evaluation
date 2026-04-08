package com.adam.evaluation.core.presentation.screen.location_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adam.evaluation.core.domain.model.Location


@Composable
fun LocationListScreen(
    viewModel: LocationListViewModel
) {
    // On observe l'état du ViewModel de manière réactive
    val state by viewModel.state.collectAsState()

    // L'UI réagit uniquement au State
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
            // Utilisation de la propriété dérivée demandée dans l'énoncé !
            Text(text = location.residentCountLabel, style = MaterialTheme.typography.bodySmall)
        }
    }
}