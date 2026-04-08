package com.adam.evaluation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.adam.evaluation.core.presentation.navigation.LocationDetailDestination
import com.adam.evaluation.core.presentation.navigation.LocationListDestination
import com.adam.evaluation.core.presentation.screen.location_detail.LocationDetailScreen
import com.adam.evaluation.core.presentation.screen.location_detail.LocationDetailViewModel
import com.adam.evaluation.core.presentation.screen.location_list.LocationListScreen
import com.adam.evaluation.core.presentation.screen.location_list.LocationListViewModel
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.parametersOf
import org.koin.compose.viewmodel.koinViewModel

/**
 * Point d'entrée Compose de l'application.
 */
@Composable
fun App(isDesktop: Boolean = false) {
    MaterialTheme {
        if (isDesktop) {
            AppDesktopMasterDetail()
        } else {
            AppMobileNavigation()
        }
    }
}

@Composable
private fun AppMobileNavigation() {
    val backStack = remember { mutableStateListOf<NavKey>(LocationListDestination) }

    NavDisplay(
        backStack = backStack,
        onBack = {
            if (backStack.size > 1) {
                backStack.removeAt(backStack.lastIndex)
            }
        },
        entryProvider = entryProvider(
            fallback = { NavEntry(it) { Text("Route non supportee") } }
        ) {
            entry<LocationListDestination> {
                val viewModel = koinViewModel<LocationListViewModel>()
                LocationListScreen(
                    viewModel = viewModel,
                    onNavigateToDetail = { locationId ->
                        backStack.add(LocationDetailDestination(locationId))
                    }
                )
            }
            entry<LocationDetailDestination> { route ->
                val viewModel = koinViewModel<LocationDetailViewModel>(
                    key = "location-detail-${route.locationId}",
                    parameters = { parametersOf(route.locationId) }
                )

                LocationDetailScreen(
                    viewModel = viewModel,
                    onBack = {
                        if (backStack.size > 1) {
                            backStack.removeAt(backStack.lastIndex)
                        }
                    }
                )
            }
        }
    )
}

@Composable
private fun AppDesktopMasterDetail() {
    val koin = GlobalContext.get()
    val listViewModel = remember { koin.get<LocationListViewModel>() }
    var selectedLocationId by remember { mutableStateOf<Int?>(null) }

    Row(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .width(420.dp)
                .fillMaxHeight()
        ) {
            LocationListScreen(
                viewModel = listViewModel,
                onNavigateToDetail = { locationId ->
                    selectedLocationId = locationId
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val locationId = selectedLocationId
            if (locationId == null) {
                Text(
                    text = "Selectionne une location dans la liste",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                val detailViewModel = remember(locationId) {
                    koin.get<LocationDetailViewModel> {
                        parametersOf(locationId)
                    }
                }
                LocationDetailScreen(viewModel = detailViewModel)
            }
        }
    }
}