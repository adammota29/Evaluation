package com.adam.evaluation.core.presentation.screen.location_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LocationDetailScreen(
    viewModel: LocationDetailViewModel,
    onBack: (() -> Unit)? = null
) {
    val state by viewModel.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            state.error != null -> {
                Text(
                    text = state.error ?: "Erreur inconnue",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            state.location != null -> {
                val location = state.location
                if (location != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(text = location.name, style = MaterialTheme.typography.titleLarge)
                        Text(text = "Type: ${location.type}")
                        Text(text = "Dimension: ${location.dimension}")
                        Text(text = location.residentCountLabel)
                        if (onBack != null) {
                            Button(onClick = onBack) {
                                Text("Retour")
                            }
                        }
                    }
                }
            }

            else -> {
                Text(
                    text = "Aucune donnee disponible",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

