package com.kvest.compose_sandbox.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.kvest.compose_sandbox.ui.animated_counter.AnimatedCounterPanel

object AnimatedCounterDestination : ComposeSandboxDestination {
    override val route = "animated_counter"
}

fun NavGraphBuilder.addAnimatedCounterToNavGraph(navController: NavHostController) {
    composable(AnimatedCounterDestination.route) {
        AnimatedCounterPanel(Modifier.fillMaxSize())
    }
}