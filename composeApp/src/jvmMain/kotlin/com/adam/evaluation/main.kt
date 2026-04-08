package com.adam.evaluation

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.adam.evaluation.core.di.initKoin
import com.adam.evaluation.core.di.platformModule

fun main() = application {
	initKoin(platformModule())

	Window(
		onCloseRequest = ::exitApplication,
		title = "Evaluation",
	) {
		App()
	}
}

